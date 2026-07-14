const AUTH_KEYS = ["authToken", "userID", "username", "role"];

export function getAuthToken() {
  return localStorage.getItem("authToken") || "";
}

export function getAuthRole() {
  return localStorage.getItem("role") || "";
}

export function isAuthenticated() {
  return Boolean(getAuthToken());
}

export function saveAuth(result, username) {
  localStorage.setItem("authToken", result.token);
  localStorage.setItem("userID", result.userID || "");
  localStorage.setItem("username", username || "");
  localStorage.setItem("role", result.role || "");
}

export function clearAuth() {
  AUTH_KEYS.forEach((key) => localStorage.removeItem(key));
}
