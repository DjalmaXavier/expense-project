import { loginUser, logoutUser, refreshToken } from "@src/api/ApiLogin";
import axios from "axios";
import { getUserFromToken } from "@context/utils/jwt";
import { useEffect, useState, createContext } from "react";
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const LOGIN_ENDPOINT = import.meta.env.VITE_AUTH_LOGIN_ENDPOINT;
const REFRESHTOKEN_ENDPOINT = import.meta.env.VITE_REFRESHTOKEN;

interface AuthContextType {
  user: string | null;
  accessToken: string | null;
  setAuth: (token: string, name: string) => void;
  login: (email: any, password: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<string | null>(null);
  const [accessToken, setAccessToken] = useState<string | null>(null);
  //const [loading, setLoading] = useState(true); TO - DO: implement loading state

  const login = async (email: string, password: string) => {
    const dt = await loginUser({ email, password });
    if (dt?.accessToken) {
      setAccessToken(dt.accessToken);
      const decoded = getUserFromToken(dt.accessToken);
      setUser(decoded.name);
      return true;
    }
    return false;
  };

  const logout = async () => {
    await logoutUser();
    setAccessToken(null);
    setUser(null);
  };

  useEffect(() => {
    const checkSession = async () => {
      try {
        const checkSession = await refreshToken();
        if (checkSession.accessToken) {
          setAccessToken(checkSession.accessToken);
          setUser(checkSession.user);
        }
      } catch {
        logout();
      }
    };

    checkSession();

    const interceptor = axios.interceptors.response.use(
      (res) => res,
      async (error) => {
        const originalRequest = error.config;

        if (
          originalRequest.url.includes(`${API_BASE_URL}${LOGIN_ENDPOINT}`) ||
          originalRequest.url.includes(
            `${API_BASE_URL}${REFRESHTOKEN_ENDPOINT}`,
          )
        ) {
          return Promise.reject(error);
        }

        if (error.response?.status === 401 && !originalRequest._retry) {
          originalRequest._retry = true;

          try {
            const refreshRes = await refreshToken();
            if (!refreshRes) {
              logout();
              return Promise.reject(error);
            }

            setAccessToken(refreshRes.accessToken);
            originalRequest.headers.Authorization = `Bearer ${refreshRes.accessToken}`;

            return axios(originalRequest);
          } catch {
            logout();
          }
        }

        return Promise.reject(error);
      },
    );

    return () => axios.interceptors.response.eject(interceptor);
  }, [accessToken]);

  return (
    <AuthContext.Provider
      value={{
        accessToken,
        user,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
