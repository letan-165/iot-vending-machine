import { getOrders } from "../api/service/order.service.js";
import { createPageShell } from "./shared/page.js";
import { escapeHtml, formatDateTime, formatMoney, maskId, getStatusBadge } from "./shared/format.js";
import { renderTable } from "./shared/table.js";

const page = document.body.dataset.page;
const { mount, showAlert, hideAlert } = createPageShell({ page, alertId: "ordersAlert" });

document.addEventListener("DOMContentLoaded", () => mount(loadOrders));

async function loadOrders() {
  try {
    renderOrdersTable(await getOrders());
    hideAlert();
  } catch (error) {
    renderOrdersTable([]);
    showAlert(error.message || "Không thể tải danh sách đơn hàng.");
  }
}

function renderOrdersTable(items) {
  renderTable("ordersTable", {
    columns: ["ID", "Machine ID", "Ngày", "Tổng tiền", "Trạng thái", "Ngày thanh toán"],
    rows: items.map((order) => [
      maskId(order.id),
      maskId(order.machineId),
      escapeHtml(formatDateTime(order.date)),
      formatMoney(order.total),
      getStatusBadge(order.status),
      escapeHtml(formatDateTime(order.datePay)),
    ]),
    emptyMessage: "Chưa có dữ liệu đơn hàng.",
  });
}
