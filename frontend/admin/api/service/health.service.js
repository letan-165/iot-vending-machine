import { request } from "../apiclient.js";

export function getHealth() {
  return request("/health", {
    includeAuth: false,
  });
}
