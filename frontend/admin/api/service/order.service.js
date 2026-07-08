import { request } from "../apiclient.js";

export function getOrders() {
  return request("/orders");
}

export function getOrderById(id) {
  return request(`/orders/${id}`);
}

export function createOrder(payload) {
  return request("/orders", {
    method: "POST",
    body: payload,
  });
}

export function updateOrderStatus(id, status) {
  return request(`/orders/pending/${id}/status/${status}`, {
    method: "PUT",
  });
}

export function completeOrder(id) {
  return request(`/orders/completed/${id}`, {
    method: "PUT",
  });
}

export function getOrderStatus(id) {
  return request(`/orders/${id}/status`);
}
