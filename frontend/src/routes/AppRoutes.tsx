import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home } from "../components/pages/indexPages";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}
