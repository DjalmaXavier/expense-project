import { Link } from "react-router-dom";
import styles from "./styles.module.css";

function NavBar() {
  return (
    <>
      <nav className={styles.navbar}>
        <Link to="/" className={styles.logo}>
          Off Gastos
        </Link>

        <div className={styles.actions}>
          <a role="button" className={styles.button}>
            Login
          </a>
          <a role="button" className={styles.button}>
            Register
          </a>
        </div>
      </nav>
    </>
  );
}

export default NavBar;
