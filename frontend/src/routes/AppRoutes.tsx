import { BrowserRouter, Route, Routes } from "react-router-dom";
import {
  Home,
  Register,
  Dashboard,
  PageNotFound,
} from "@src/components/pages/indexPages";
import RequireAuth from "./RequireAuth";
import GuestRoute from "./GuestRoute";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          index
          element={
            <GuestRoute>
              <Home />
            </GuestRoute>
          }
        />
        <Route path="*" element={<PageNotFound />} />
        <Route path="register" element={<Register />} />
        <Route
          path="/dashboard"
          element={
            <RequireAuth>
              <Dashboard />
            </RequireAuth>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}
