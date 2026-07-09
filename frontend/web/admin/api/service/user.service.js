import { request } from "../apiclient.js";

export function login(payload) {
  return request("/users/login", {
    method: "POST",
    includeAuth: false,
    body: payload,
  }).then((result) => {
    if (!result?.token) {
      throw new Error("Dang nhap that bai. Vui long kiem tra lai tai khoan.");
    }

    return result;
  });
}

export function getUsers() {
  return request("/users");
}

export function createUser(payload) {
  return request("/users", {
    method: "POST",
    body: payload,
  });
}

export function updateUser(id, payload) {
  return request(`/users/${id}`, {
    method: "PUT",
    body: payload,
  });
}

export function deleteUser(id) {
  return request(`/users/${id}`, {
    method: "DELETE",
  });
}
