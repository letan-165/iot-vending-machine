import { createMachine, getMachines, updateMachine } from "../api/service/machine.service.js";
import { createPageShell } from "./shared/page.js";
import { escapeAttr, escapeHtml, getStatusBadge, maskId } from "./shared/format.js";
import { actionButton, actionButtons, renderTable } from "./shared/table.js";
import { getFormValue, openModal, setFormValue, setText } from "./shared/modal.js";

const page = document.body.dataset.page;
const machineModal = openModal(document.getElementById("machineModal"));
const { mount, showAlert, hideAlert } = createPageShell({ page, alertId: "machineAlert" });

let machines = [];

document.addEventListener("DOMContentLoaded", () =>
  mount(loadMachines, {
    openInventoryForMachine,
    openMachineModal,
    submitMachineForm,
  }),
);

async function loadMachines() {
  try {
    machines = await getMachines();
    renderMachinesTable(machines);
    hideAlert();
  } catch (error) {
    machines = [];
    renderMachinesTable([]);
    showAlert(error.message || "Không thể tải danh sách máy bán hàng.");
  }
}

function renderMachinesTable(items) {
  renderTable("machinesTable", {
    columns: ["ID", "Vị trí", "SẢN PHẨM", "Trạng thái", "Thao tác"],
    rows: items.map((machine) => {
      const count = machine.products?.length || 0;

      return [
        maskId(machine.id),
        escapeHtml(machine.location),
        actionButtons([
          actionButton(
            `Hiện có: ${count}`,
            "bi-box-seam",
            "primary",
            `openInventoryForMachine('${escapeAttr(machine.id)}')`,
          ),
        ]),
        getStatusBadge(machine.status),
        actionButtons([
          actionButton("Sửa", "bi-pencil", "primary", `openMachineModal('${escapeAttr(machine.id)}')`),
        ]),
      ];
    }),
    emptyMessage: "Chưa có dữ liệu máy bán hàng.",
  });
}

function openInventoryForMachine(machineId) {
  window.location.href = `inventory.html?machineId=${encodeURIComponent(machineId)}`;
}

function openMachineModal(machineId = "") {
  const machine = machines.find((item) => String(item.id) === String(machineId)) || null;

  setFormValue("machineId", machine?.id || "");
  setFormValue("machineLocation", machine?.location || "");
  setFormValue("machineStatus", machine?.status || "ACTIVE");
  setText("machineModalTitle", machine ? "Sửa máy" : "Thêm máy");

  machineModal?.show();
}

async function submitMachineForm() {
  const id = getFormValue("machineId");
  const payload = {
    location: getFormValue("machineLocation"),
    status: getFormValue("machineStatus"),
  };

  try {
    if (id) {
      await updateMachine(id, payload);
    } else {
      await createMachine(payload);
    }
    machineModal?.hide();
    await loadMachines();
  } catch (error) {
    showAlert(error.message || "Không thể lưu thông tin máy.");
  }
}
