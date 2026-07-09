import { login } from "../api/service/user.service.js";
import { saveAuth } from "./shared/session.js";

const loginForm = document.getElementById("loginForm");
const loginAlert = document.getElementById("loginAlert");
const loginButton = document.getElementById("loginButton");
const demoAccount = document.querySelector(".login-card .bg-light");

if (demoAccount) {
  demoAccount.innerHTML =
    "Tai khoan demo: <strong>tan1</strong> / <strong>1</strong><br>Role duoc phep vao: <strong>ADMIN</strong>";
}

loginForm?.addEventListener("submit", async (event) => {
  event.preventDefault();

  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value;

  setLoginLoading(true);
  hideLoginAlert();

  try {
    const result = await login({ username, password });

    saveAuth(result, username);

    window.location.href = "dashboard.html";
  } catch (error) {
    showLoginAlert(error.message || "Không thể kết nối đến máy chủ.");
  } finally {
    setLoginLoading(false);
  }
});

function showLoginAlert(message) {
  loginAlert.textContent = message;
  loginAlert.classList.remove("d-none");
}

function hideLoginAlert() {
  loginAlert.textContent = "";
  loginAlert.classList.add("d-none");
}

function setLoginLoading(isLoading) {
  loginButton.disabled = isLoading;
  loginButton.innerHTML = isLoading
    ? '<span class="spinner-border spinner-border-sm me-2" aria-hidden="true"></span>Đang đăng nhập...'
    : '<i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập';
}
