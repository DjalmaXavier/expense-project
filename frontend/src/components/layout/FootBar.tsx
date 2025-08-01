import styles from "./styles.module.css";

function FootBar() {
  const year: any = new Date().getFullYear();
  return (
    <>
      <footer>
        <div className={styles.footer}>
          <span>© {year} Copyright</span>
        </div>
      </footer>
    </>
  );
}

export default FootBar;
