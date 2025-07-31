import { Link } from "react-router-dom";
import styles from "./styles.module.css";

function NavBar() {
  return (
    <>
      <nav className="navbar">
        <Link to="/" className="navbar-logo">
          Not too Expensive
        </Link>

        <div className="navbar-actions">
          <a role="button" className="navbar-login">
            Login
          </a>
          <a role="button" className="navbar-register">
            Register
          </a>
        </div>
      </nav>
    </>
  );
}

export default NavBar;
