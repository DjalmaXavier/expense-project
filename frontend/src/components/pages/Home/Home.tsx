import FootBar from "../../layout/FootBar";
import NavBar from "../../layout/NavBar";
import styles from "./styles.module.css";

function Home() {
  return (
    <>
      <NavBar />
      <main className={styles.main}>
        <div className={styles.content}>
          <h1>Controle de gastos</h1>
        </div>
      </main>
      <FootBar />
    </>
  );
}

export default Home;
