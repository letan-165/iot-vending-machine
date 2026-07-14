import {
  getOrderByIdPublic,
  updateOrderStatusPublic,
} from "./user-payment.service.js";

const paymentAlert = document.getElementById("paymentAlert");
const paymentAmountText = document.getElementById("paymentAmountText");
const paidButton = document.getElementById("paidButton");
const cancelButton = document.getElementById("cancelButton");

let currentOrderId = "";

document.addEventListener("DOMContentLoaded", () => {
  const orderId = getOrderIdFromPath();
  if (!orderId) {
    showAlert("Thiếu orderId trên đường dẫn.");
    setButtonsDisabled(true);
    return;
  }

  loadOrder(orderId);
});

paidButton?.addEventListener("click", () => submitStatus("PAID"));
cancelButton?.addEventListener("click", () => submitStatus("CANCELLED"));

async function loadOrder(orderId) {
  setButtonsDisabled(true);
  hideAlert();

  try {
    const order = await getOrderByIdPublic(orderId);
    currentOrderId = String(order?.id || orderId);
    paymentAmountText.textContent = `Số tiền cần thanh toán: ${formatVnd(order?.total)} VND`;
    setButtonsDisabled(false);
  } catch (error) {
    currentOrderId = "";
    paymentAmountText.textContent = "Số tiền cần thanh toán: -- VND";
    showAlert(error.message || "Không thể tải đơn hàng.");
  }
}

async function submitStatus(status) {
  const orderId = currentOrderId || getOrderIdFromPath();
  if (!orderId) {
    showAlert("Thiếu orderId trên đường dẫn.");
    return;
  }

  setButtonsDisabled(true);
  hideAlert();

  try {
    await updateOrderStatusPublic(orderId, status);
  } catch (error) {
    showAlert(error.message || "Không thể cập nhật trạng thái thanh toán.");
  } finally {
    setButtonsDisabled(false);
  }
}

function getOrderIdFromPath() {
  const { pathname, search } = window.location;
  const marker = "user-payment.html";
  const markerIndex = pathname.lastIndexOf(marker);

  if (markerIndex >= 0) {
    const suffix = pathname.slice(markerIndex + marker.length).replace(/^\/+/, "");
    if (suffix) {
      return decodeURIComponent(suffix.split("/")[0]);
    }
  }

  return new URLSearchParams(search).get("orderId") || "";
}

function setButtonsDisabled(isDisabled) {
  [paidButton, cancelButton].forEach((button) => {
    if (button) button.disabled = isDisabled;
  });
}

function showAlert(message, variant = "danger") {
  if (!paymentAlert) return;
  paymentAlert.className = `alert alert-${variant}`;
  paymentAlert.textContent = message;
  paymentAlert.classList.remove("d-none");
}

function hideAlert() {
  if (!paymentAlert) return;
  paymentAlert.textContent = "";
  paymentAlert.className = "alert d-none";
}

function formatVnd(value) {
  return Number(value || 0).toLocaleString("vi-VN");
}
