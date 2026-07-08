import { getMachineById, updateMachineProducts } from "../api/service/machine.service.js";
import { getLogMachine } from "../api/service/machine-log.service.js";
import { getProducts } from "../api/service/product.service.js";
import { createPageShell } from "./shared/page.js";
import {
  escapeAttr,
  escapeHtml,
  formatDateTime,
  formatMoney,
  getStatusBadge,
  maskId,
  safeImage,
} from "./shared/format.js";
import { getFormValue, openModal, setFormValue, setText } from "./shared/modal.js";
import { actionButton, actionButtons, renderTable } from "./shared/table.js";

const page = document.body.dataset.page;
const params = new URLSearchParams(window.location.search);
const machineId = params.get("machineId") || "";
const inventoryModal = openModal(document.getElementById("inventoryModal"));
const { mount, showAlert, hideAlert } = createPageShell({
  page,
  alertId: "inventoryAlert",
  alertTarget: ".main-content",
});

let currentMachine = null;
let allProducts = [];
let pendingInventory = [];

document.addEventListener("DOMContentLoaded", () =>
  mount(loadInventory, {
    openInventoryModal: openInventoryModalUI,
    submitInventoryForm,
    savePendingInventory,
    removePendingInventory,
    queueOutport,
  }).then(bindQuantityGuard),
);

function bindQuantityGuard() {
  const quantityInput = document.getElementById("inventoryQuantity");
  quantityInput?.addEventListener("keydown", (event) => {
    if (event.key === "-" || event.key === "Subtract") event.preventDefault();
  });
  quantityInput?.addEventListener("input", () => {
    if (Number(quantityInput.value) < 0) quantityInput.value = "";
  });
  renderPendingInventory();
}

async function loadInventory() {
  if (!machineId) {
    showAlert("Thiếu machineId trên đường dẫn.");
    renderEmptyState();
    return;
  }

  try {
    const [machineResult, logResult, productsResult] = await Promise.allSettled([
      getMachineById(machineId),
      getLogMachine(machineId),
      getProducts(),
    ]);

    if (machineResult.status !== "fulfilled") throw machineResult.reason;
    if (logResult.status !== "fulfilled") throw logResult.reason;

    currentMachine = machineResult.value;
    allProducts = productsResult.status === "fulfilled" ? productsResult.value || [] : [];

    renderMachineHeader(currentMachine);
    renderMachineProducts(currentMachine?.products || []);
    renderMachineLogs(logResult.value?.products || []);
    hideAlert();
  } catch (error) {
    currentMachine = null;
    allProducts = [];
    renderEmptyState();
    showAlert(error.message || "Không thể tải dữ liệu tồn kho.");
  }
}

function renderMachineHeader(machine) {
  setText("machineTitle", `Tồn kho máy ${maskId(machine?.id || machineId)}`);
  const meta = document.getElementById("machineMeta");
  if (meta) {
    meta.innerHTML = `
      <span class="badge text-bg-primary me-2">${escapeHtml(machine?.status || "--")}</span>
      <span class="text-muted">${escapeHtml(machine?.location || "")}</span>
    `;
  }
}

function renderMachineProducts(products) {
  renderTable("inventoryTable", {
    columns: ["ID", "Ảnh", "Tên", "Giá", "Trạng thái", "Số lượng", "Thao tác"],
    rows: products.map((product) => [
      maskId(product.id),
      `<img class="product-img" src="${safeImage(product.image)}" alt="${escapeHtml(product.name)}">`,
      `<div class="fw-bold">${escapeHtml(product.name)}</div>`,
      formatMoney(product.price),
      getStatusBadge(product.status),
      escapeHtml(product.quantity),
      actionButtons([
        actionButton("Nhập", "bi-plus-circle", "success", `openInventoryModal('IMPORT', '${escapeAttr(product.id)}')`),
        actionButton("Điều chỉnh", "bi-sliders", "primary", `openInventoryModal('ADJUSTMENT', '${escapeAttr(product.id)}')`),
        actionButton("Xuất", "bi-box-arrow-right", "danger", `queueOutport('${escapeAttr(product.id)}', ${Number(product.quantity) || 0})`),
      ]),
    ]),
    emptyMessage: "Máy chưa có sản phẩm.",
  });
}

function renderMachineLogs(logProducts) {
  renderTable("inventoryLogs", {
    columns: ["ID", "Tên", "Số lượng", "Loại", "Ngày"],
    rows: logProducts.map((item) => [
      maskId(item.id),
      escapeHtml(item.name),
      escapeHtml(item.quantity),
      getStatusBadge(item.type),
      escapeHtml(formatDateTime(item.date)),
    ]),
    emptyMessage: "Chưa có lịch sử tồn kho.",
  });
}

