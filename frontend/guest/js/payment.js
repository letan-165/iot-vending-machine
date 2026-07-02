// js/payment.js
let qrTimer = null;

document.addEventListener("DOMContentLoaded", () => {
  // 1. Khởi tạo Header chung
  initHeader("Sẵn sàng");

  // 2. Load dữ liệu đơn hàng
  loadOrderData();

  // 3. Bắt đầu đếm ngược
  startQrTimer();
});

function loadOrderData() {
  const savedOrder = JSON.parse(localStorage.getItem("currentOrder"));

  if (!savedOrder) {
    window.location.href = "product.html";
    return;
  }

  // Hiển thị thông tin
  document.getElementById("orderCodeLabel").textContent = "#" + savedOrder.id;
  document.getElementById("orderTotalLabel").textContent = formatMoney(
    savedOrder.total,
  );
  document.getElementById("qrAmountLabel").textContent = formatMoney(
    savedOrder.total,
  );

  // Hiển thị danh sách sản phẩm
  const listContainer = document.getElementById("orderItemsList");
  if (listContainer) {
    listContainer.innerHTML = savedOrder.items
      .map(
        (item) => `
            <div class="d-flex justify-content-between small text-muted">
                <span>${item.name} × ${item.qty}</span>
                <span>${formatMoney(item.price * item.qty)}</span>
            </div>
        `,
      )
      .join("");
  }
}

function startQrTimer() {
  let seconds = 300;
  const el = document.getElementById("qrCountdown");

  qrTimer = setInterval(() => {
    seconds--;
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    if (el)
      el.textContent = `${String(m).padStart(2, "0")}:${String(s).padStart(2, "0")}`;

    if (seconds <= 0) {
      clearInterval(qrTimer);
      alert("Đã hết thời gian thanh toán!");
      window.location.href = "product.html";
    }
  }, 1000);
}

function confirmPayment() {
  clearInterval(qrTimer);
  const order = JSON.parse(localStorage.getItem("currentOrder"));
  // Chuyển sang receiver, truyền kèm order
  window.location.href = `receiver.html?order=${encodeURIComponent(JSON.stringify(order))}`;
}

function formatMoney(amount) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
}
