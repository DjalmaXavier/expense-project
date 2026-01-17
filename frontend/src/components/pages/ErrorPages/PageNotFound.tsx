import { Link } from "react-router-dom";
import FootBar from "@src/components/layout/FootBar";
import NavBar from "@src/components/layout/NavBar";
import styles from "./styles.module.css";

function PageNotFound() {
  return (
    <>
      <div className="app">
        <NavBar />
        <main className={styles.main}>
          <div className={styles.content}>
            <h1>404 - Página não encontrada</h1>
            <p>A página que você procurou não existe.</p>
            <Link to="/">Volte para o inicio</Link>
          </div>
        </main>
        <FootBar />
      </div>
    </>
  );
}

export default PageNotFound;
