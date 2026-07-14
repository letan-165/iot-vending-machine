import { getDashboard } from "../api/service/dashboard.service.js";
import { createPageShell } from "./shared/page.js";
import { escapeHtml, formatDateTime, formatMoney, getStatusBadge, maskId } from "./shared/format.js";
import { renderTable } from "./shared/table.js";

const page = document.body.dataset.page;
const { mount, showAlert, hideAlert } = createPageShell({ page, alertId: "dashboardAlert" });

document.addEventListener("DOMContentLoaded", () => mount(loadDashboard));

async function loadDashboard() {
  try {
    const dashboard = await getDashboard();

    renderSummaryCards(dashboard);
    renderOrdersTable(dashboard.orderToday || []);
    renderMachinesTable(dashboard.machines || []);
    hideAlert();
  } catch (error) {
    renderSummaryCards();
    renderOrdersTable([]);
    renderMachinesTable([]);
    showAlert(error.message || "Không thể tải dữ liệu dashboard.");
  }
}

function renderSummaryCards(dashboard = {}) {
  setCardText("dashboardRevenueTotal", `Tổng: ${formatMoney(dashboard.totalRevenueToday || 0)}`);
  setCardText("dashboardOrderTotal", `Tổng: ${formatCount(dashboard.totalOrderToday || 0)}`);
  setCardText("dashboardProductTotal", `Tổng: ${formatCount(dashboard.totalProductAvailable || 0)}`);
  setCardText("dashboardMachineTotal", `Tổng: ${formatCount(dashboard.totalMachineActive || 0)}`);
}

function renderOrdersTable(items) {
  renderTable("recentOrders", {
    columns: ["ID", "Ngày", "Tổng tiền", "Trạng thái"],
    rows: items.map((order) => [
      maskId(order.id),
      escapeHtml(formatDateTime(order.date)),
      escapeHtml(formatMoney(order.total)),
      getStatusBadge(order.status),
    ]),
    emptyMessage: "Chưa có dữ liệu đơn hàng hôm nay.",
  });
}

function renderMachinesTable(items) {
  renderTable("machineSummary", {
    columns: ["ID", "Tổng SP", "Vị trí", "Trạng thái"],
    rows: items.map((machine) => [
      maskId(machine.id),
      escapeHtml(formatCount(getMachineTotalProducts(machine))),
      escapeHtml(machine.location || "--"),
      getStatusBadge(machine.status),
    ]),
    emptyMessage: "Chưa có dữ liệu máy.",
  });
}

function setCardText(id, text) {
  const element = document.getElementById(id);
  if (element) {
    element.textContent = text;
  }
}

function formatCount(value) {
  return Number(value || 0).toLocaleString("vi-VN");
}

function getMachineTotalProducts(machine) {
  return (machine?.products || []).reduce(
    (total, product) => total + Number(product?.quantity || 0),
    0,
  );
}
