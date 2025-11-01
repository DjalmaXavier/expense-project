import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home, Register } from "@src/components/pages/indexPages";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
        <Route path="register" element={<Register />} />
      </Routes>
    </BrowserRouter>
  );
}
