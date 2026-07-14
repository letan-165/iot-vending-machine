import { request } from "../apiclient.js";

export function getDashboard() {
  return request("/dashboard");
}
