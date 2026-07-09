import { request } from "../apiclient.js";

export function getProducts() {
  return request("/products");
}

export function createProduct(payload) {
  return request("/products", {
    method: "POST",
    body: payload,
  });
}

export function updateProduct(id, payload) {
  return request(`/products/${id}`, {
    method: "PUT",
    body: payload,
  });
}
