import { Link, useLocation, useNavigate } from "react-router-dom";

export default function Sidebar() {
  const role = localStorage.getItem("role") || "USER";
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/");
  };

  const navLinks = [
    { name: "Dashboard", path: "/dashboard", roles: ["ADMIN", "USER"] },
    { name: "Invoices", path: "/invoices", roles: ["ADMIN", "USER"] },
    { name: "Customers", path: "/customers", roles: ["ADMIN"] },
    { name: "Profile", path: "/profile", roles: ["ADMIN", "USER"] },
    { name: "Settings", path: "/settings", roles: ["ADMIN", "USER"] },
  ];

  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <h2>SaaS Pro</h2>
        <span className="role-badge">{role}</span>
      </div>
      
      <nav className="sidebar-nav">
        {navLinks
          .filter((link) => link.roles.includes(role))
          .map((link) => (
            <Link
              key={link.name}
              to={link.path}
              className={`sidebar-link ${location.pathname === link.path ? "active" : ""}`}
            >
              {link.name}
            </Link>
          ))}
      </nav>

      <div className="sidebar-footer">
        <button onClick={handleLogout} className="btn-logout">Logout</button>
      </div>
    </aside>
  );
}
