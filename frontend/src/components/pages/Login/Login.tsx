import { useState } from "react";
import FootBar from "../../layout/FootBar";
import NavBar from "../../layout/NavBar";
import { loginUser } from "../../../api/ApiLogin";

function Login() {
  const [loginAuth, setLoginAuth] = useState("");
  const [password, setPassword] = useState("");

  const loginButton = async (e: any) => {
    e.preventDefault();
    const user = { email: loginAuth, password };
    await loginUser(user).then((data) => {
      console.log(data);
    });
  };

  return (
    <>
      <NavBar />
      <div>
        <h1>LOGIN PAGE</h1>
        <form>
          <div>
            <label>Email adress: </label>
            <input
              type="email"
              onChange={(e: any) => setLoginAuth(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Password: </label>
            <input
              type="password"
              onChange={(e: any) => setPassword(e.target.value)}
              required
            />
          </div>
          <div>
            <button type="submit" value="Login" onClick={loginButton}>
              Login
            </button>
          </div>
        </form>
        <FootBar />
      </div>
    </>
  );
}

export default Login;