function openInventoryModalUI(mode = "IMPORT", productId = "") {
  if (!currentMachine) {
    showAlert("Chưa có dữ liệu máy để thao tác tồn kho.");
    return;
  }

  const existingIds = new Set((currentMachine.products || []).map((item) => String(item.id)));
  const products = mode === "NEW" ? allProducts.filter((item) => !existingIds.has(String(item.id))) : currentMachine.products || [];
  const productSelect = document.getElementById("inventoryProductId");

  if (mode === "NEW" && !products.length) {
    showAlert("Không còn sản phẩm mới nào để nhập.");
    return;
  }

  if (productSelect) {
    productSelect.innerHTML = products.length
      ? products
          .map(
            (product) =>
              `<option value="${escapeAttr(product.id)}"${String(product.id) === String(productId) ? " selected" : ""}>${escapeHtml(product.name)}</option>`,
          )
          .join("")
      : `<option value="">Chưa có sản phẩm</option>`;
  }

  setFormValue("inventoryType", mode === "NEW" ? "IMPORT" : mode);
  setFormValue("inventoryMode", mode === "NEW" ? "NEW" : "EXISTING");
  setFormValue("inventoryQuantity", "");
  setText(
    "inventoryModalTitle",
    mode === "NEW" ? "Nhập sản phẩm mới" : mode === "IMPORT" ? "Thêm nhập vào danh sách" : "Thêm điều chỉnh vào danh sách",
  );

  if (document.getElementById("inventoryModalHint")) {
    setText(
      "inventoryModalHint",
      mode === "NEW"
        ? "Chỉ hiển thị sản phẩm chưa có trong máy. Khi thêm sẽ được đẩy vào danh sách chờ với type IMPORT."
        : "",
    );
  }

  inventoryModal?.show();
}

function submitInventoryForm() {
  const productId = getFormValue("inventoryProductId");
  const quantity = Number(getFormValue("inventoryQuantity"));
  const type = getFormValue("inventoryType") || "IMPORT";
  const mode = getFormValue("inventoryMode") || "EXISTING";
  const productSource = mode === "NEW" ? allProducts : currentMachine?.products || [];
  const product = productSource.find((item) => String(item.id) === String(productId));

  if (!productId || Number.isNaN(quantity) || quantity < 0) {
    showAlert("Vui lòng chọn sản phẩm và nhập số lượng không âm.");
    return;
  }

  pendingInventory.push({
    id: productId,
    name: product?.name || productId,
    quantity,
    type,
  });

  inventoryModal?.hide();
  setText("inventoryModalHint", "");
  renderPendingInventory();
}

function queueOutport(productId, quantity) {
  if (!productId || Number.isNaN(Number(quantity)) || Number(quantity) <= 0) {
    showAlert("Sản phẩm phải có số lượng lớn hơn 0 để xuất.");
    return;
  }

  const product = (currentMachine?.products || []).find((item) => String(item.id) === String(productId));
  pendingInventory.push({
    id: productId,
    name: product?.name || productId,
    quantity: -Math.abs(Number(quantity)),
    type: "OUTPORT",
  });
  renderPendingInventory();
}

async function savePendingInventory() {
  if (!pendingInventory.length) {
    showAlert("Chưa có dữ liệu nào trong danh sách chờ lưu.");
    return;
  }

  try {
    await updateMachineProducts(machineId, pendingInventory.map(({ name, ...item }) => item));
    pendingInventory = [];
    renderPendingInventory();
    await loadInventory();
  } catch (error) {
    showAlert(error.message || "Không thể lưu danh sách tồn kho.");
  }
}

function renderPendingInventory() {
  renderTable("pendingInventoryTable", {
    columns: ["ID", "Tên", "Số lượng", "Loại", "Thao tác"],
    rows: pendingInventory.map((item, index) => [
      maskId(item.id),
      escapeHtml(item.name),
      escapeHtml(item.quantity),
      getStatusBadge(item.type),
      actionButtons([actionButton("Xóa", "bi-trash", "danger", `removePendingInventory(${index})`)]),
    ]),
    emptyMessage: "Chưa có mục nào trong danh sách chờ lưu.",
  });
}

function removePendingInventory(index) {
  pendingInventory.splice(index, 1);
  renderPendingInventory();
}

function renderEmptyState() {
  renderMachineProducts([]);
  renderMachineLogs([]);
  renderPendingInventory();
}
