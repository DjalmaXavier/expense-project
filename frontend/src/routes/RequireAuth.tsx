import { useAuth } from "@/hook/useAuth";
import { Navigate } from "react-router-dom";

function RequireAuth({ children }: { children: React.ReactNode }) {
  const { accessToken } = useAuth();
  return accessToken != null ? children : <Navigate to="/login" replace />;
}

export default RequireAuth;
