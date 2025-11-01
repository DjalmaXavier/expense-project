import { Link } from "react-router-dom";
import styles from "@/src/components/layout/styles.module.css";

function NavBar() {
  return (
    <>
      <nav className={styles.navbar}>
        <Link to="/" className={styles.logo}>
          Off Gastos
        </Link>

        <div className={styles.actions}>
          <Link to="/register" className={styles.button}>
            Register
          </Link>
        </div>
      </nav>
    </>
  );
}

export default NavBar;
