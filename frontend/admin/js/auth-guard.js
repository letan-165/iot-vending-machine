import { clearAuth, isAuthenticated } from "./shared/session.js";

(function () {
  const loginPath = "login.html";
  const currentPath = window.location.pathname.split("/").pop() || "";
  const redirectToLogin = () => {
    if (currentPath !== loginPath) {
      window.location.replace(loginPath);
    }
  };

  window.handleLogout = function handleLogout() {
    clearAuth();
    redirectToLogin();
  };

  if (currentPath !== loginPath && !isAuthenticated()) {
    redirectToLogin();
    return;
  }

  window.addEventListener("storage", () => {
    if (!isAuthenticated()) redirectToLogin();
  });

  setInterval(() => {
    if (currentPath !== loginPath && !isAuthenticated()) redirectToLogin();
  }, 500);
})();
