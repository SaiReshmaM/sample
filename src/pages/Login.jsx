import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import API from "../api/api";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const login = async (e) => {
    e.preventDefault();
    try {
      const res = await API.post("/auth/login", {
        email,
        password,
      });

      console.log(res.data); // 🔍 check this in console

      // ✅ store token correctly
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", res.data.role);

      alert("Login successful");

      navigate("/dashboard");

    } catch (err) {
      console.error(err);
      alert("Login failed. Please check your credentials or register.");
    }
  };

  return (
    <div className="login-container" style={{ padding: "40px", maxWidth: "400px", margin: "auto", textAlign: "center" }}>
      <h2>Login</h2>

      <form onSubmit={login} style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
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

        <button type="submit" style={{ padding: "10px", marginTop: "10px", cursor: "pointer" }}>Login</button>
      </form>

      <p style={{ marginTop: "20px" }}>
        Don't have an account? <Link to="/register">Register here</Link>
      </p>
    </div>
  );
}