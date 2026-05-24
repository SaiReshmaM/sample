import { useEffect, useState } from "react";
import API from "../api/api";

export default function Customers() {
  const [customers, setCustomers] = useState([]);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const loadCustomers = () => {
    API.get("/customers").then((res) => setCustomers(res.data));
  };

  const addCustomer = () => {
    API.post("/customers", { name, email }).then(() => {
      loadCustomers();
      setName("");
      setEmail("");
    });
  };

  useEffect(() => {
    loadCustomers();
  }, []);

  return (
    <div>
      <h2>Customers</h2>

      <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} />
      <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
      <button onClick={addCustomer}>Add</button>

      <ul>
        {customers.map((c) => (
          <li key={c.id}>{c.name} - {c.email}</li>
        ))}
      </ul>
    </div>
  );
}