import { useState, useEffect } from "react";

export default function Settings() {
  const [darkMode, setDarkMode] = useState(true);
  const [notifications, setNotifications] = useState(true);
  const [twoFactor, setTwoFactor] = useState(false);
  const [saveStatus, setSaveStatus] = useState("");

  // Load from local storage
  useEffect(() => {
    const loadedDark = localStorage.getItem("theme") !== "light";
    const loadedNotifs = localStorage.getItem("notifications") === "true";
    const loadedTwoFac = localStorage.getItem("2fa") === "true";
    
    setDarkMode(loadedDark);
    setNotifications(loadedNotifs);
    setTwoFactor(loadedTwoFac);
  }, []);

  const handleSave = () => {
    localStorage.setItem("theme", darkMode ? "dark" : "light");
    localStorage.setItem("notifications", notifications);
    localStorage.setItem("2fa", twoFactor);

    // Explicitly toggle light mode classes if disabled
    if (darkMode) {
      document.body.classList.remove("light-theme");
    } else {
      document.body.classList.add("light-theme");
    }

    setSaveStatus("Settings successfully saved!");
    setTimeout(() => setSaveStatus(""), 3000);
  };

  const Toggle = ({ label, state, setState }) => (
    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", padding: "15px 0", borderBottom: "1px solid var(--border-color)" }}>
      <span style={{ fontSize: "1.1rem", fontWeight: "600" }}>{label}</span>
      <label className="switch">
        <input type="checkbox" checked={state} onChange={(e) => setState(e.target.checked)} />
        <span className="slider round"></span>
      </label>
    </div>
  );

  return (
    <div className="app-container">
      <h2>Settings</h2>
      
      <div className="card" style={{ maxWidth: "600px" }}>
        <h3>Preferences</h3>
        <Toggle label="Dark Mode Interface" state={darkMode} setState={setDarkMode} />
        <Toggle label="Email Notifications" state={notifications} setState={setNotifications} />
        
        <h3 style={{ marginTop: "30px" }}>Security</h3>
        <Toggle label="Two-Factor Authentication (2FA)" state={twoFactor} setState={setTwoFactor} />
        
        <button onClick={handleSave} style={{ marginTop: "30px", width: "100%" }}>Save Changes</button>
        {saveStatus && <p style={{ color: "var(--secondary-color)", marginTop: "15px", textAlign: "center", fontWeight: "bold" }}>{saveStatus}</p>}
      </div>
    </div>
  );
}
