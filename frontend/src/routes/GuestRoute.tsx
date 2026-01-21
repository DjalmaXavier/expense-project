import { useAuth } from "@/hook/useAuth";
import { Navigate } from "react-router-dom";

function GuestRoute({ children }: { children: React.ReactNode }) {
  const { accessToken } = useAuth();

  return accessToken === null ? children : <Navigate to="/dashboard" replace />;
}

export default GuestRoute;
