import { createProduct, getProducts, updateProduct } from "../api/service/product.service.js";
import { createPageShell } from "./shared/page.js";
import { escapeAttr, escapeHtml, formatMoney, getStatusBadge, safeImage } from "./shared/format.js";
import { actionButton, actionButtons, renderTable } from "./shared/table.js";
import { getFormValue, openModal, setFormValue, setText } from "./shared/modal.js";

const page = document.body.dataset.page;
const productModal = openModal(document.getElementById("productModal"));
const { mount, showAlert, hideAlert } = createPageShell({
  page,
  alertId: "productAlert",
});

let products = [];

document.addEventListener("DOMContentLoaded", () =>
  mount(loadProducts, { openProductModal, submitProductForm }),
);

async function loadProducts() {
  try {
    products = await getProducts();
    renderProductsTable(products);
    hideAlert();
  } catch (error) {
    products = [];
    renderProductsTable([]);
    showAlert(error.message || "Không thể tải danh sách sản phẩm.");
  }
}

function renderProductsTable(items) {
  renderTable("productsTable", {
    columns: ["ID", "Ảnh", "Tên", "Giá", "Trạng thái", "Thao tác"],
    rows: items.map((product) => [
      `#${escapeHtml(product.id)}`,
      `<img class="product-img" src="${safeImage(product.image)}" alt="${escapeHtml(product.name)}">`,
      `<div class="fw-bold">${escapeHtml(product.name)}</div>`,
      formatMoney(product.price),
      getStatusBadge(product.status),
      actionButtons([
        actionButton("Sửa", "bi-pencil", "primary", `openProductModal('${escapeAttr(product.id)}')`),
      ]),
    ]),
    emptyMessage: "Chưa có dữ liệu sản phẩm.",
  });
}

function openProductModal(id = "") {
  const product = products.find((item) => String(item.id) === String(id)) || null;

  setFormValue("productId", product?.id || "");
  setFormValue("productName", product?.name || "");
  setFormValue("productPrice", product?.price ?? "");
  setFormValue("productImage", product?.image || "");
  setFormValue("productStatus", product?.status || "AVAILABLE");
  setText("productModalTitle", product ? "Cập nhật sản phẩm" : "Thêm sản phẩm mới");

  productModal?.show();
}

async function submitProductForm() {
  const id = getFormValue("productId");
  const payload = {
    name: getFormValue("productName"),
    price: Number(getFormValue("productPrice")),
    image: getFormValue("productImage"),
    status: getFormValue("productStatus"),
  };

  try {
    await (id ? updateProduct(id, payload) : createProduct(payload));
    productModal?.hide();
    await loadProducts();
  } catch (error) {
    showAlert(error.message || "Không thể lưu sản phẩm.");
  }
}
