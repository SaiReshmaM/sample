import { useEffect, useState } from "react";
import API from "../api/api";

export default function Invoices() {
  const [invoices, setInvoices] = useState([]);
  const [amount, setAmount] = useState("");
  const [status, setStatus] = useState("PENDING");
  
  const role = localStorage.getItem("role") || "USER";

  const loadInvoices = () => {
    API.get("/invoices").then((res) => setInvoices(res.data)).catch(console.error);
  };

  const addInvoice = () => {
    API.post("/invoices", { amount, status }).then(() => {
      loadInvoices();
      setAmount("");
    }).catch(err => alert("Failed to add invoice"));
  };
  
  const updateInvoice = (id, newAmount, newStatus) => {
    API.put(`/invoices/${id}`, { amount: newAmount, status: newStatus }).then(() => {
      loadInvoices();
    }).catch(err => alert("Failed to update invoice"));
  };

  const deleteInvoice = (id) => {
    API.delete(`/invoices/${id}`).then(() => {
      loadInvoices();
    }).catch(err => alert("Failed to delete invoice"));
  };

  const downloadPDF = (id, amount, status) => {
    const content = `
      <html>
        <head><title>Invoice #${id}</title></head>
        <body style="font-family: Arial, sans-serif; padding: 40px;">
          <h2>SaaS Project Invoice</h2>
          <hr/>
          <p><strong>Invoice ID:</strong> ${id}</p>
          <p><strong>Amount:</strong> ₹${amount}</p>
          <p><strong>Status:</strong> ${status}</p>
          <br/>
          <p>Thank you for your business!</p>
          <script>
            window.onload = function() {
              setTimeout(function() {
                window.print();
                window.onafterprint = function() {
                  window.close();
                };
              }, 500); // Give it a short delay to render content
            };
          </script>
        </body>
      </html>
    `;
    const printWindow = window.open('', '_blank');
    if (printWindow) {
      printWindow.document.write(content);
      printWindow.document.close();
    } else {
      alert("Please allow popups to download the PDF");
    }
  };

  useEffect(() => {
    loadInvoices();
  }, []);

  return (
    <div className="app-container">
      <h2>Invoices</h2>

      {role === "ADMIN" && (
        <div style={{ marginBottom: "20px", padding: "10px", border: "1px solid #ccc", borderRadius: "8px" }}>
          <h3>Create Invoice</h3>
          <input placeholder="Amount" value={amount} onChange={(e) => setAmount(e.target.value)} style={{ marginRight: "10px" }} />
          <select value={status} onChange={(e) => setStatus(e.target.value)} style={{ marginRight: "10px" }}>
            <option value="PENDING">PENDING</option>
            <option value="PAID">PAID</option>
          </select>
          <button onClick={addInvoice}>Add</button>
        </div>
      )}

      <table border="1" cellPadding="10" style={{ borderCollapse: "collapse", width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {invoices.map((i) => (
            <tr key={i.id}>
              <td>{i.id}</td>
              <td>₹{i.amount}</td>
              <td>{i.status}</td>
              <td>
                <button onClick={() => downloadPDF(i.id, i.amount, i.status)} style={{ marginRight: "10px" }}>Download PDF</button>
                {role === "ADMIN" && (
                  <>
                    <button onClick={() => {
                        const newAmount = prompt("Enter new amount for invoice", i.amount);
                        if (newAmount) {
                            updateInvoice(i.id, newAmount, i.status);
                        }
                    }} style={{ marginRight: "10px" }}>Edit ✏️</button>
                    <button onClick={() => updateInvoice(i.id, i.amount, i.status === "PENDING" ? "PAID" : "PENDING")} style={{ marginRight: "10px" }}>Toggle Status</button>
                    <button onClick={() => deleteInvoice(i.id)}>Delete ❌</button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}