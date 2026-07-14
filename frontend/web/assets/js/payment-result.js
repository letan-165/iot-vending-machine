const resultTitle = document.getElementById("resultTitle");
const resultIcon = document.getElementById("resultIcon");
const resultOrderId = document.getElementById("resultOrderId");

document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const status = String(params.get("status") || "").toUpperCase();
  const orderId = params.get("orderId") || "--";

  if (resultOrderId) {
    resultOrderId.textContent = `Mã đơn: ${orderId}`;
  }

  if (status === "PAID") {
    setResult("Thanh toán thành công", "Cảm ơn bạn đã mua hàng", "check-circle", "success");
  } else {
    setResult("Thanh toán thất bại", "Cảm ơn bạn đã mua hàng", "x-circle", "danger");
  }
});

function setResult(title, subtitle, icon, variant) {
  if (resultTitle) resultTitle.textContent = title;
  if (resultIcon) {
    resultIcon.className = `payment-logo mx-auto mb-4 bg-${variant}`;
    resultIcon.innerHTML = `<i class="bi bi-${icon}"></i>`;
  }

  const subtitleEl = document.querySelector(".lead");
  if (subtitleEl) subtitleEl.textContent = subtitle;
}
