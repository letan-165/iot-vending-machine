// js/product.js
let cart = [];

document.addEventListener("DOMContentLoaded", () => {
  // 1. Khởi tạo Header chung
  initHeader("Sẵn sàng");

  // 2. Render danh sách sản phẩm
  renderProducts();
});

// Hàm render sản phẩm ra màn hình
function renderProducts() {
  const grid = document.getElementById("productGrid");
  if (!grid) return;

  grid.innerHTML = SAMPLE_DATA.products
    .map((p) => {
      const cartItem = cart.find((c) => c.id === p.id);
      const qty = cartItem ? cartItem.qty : 0;
      return `
            <div class="col" id="product-${p.id}">
                <div class="card h-100 border-0 shadow-sm">
                    <img src="${p.image}" class="card-img-top p-2" style="height: 100px; object-fit: contain;">
                    <div class="card-body p-2 text-center">
                        <div class="small fw-bold text-truncate">${p.name}</div>
                        <div class="text-primary small fw-bold">${formatMoney(p.price)}</div>
                        <div class="btn-group btn-group-sm w-100 mt-2">
                            <button class="btn btn-outline-secondary" onclick="changeQty(${p.id}, -1)" ${qty === 0 ? "disabled" : ""}><i class="bi bi-dash"></i></button>
                            <button class="btn btn-light disabled fw-bold">${qty}</button>
                            <button class="btn btn-outline-secondary" onclick="changeQty(${p.id}, 1)" ${qty >= p.quantity ? "disabled" : ""}><i class="bi bi-plus"></i></button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    })
    .join("");
}

function changeQty(id, delta) {
  const product = SAMPLE_DATA.products.find((p) => p.id === id);
  let item = cart.find((c) => c.id === id);

  if (!item && delta > 0) {
    cart.push({ ...product, qty: 1 });
  } else if (item) {
    item.qty += delta;
    if (item.qty <= 0) cart = cart.filter((c) => c.id !== id);
  }

  renderProducts();
  updateCartUI();
}

function updateCartUI() {
  const total = cart.reduce((sum, c) => sum + c.price * c.qty, 0);
  document.getElementById("cartTotalTop").textContent = formatMoney(total);
  document.getElementById("createOrderBtn").disabled = cart.length === 0;
}

function formatMoney(amount) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
}

function createOrder() {
  if (cart.length === 0) return;

  const total = cart.reduce((sum, c) => sum + c.price * c.qty, 0);
  const orderData = {
    id: "1004", // Bạn có thể thay bằng ID động
    items: cart,
    total: total,
  };

  // Lưu vào localStorage để trang payment lấy dữ liệu
  localStorage.setItem("currentOrder", JSON.stringify(orderData));

  // Chuyển trang
  window.location.href = "payment.html";
}
