// js/header.js
function updateClock() {
  const el = document.getElementById("header-right");
  if (el) {
    el.textContent = new Date().toLocaleTimeString("vi-VN", {
      hour: "2-digit",
      minute: "2-digit",
    });
  }
}

function initHeader(status = "Sẵn sàng") {
  // 1. Cập nhật trạng thái
  const elLeft = document.getElementById("header-left");
  if (elLeft) {
    elLeft.textContent = `VM-HCM-01 - ${status}`;
  }

  // 2. Chạy đồng hồ lần đầu và thiết lập interval
  updateClock();
  setInterval(updateClock, 60000);
}
