import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "@hook/useAuth";
import { FootBar, NavBar } from "@src/components/layout/indexLayouts";
import { FlashMessageError } from "@src/components/utils/FlashMessages";
import styles from "./styles.module.css";

function Home() {
  const navigator = useNavigate();
  const { login } = useAuth();
  const [loginAuth, setLoginAuth] = useState<string | null>(null);
  const [password, setPassword] = useState("");
  const [flashMessage, setFlashMessage] = useState<string | null>(null);

  const loginButton = async (e: any) => {
    e.preventDefault();
    const check = await login(loginAuth, password);
    if (!check) {
      setFlashMessage("Usuário e/ou senha incorretos!");
    } else {
      navigator("/dashboard");
    }
  };
  return (
    <>
      <div className="app">
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
              {flashMessage && (
                <FlashMessageError
                  key={flashMessage}
                  message={flashMessage}
                  onClose={() => setFlashMessage(null)}
                />
              )}
            </form>
          </div>
        </main>
        <FootBar />
      </div>
    </>
  );
}

export default Home;
