import { clearAuth, getAuthRole, isAuthenticated } from "./shared/session.js";

(function () {
  const loginPath = "login.html";
  const currentPath = window.location.pathname.split("/").pop() || "";

  const redirectTo = (path) => {
    if (currentPath !== path) {
      window.location.replace(path);
    }
  };

  window.handleLogout = function handleLogout() {
    clearAuth();
    redirectTo(loginPath);
  };

  if (currentPath !== loginPath && !isAuthenticated()) {
    redirectTo(loginPath);
    return;
  }

  const role = String(getAuthRole() || "").toUpperCase();
  const staffAllowedPaths = new Set(["machines.html", "inventory.html", loginPath]);
  if (role === "STAFF" && !staffAllowedPaths.has(currentPath)) {
    redirectTo("machines.html");
    return;
  }

  window.addEventListener("storage", () => {
    if (!isAuthenticated()) redirectTo(loginPath);
  });

  setInterval(() => {
    if (currentPath !== loginPath && !isAuthenticated()) redirectTo(loginPath);
  }, 500);
})();
