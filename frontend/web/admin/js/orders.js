import { getOrders } from "../api/service/order.service.js";
import { createPageShell } from "./shared/page.js";
import {
  formatDateTime,
  formatMoney,
  getStatusBadge,
  maskId,
} from "./shared/format.js";
import { renderTable } from "./shared/table.js";

const page = document.body.dataset.page;
const { mount, showAlert, hideAlert } = createPageShell({
  page,
  alertId: "ordersAlert",
});

document.addEventListener("DOMContentLoaded", () => mount(loadOrders));

async function loadOrders() {
  try {
    const orders = await getOrders();
    const items = orders || [];

    renderSummary(buildSummary(items));
    renderOrdersTable(items);
    renderMachineRevenueTable(buildMachineRevenue(items));
    hideAlert();
  } catch (error) {
    renderSummary(createEmptySummary());
    renderOrdersTable([]);
    renderMachineRevenueTable([]);
    showAlert(error.message || "Không thể tải danh sách đơn hàng.");
  }
}

function buildSummary(items) {
  const totalOrders = items.length;
  const totalRevenue = items.reduce(
    (sum, order) => sum + Number(order?.total || 0),
    0,
  );
  const statusCounts = items.reduce((acc, order) => {
    const status = String(order?.status || "UNKNOWN");
    acc[status] = (acc[status] || 0) + 1;
    return acc;
  }, {});

  return {
    totalOrders,
    totalRevenue,
    cancelledOrders: statusCounts.CANCELLED || 0,
    completedOrders: statusCounts.COMPLETED || 0,
  };
}

function createEmptySummary() {
  return {
    totalOrders: 0,
    totalRevenue: 0,
    cancelledOrders: 0,
    completedOrders: 0,
  };
}

function buildMachineRevenue(items) {
  const grouped = items.reduce((acc, order) => {
    const machineId = String(order?.machineId || "").trim();
    if (!machineId) return acc;

    if (!acc[machineId]) {
      acc[machineId] = {
        machineId,
        totalRevenue: 0,
        totalOrders: 0,
      };
    }

    acc[machineId].totalRevenue += Number(order?.total || 0);
    acc[machineId].totalOrders += 1;
    return acc;
  }, {});

  return Object.values(grouped).sort((a, b) => b.totalRevenue - a.totalRevenue);
}

function renderSummary(summary) {
  setText("orderTotalCountMini", formatCount(summary.totalOrders));
  setText("orderRevenueMini", formatMoney(summary.totalRevenue));
  setText("orderCancelledMini", formatCount(summary.cancelledOrders));
  setText("orderCompletedMini", formatCount(summary.completedOrders));
}

function renderOrdersTable(items) {
  renderTable("ordersTable", {
    columns: ["ID", "machineId", "Ngày", "Tổng tiền", "Trạng thái"],
    rows: items.map((order) => [
      maskId(order.id),
      maskId(order.machineId),
      formatDateTime(order.date),
      formatMoney(order.total),
      getStatusBadge(order.status),
    ]),
    emptyMessage: "Chưa có dữ liệu đơn hàng.",
  });
}

function renderMachineRevenueTable(items) {
  renderTable("machineRevenueTable", {
    columns: ["machineId", "Tổng doanh thu", "Tổng đơn"],
    rows: items.map((item) => [
      maskId(item.machineId),
      formatMoney(item.totalRevenue),
      formatCount(item.totalOrders),
    ]),
    emptyMessage: "Chưa có dữ liệu theo machineId.",
  });
}

function setText(id, value) {
  const el = document.getElementById(id);
  if (el) el.textContent = value;
}

function formatCount(value) {
  return Number(value || 0).toLocaleString("vi-VN");
}
