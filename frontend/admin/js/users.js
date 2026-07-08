import { createUser, getUsers, updateUser } from "../api/service/user.service.js";
import { createPageShell } from "./shared/page.js";
import { escapeAttr, escapeHtml, maskId } from "./shared/format.js";
import { actionButton, actionButtons, renderTable } from "./shared/table.js";
import { getFormValue, openModal, setFormValue, setText } from "./shared/modal.js";

const page = document.body.dataset.page;
const userModal = openModal(document.getElementById("userModal"));
const { mount, showAlert, hideAlert } = createPageShell({ page, alertId: "userAlert" });

let users = [];

document.addEventListener("DOMContentLoaded", () =>
  mount(loadUsers, { openUserModal, submitUserForm }),
);

async function loadUsers() {
  try {
    users = await getUsers();
    renderUsersTable(users);
    hideAlert();
  } catch (error) {
    users = [];
    renderUsersTable([]);
    showAlert(error.message || "Không thể tải danh sách người dùng.");
  }
}

function renderUsersTable(items) {
  renderTable("usersTable", {
    columns: ["ID", "Username", "Full name", "Email", "Role", "Thao tác"],
    rows: items.map((user) => [
      maskId(user.id),
      escapeHtml(user.username),
      escapeHtml(user.fullName),
      escapeHtml(user.email),
      escapeHtml(user.role),
      actionButtons([actionButton("Sửa", "bi-pencil", "primary", `openUserModal('${escapeAttr(user.id)}')`)]),
    ]),
    emptyMessage: "Chưa có dữ liệu người dùng.",
  });
}

function openUserModal(id = "") {
  const user = users.find((item) => String(item.id) === String(id)) || null;

  setFormValue("userId", user?.id || "");
  setFormValue("userUsername", user?.username || "");
  setFormValue("userPassword", "");
  setFormValue("userFullName", user?.fullName || "");
  setFormValue("userEmail", user?.email || "");
  setFormValue("userRole", user?.role || "ADMIN");
  setText("userModalTitle", user ? "Sửa người dùng" : "Thêm mới");

  document.getElementById("userUsernameGroup")?.classList.toggle("d-none", !!user);
  document.getElementById("userPasswordGroup")?.classList.toggle("d-none", !!user);
  userModal?.show();
}

async function submitUserForm() {
  const id = getFormValue("userId");
  const payload = {
    fullName: getFormValue("userFullName"),
    email: getFormValue("userEmail"),
    role: getFormValue("userRole") || "ADMIN",
  };

  try {
    if (id) {
      await updateUser(id, payload);
    } else {
      const createPayload = {
        username: getFormValue("userUsername"),
        password: getFormValue("userPassword"),
        ...payload,
      };

      if (!createPayload.username || !createPayload.password) {
        showAlert("Vui lòng nhập username và password khi tạo mới.");
        return;
      }

      await createUser(createPayload);
    }

    userModal?.hide();
    await loadUsers();
  } catch (error) {
    showAlert(error.message || "Không thể lưu người dùng.");
  }
}
