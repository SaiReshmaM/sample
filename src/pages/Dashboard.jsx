// import React, { useEffect, useState } from "react";

// function Dashboard({ token }) {
//   const [customers, setCustomers] = useState([]);
//   const [name, setName] = useState("");
//   const [email, setEmail] = useState("");

//   const fetchCustomers = async () => {
//     const res = await fetch("http://localhost:8080/customers", {
//       headers: {
//         Authorization: "Bearer " + token
//       }
//     });

//     const data = await res.json();
//     setCustomers(data);
//   };

//   useEffect(() => {
//     fetchCustomers();
//   }, []);

//   const addCustomer = async () => {
//     await fetch("http://localhost:8080/customers", {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//         Authorization: "Bearer " + token
//       },
//       body: JSON.stringify({ name, email })
//     });

//     fetchCustomers();
//   };

//   return (
//     <div style={{ padding: "20px" }}>
//       <h2>Dashboard</h2>

//       <input placeholder="Name" onChange={(e) => setName(e.target.value)} />
//       <input placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
//       <button onClick={addCustomer}>Add</button>

//       <ul>
//         {customers.map((c) => (
//           <li key={c.id}>
//             {c.name} - {c.email}
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// }

// export default Dashboard;
import { useEffect, useState } from "react";
import API from "../api/api";

export default function Dashboard() {
  const [revenue, setRevenue] = useState(0);
  const [summary, setSummary] = useState("");
  const role = localStorage.getItem("role") || "USER";

  useEffect(() => {
    if (role === "ADMIN") {
      API.get("/dashboard/revenue")
        .then((res) => setRevenue(res.data))
        .catch(() => alert("Error loading revenue"));
    }
    API.get("/invoices/summary")
      .then((res) => setSummary(res.data))
      .catch(() => alert("Error loading summary"));
  }, [role]);

  return (
    <div className="app-container">
      <h2>Dashboard ({role})</h2>
      {role === "ADMIN" ? (
        <div className="summary-cards">
          <div className="card">
            <h3>Overview</h3>
            <p style={{ fontSize: "1.2rem", fontWeight: "600", marginTop: "10px" }}>{summary}</p>
          </div>
          <div className="card">
            <h3>Total Revenue</h3>
            <p style={{ fontSize: "1.5rem", color: "var(--secondary-color)", fontWeight: "700", marginTop: "10px" }}>₹{revenue}</p>
          </div>
        </div>
      ) : (
        <div className="summary-cards">
          <div className="card">
            <h3>Summary</h3>
            <p style={{ fontSize: "1.2rem", fontWeight: "600", marginTop: "10px" }}>{summary}</p>
          </div>
        </div>
      )}
    </div>
  );
}