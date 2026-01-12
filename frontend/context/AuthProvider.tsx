import { loginUser } from "@src/api/ApiLogin";
import axios from "axios";
import { useEffect, useState, createContext } from "react";

interface AuthContextType {
  user: string | null;
  accessToken: string | null;
  setAuth: (token: string, name: string) => void;
  login: (email: any, password: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState("");
  const [accessToken, setAccessToken] = useState("");

  const login = async (email: string, password: string) => {
    const dt = await loginUser({ email, password });
    if (dt?.accessToken) {
      setAccessToken(dt.token);
      setUser(dt.user);
      return true;
    } else {
      setAccessToken("");
      setUser("");
      return false;
    }
  };

  function logout() {
    setAccessToken("");
    setUser("");
  }

  useEffect(() => {
    const interceptor = axios.interceptors.response.use(
      (res: any) => res,
      async (error: any) => {
        if (error.response?.status === 401 && !error.config._retry) {
          error.config._retry = true;
          try {
            const refreshRes = await axios.post(
              "/auth/refresh",
              {},
              { withCredentials: true }
            );
            setAccessToken(refreshRes.data.accessToken);
            error.config.headers.Authorization = `Bearer ${refreshRes.data.accessToken}`;
            return axios(error.config);
          } catch (refreshError) {
            setAccessToken("");
            setUser("");
            return Promise.reject(refreshError);
          }
        }
        return Promise.reject(error);
      }
    );

    return () => axios.interceptors.response.eject(interceptor);
  }, []);

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
