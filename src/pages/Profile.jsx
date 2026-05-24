import { useEffect, useState } from "react";

export default function Profile() {
  const role = localStorage.getItem("role") || "USER";
  const [email, setEmail] = useState("user@example.com");

  useEffect(() => {
    try {
      const token = localStorage.getItem("token");
      if (token && token.split('.').length === 3) {
        // very basic manual parsing since we don't have jwt-decode installed
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (payload.sub) setEmail(payload.sub);
      }
    } catch (e) {
      console.error(e);
    }
  }, []);

  return (
    <div className="app-container">
      <h2>My Profile</h2>
      <div className="card profile-card" style={{ display: "flex", gap: "20px", alignItems: "center" }}>
        <div className="avatar" style={{ 
          width: "100px", height: "100px", borderRadius: "50%", 
          background: "linear-gradient(135deg, var(--primary-color), var(--secondary-color))",
          display: "flex", alignItems: "center", justifyContent: "center",
          fontSize: "2rem", fontWeight: "bold"
        }}>
          {email.charAt(0).toUpperCase()}
        </div>
        <div>
          <h3 style={{ marginBottom: "5px" }}>{email}</h3>
          <p style={{ color: "var(--primary-color)", fontWeight: "600" }}>Role: {role}</p>
          <p style={{ marginTop: "10px", color: "#9ca3af" }}>Member since: April 2026</p>
        </div>
      </div>
    </div>
  );
}
