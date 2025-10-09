import { useState } from "react";
import FootBar from "../../layout/FootBar";
import NavBar from "../../layout/NavBar";
import styles from "./styles.module.css";
import { loginUser } from "../../../api/ApiLogin";
import { Link, useNavigate } from "react-router-dom";

function Home() {
  const navigator = useNavigate();
  const [loginAuth, setLoginAuth] = useState("");
  const [password, setPassword] = useState("");

  const loginButton = async (e: any) => {
    e.preventDefault();
    const user = { email: loginAuth, password };
    await loginUser(user).then((data) => {
      navigator("/main");
      console.log(data);
    });
  };
  return (
    <>
      <NavBar />
      <main className={styles.main}>
        <div className={styles.content}>
          <h1>Controle de gastos</h1>
          <form className={styles.form}>
            <span className={styles.input_span}>
              <label className={styles.label}>Email</label>
              <input
                type="email"
                onChange={(e: any) => setLoginAuth(e.target.value)}
              />
            </span>
            <span className={styles.input_span}>
              <label className={styles.label}>Password</label>
              <input
                type="password"
                onChange={(e: any) => setPassword(e.target.value)}
              />
            </span>
            <input
              type="submit"
              value="Log in"
              className={styles.submit}
              onClick={loginButton}
            />
            <span className={styles.span}>
              Don't have an account? <Link to={"/register"}>Sign up</Link>
            </span>
          </form>
        </div>
      </main>
      <FootBar />
    </>
  );
}

export default Home;
