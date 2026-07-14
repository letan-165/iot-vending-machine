import { request } from "../../admin/api/apiclient.js";

export function getOrderByIdPublic(id) {
  return request(`/orders/${id}`, { includeAuth: false });
}

export function updateOrderStatusPublic(id, status) {
  return request(`/orders/pending/${id}/status/${status}`, {
    method: "PUT",
    includeAuth: false,
  });
}
