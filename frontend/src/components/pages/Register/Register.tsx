import { useNavigate } from "react-router-dom";
import FootBar from "@src/components/layout/FootBar";
import NavBar from "@src/components/layout/NavBar";
import { useState } from "react";
import { registerUser } from "@src/api/ApiRegister";
import styles from "./style.module.css";
import { FlashMessageError } from "@src/components/utils/FlashMessages";

function Register() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [confirmedPassword, setConfirmedPassword] = useState("");
  const [flashMessage, setFlashMessage] = useState<string | null>(null);

  function saveUser(e: any) {
    e.preventDefault();

    if (confirmedPassword !== password) {
      setFlashMessage("As senhas não coincidem!");
      return;
    }

    const user = { login, password, name };
    registerUser(user).then((e: any) => {
      if (e.code === "EMAIL_ALREADY_EXISTS") {
        setFlashMessage("E-mail já cadastrado!");
        return;
      }
      navigate("/login");
    });
  }

  return (
    <>
      <div className="app">
        <NavBar />
        <div className={styles.main}>
          <h1>REGISTER PAGE</h1>
          <form className={styles.form}>
            <span className={styles.input_span}>
              <label className={styles.label}>Username</label>
              <input
                type="email"
                onChange={(e: any) => setName(e.target.value)}
              />
            </span>
            <span className={styles.input_span}>
              <label className={styles.label}>E-mail</label>
              <input
                type="email"
                onChange={(e: any) => setLogin(e.target.value)}
              />
            </span>
            <span className={styles.input_span}>
              <label className={styles.label}>Password</label>
              <input
                type="password"
                onChange={(e: any) => setPassword(e.target.value)}
              />
            </span>
            <span className={styles.input_span}>
              <label className={styles.label}>Confirm password</label>
              <input
                type="password"
                onChange={(e: any) => setConfirmedPassword(e.target.value)}
              />
            </span>
            <input
              type="submit"
              value="Register"
              className={styles.submit}
              onClick={saveUser}
            />
            {flashMessage && (
              <FlashMessageError
                key={flashMessage}
                message={flashMessage}
                onClose={() => setFlashMessage(null)}
              />
            )}
          </form>
        </div>
      </div>

      <FootBar />
    </>
  );
}

export default Register;
