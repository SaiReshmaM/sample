import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import API from "../api/api";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("USER"); // default
  const navigate = useNavigate();

  const register = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post("/auth/register", {
        email,
        password,
        role,
      });

      if (res.data === "User already exists!") {
        alert(res.data);
      } else {
        alert("Registration successful! Please login.");
        navigate("/");
      }
    } catch (err) {
      console.error(err);
      alert("Registration failed");
    }
  };

  return (
    <div className="login-container" style={{ padding: "40px", maxWidth: "400px", margin: "auto", textAlign: "center" }}>
      <h2>Register</h2>

      <form onSubmit={register} style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          style={{ padding: "10px" }}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          style={{ padding: "10px" }}
        />
        
        <select value={role} onChange={(e) => setRole(e.target.value)} style={{ padding: "10px" }}>
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>

        <button type="submit" style={{ padding: "10px", marginTop: "10px", cursor: "pointer" }}>Register</button>
      </form>
      
      <p style={{ marginTop: "20px" }}>
        Already have an account? <Link to="/">Login here</Link>
      </p>
    </div>
  );
}
