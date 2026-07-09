import { request } from "../apiclient.js";

export function getMachines() {
  return request("/machines");
}

export function getMachineById(id) {
  return request(`/machines/${id}`);
}

export function createMachine(payload) {
  return request("/machines", {
    method: "POST",
    body: payload,
  });
}

export function updateMachine(id, payload) {
  return request(`/machines/${id}`, {
    method: "PUT",
    body: payload,
  });
}

export function deleteMachine(id) {
  return request(`/machines/${id}`, {
    method: "DELETE",
  });
}

export function updateMachineProducts(id, payload) {
  return request(`/machines/${id}/product`, {
    method: "PUT",
    body: payload,
  });
}
