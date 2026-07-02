/**
 * js/receiver.js
 * Logic cho màn hình Nhận hàng
 */

document.addEventListener("DOMContentLoaded", () => {
  initHeader("Sẵn sàng");
  displayOrderInfo();
});

/**
 * Hàm lấy mã đơn hàng từ URL và hiển thị lên giao diện
 */
function displayOrderInfo() {
  const urlParams = new URLSearchParams(window.location.search);
  const orderDataRaw = urlParams.get("order");

  if (orderDataRaw) {
    try {
      // Giải mã và parse JSON từ URL
      const orderData = JSON.parse(decodeURIComponent(orderDataRaw));

      // Cập nhật lên HTML
      const orderCodeEl = document.getElementById("successOrderCode");
      if (orderCodeEl && orderData.id) {
        orderCodeEl.textContent = "#" + orderData.id;
      }
    } catch (error) {
      console.error("Lỗi khi đọc thông tin đơn hàng:", error);
    }
  }
}

/**
 * Hàm reset kiosk để quay về trang chủ
 */
function resetKiosk() {
  // Xóa đơn hàng cũ trong localStorage để sạch bộ nhớ trước khi mua lượt mới
  localStorage.removeItem("currentOrder");

  // Chuyển về trang chọn sản phẩm
  window.location.href = "product.html";
}
