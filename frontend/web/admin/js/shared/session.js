const AUTH_KEYS = ["authToken", "userID", "username"];

export function getAuthToken() {
  return localStorage.getItem("authToken") || "";
}

export function isAuthenticated() {
  return Boolean(getAuthToken());
}

export function saveAuth(result, username) {
  localStorage.setItem("authToken", result.token);
  localStorage.setItem("userID", result.userID || "");
  localStorage.setItem("username", username || "");
}

export function clearAuth() {
  AUTH_KEYS.forEach((key) => localStorage.removeItem(key));
}
