import { useNavigate } from "react-router-dom";
import FootBar from "../../layout/FootBar";
import NavBar from "../../layout/NavBar";
import { useState } from "react";
import { registerUser } from "../../../api/ApiRegister";

function Register() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [confirmedPassword, setConfirmedPassword] = useState("");

  function saveUser(e: any) {
    e.preventDefault();

    if (confirmedPassword !== password) {
      alert("Passwords do not match!");
      return;
    }

    const user = { login, password, name };
    registerUser(user).then((e: any) =>
      //Fazer tabela de status

      navigate("/login")
    );
  }

  return (
    <>
      <div>
        <NavBar />
        <div>
          <h1>REGISTER PAGE</h1>
          <form>
            <div>
              <label>Username: </label>
              <input
                type="text"
                placeholder="Insert username"
                onChange={(e: any) => setName(e.target.value)}
                name="username"
              />
            </div>
            <div>
              <label>Email address</label>
              <input
                type="email"
                placeholder="Insert email"
                onChange={(e: any) => setLogin(e.target.value)}
                name="email"
              />
            </div>
            <div>
              <label>Password</label>
              <input
                type="password"
                placeholder="Insert password"
                onChange={(e: any) => setPassword(e.target.value)}
                name="password"
              />
            </div>
            <div>
              <label>Confirm Password</label>
              <input
                type="password"
                placeholder="Confirm Password"
                onChange={(e: any) => setConfirmedPassword(e.target.value)}
                name="confirmPassword"
              />
            </div>
            <div>
              <button type="submit" onClick={saveUser}>
                Cadastrar
              </button>
            </div>
          </form>
        </div>
      </div>

      <FootBar />
    </>
  );
}

export default Register;
