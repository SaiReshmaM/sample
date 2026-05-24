// const API = "http://localhost:8080";

// export const login = async (data) => {
//   const res = await fetch(`${API}/auth/login`, {
//     method: "POST",
//     headers: {"Content-Type": "application/json"},
//     body: JSON.stringify(data)
//   });
//   return res.json();
// };

// export const getCustomers = async (token) => {
//   const res = await fetch(`${API}/customers`, {
//     headers: {
//       "Authorization": "Bearer " + token
//     }
//   });
//   return res.json();
// };

// export const createCustomer = async (token, data) => {
//   const res = await fetch(`${API}/customers`, {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//       "Authorization": "Bearer " + token
//     },
//     body: JSON.stringify(data)
//   });
//   return res.json();
// };

import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080",
});

// attach token automatically
API.interceptors.request.use((req) => {
  const token = localStorage.getItem("token");
  if (token && token !== "undefined" && token !== "null") {
    req.headers.Authorization = `Bearer ${token}`;
  } else {
    // Clean up if it's invalid
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    if (window.location.pathname !== "/" && window.location.pathname !== "/register") {
       window.location.href = "/";
    }
  }
  return req;
});

export default API;