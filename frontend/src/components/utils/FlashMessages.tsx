import React, { useEffect, useState } from "react";
import styles from "./styles.module.css";

interface FlashMessage {
  message: string;
  type?: "success" | "error";
  duration?: number;
  onClose: () => void;
}

export const FlashMessageError: React.FC<FlashMessage> = ({
  message,
  type = "error",
  duration = 3000,
  onClose,
}) => {
  const [fadeOut, setFadeOut] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setFadeOut(true);
      setTimeout(onClose, 500);
    }, duration);

    return () => clearTimeout(timer);
  }, [duration, onClose]);

  return (
    <div
      className={`${styles.flash_card} ${fadeOut ? styles.fade_out : ""}`}
      style={{ backgroundColor: type === "error" ? "#f56565" : "#48bb78" }}
    >
      {message}
    </div>
  );
};
