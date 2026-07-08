import { getAuthToken } from "../js/shared/session.js";

// const API_BASE_URL = "http://localhost:8080/api";
const API_BASE_URL =
  "https://p01--iot-vending-machine--9w4j25rlpvhz.code.run/api";

export async function request(path, options = {}) {
  const { method = "GET", headers = {}, body, includeAuth = true } = options;

  const requestHeaders = {
    accept: "*/*",
    ...headers,
  };

  if (includeAuth) {
    const token = getAuthToken();
    if (token) {
      requestHeaders.Authorization = `Bearer ${token}`;
    }
  }

  const requestOptions = { method, headers: requestHeaders };
  if (body !== undefined) {
    requestOptions.body =
      typeof body === "string" || body instanceof FormData
        ? body
        : JSON.stringify(body);
    if (!(body instanceof FormData) && !requestHeaders["Content-Type"]) {
      requestHeaders["Content-Type"] = "application/json";
    }
  }

  const response = await fetch(`${API_BASE_URL}${path}`, requestOptions);
  const data = await response.json().catch(() => null);

  if (!response.ok) {
    throw new Error(
      data?.message ||
        data?.error ||
        `Request failed with status ${response.status}`,
    );
  }

  return data?.result ?? data;
}
