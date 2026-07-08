const page = document.body.dataset.page;

const navItems = [
  {
    href: "dashboard.html",
    page: "dashboard",
    icon: "bi-speedometer2",
    label: "Dashboard",
  },
  {
    href: "products.html",
    page: "products",
    icon: "bi-cup-straw",
    label: "Sản phẩm",
  },
  {
    href: "orders.html",
    page: "orders",
    icon: "bi-receipt",
    label: "Đơn hàng",
  },
  { href: "users.html", page: "users", icon: "bi-people", label: "Tài khoản" },
  {
    href: "machines.html",
    page: "machines",
    icon: "bi-hdd-rack",
    label: "Máy bán nước",
  },
  {
    href: "reports.html",
    page: "reports",
    icon: "bi-bar-chart-line",
    label: "Báo cáo",
  },
];

document.addEventListener("DOMContentLoaded", () => {
  if (page === "login") {
    setupLogin();
    return;
  }

  renderSidebar();
  bindMobileMenuEvents();

  const renderers = {
    dashboard: renderDashboard,
    products: renderProducts,
    inventory: renderInventory,
    orders: renderOrders,
    users: renderUsers,
    machines: renderMachines,
    reports: renderReports,
  };

  renderers[page]?.();
});

function setupLogin() {
  const form = document.getElementById("loginForm");

  form?.addEventListener("submit", (event) => {
    event.preventDefault();
    window.location.href = "dashboard.html";
  });
}

function renderSidebar() {
  const sidebar = document.getElementById("sidebar");
  if (!sidebar) return;

  sidebar.innerHTML = `
    <div class="sidebar-header">
      <div class="brand">
        <div class="brand-icon"><i class="bi bi-hdd-rack"></i></div>
        <div>
          <div class="brand-title">Vending Admin</div>
          <div class="brand-subtitle">Admin workspace</div>
        </div>
      </div>

      <button
        type="button"
        class="mobile-menu-btn"
        aria-label="Mở menu"
        aria-controls="sidebarNav"
        aria-expanded="false"
      >
        <i class="bi bi-list"></i>
        <span>Menu</span>
      </button>
    </div>

    <nav id="sidebarNav" class="nav flex-column">
      ${navItems.map(renderNavItem).join("")}
    </nav>

    <div class="sidebar-footer">
      <button class="btn btn-outline-light btn-sm w-100" onclick="logout()">
        <i class="bi bi-box-arrow-right me-1"></i> Đăng xuất
      </button>
    </div>
  `;
}

function renderNavItem(item) {
  const activeClass = page === item.page ? "active" : "";

  return `
    <a class="nav-link ${activeClass}" href="${item.href}">
      <i class="bi ${item.icon}"></i>
      <span>${item.label}</span>
    </a>
  `;
}

function toggleMobileMenu() {
  const sidebar = document.getElementById("sidebar");
  const button = document.querySelector(".mobile-menu-btn");

  if (!sidebar || !button) return;

  const isOpen = sidebar.classList.toggle("is-open");
  button.setAttribute("aria-expanded", String(isOpen));
  button.innerHTML = isOpen
    ? '<i class="bi bi-x-lg"></i><span>Đóng</span>'
    : '<i class="bi bi-list"></i><span>Menu</span>';
}

function closeMobileMenu() {
  const sidebar = document.getElementById("sidebar");
  const button = document.querySelector(".mobile-menu-btn");

  if (!sidebar || !button) return;

  sidebar.classList.remove("is-open");
  button.setAttribute("aria-expanded", "false");
  button.innerHTML = '<i class="bi bi-list"></i><span>Menu</span>';
}

function bindMobileMenuEvents() {
  const menuButton = document.querySelector(".mobile-menu-btn");

  menuButton?.addEventListener("click", (event) => {
    event.preventDefault();
    event.stopPropagation();
    toggleMobileMenu();
  });

  document.addEventListener("click", (event) => {
    const sidebar = document.getElementById("sidebar");

    if (!sidebar || !sidebar.classList.contains("is-open")) return;
    if (sidebar.contains(event.target)) return;

    closeMobileMenu();
  });

  document.addEventListener("click", (event) => {
    const link = event.target.closest("#sidebarNav .nav-link");
    if (link && window.innerWidth <= 991) closeMobileMenu();
  });

  window.addEventListener("resize", () => {
    if (window.innerWidth > 991) closeMobileMenu();
  });
}

function logout() {
  window.location.href = "login.html";
}

function renderDashboard() {
  const completedOrders = SAMPLE_DATA.orders.filter((order) =>
    ["COMPLETED", "PAID"].includes(order.status),
  );
  const revenue = completedOrders.reduce(
    (sum, order) => sum + Number(order.total),
    0,
  );
  const todayRevenue = completedOrders
    .filter((order) => order.date.startsWith("2026-06-27"))
    .reduce((sum, order) => sum + Number(order.total), 0);
  const lowStock = SAMPLE_DATA.products.filter(
    (product) => Number(product.quantity) <= 10,
  ).length;
  const activeMachines = SAMPLE_DATA.machines.filter(
    (machine) => machine.status === "ACTIVE",
  ).length;
  const recentOrders = SAMPLE_DATA.orders
    .slice()
    .sort((a, b) => b.id - a.id)
    .slice(0, 5);

  setHtml(
    "dashboardStats",
    `
    ${statCard("bi-cash-coin", "Doanh thu hôm nay", formatMoney(todayRevenue))}
    ${statCard("bi-calendar2-month", "Doanh thu tháng", formatMoney(revenue))}
    ${statCard("bi-receipt-cutoff", "Tổng đơn hàng", formatNumber(SAMPLE_DATA.orders.length))}
    ${statCard("bi-box-seam", "Sản phẩm sắp hết", formatNumber(lowStock))}
    ${statCard("bi-hdd-network", "Máy đang Active", `${activeMachines}/${SAMPLE_DATA.machines.length}`)}
  `,
  );

  renderTable("recentOrders", {
    columns: ["Mã đơn", "Ngày", "Tổng tiền", "Trạng thái"],
    rows: recentOrders.map((order) => [
      `#${escapeHtml(order.id)}`,
      escapeHtml(order.date),
      formatMoney(order.total),
      getStatusBadge(order.status),
    ]),
    emptyMessage: "Chưa có dữ liệu đơn hàng.",
  });

  renderTable("machineSummary", {
    columns: ["Máy", "Vị trí", "Trạng thái"],
    rows: SAMPLE_DATA.machines.map((machine) => [
      escapeHtml(machine.name),
      escapeHtml(machine.location),
      getStatusBadge(machine.status),
    ]),
    emptyMessage: "Chưa có dữ liệu máy bán nước.",
  });
}

function renderProducts(products = SAMPLE_DATA.products) {
  renderTable("productsTable", {
    columns: ["ID", "Ảnh", "Tên", "Giá", "Trạng thái", "Thao tác"],
    rows: products.map((product) => [
      `#${escapeHtml(product.id)}`,
      `<img class="product-img" src="${safeImage(product.image)}" alt="${escapeHtml(product.name)}">`,
      productNameCell(product),
      formatMoney(product.price),
      getStatusBadge(product.status),
      actionButtons([
        actionButton(
          "Sửa",
          "bi-pencil",
          "primary",
          `editProduct(${product.id})`,
        ),
        actionButton(
          "Ngừng bán",
          "bi-trash",
          "danger",
          `deleteProduct(${product.id})`,
        ),
      ]),
    ]),
    emptyMessage: "Chưa có dữ liệu sản phẩm.",
  });
}

function openProductModal(product = null) {
  setValue("productId", product?.id || "");
  setValue("productName", product?.name || "");
  setValue("productPrice", product?.price || "");
  setValue("productImage", product?.image || "");
  setValue("productStatus", product?.status || "AVAILABLE");
  setText(
    "productModalTitle",
    product ? "Cập nhật sản phẩm" : "Thêm sản phẩm mới",
  );

  bootstrap.Modal.getOrCreateInstance(
    document.getElementById("productModal"),
  ).show();
}

function editProduct(id) {
  const product = SAMPLE_DATA.products.find((item) => item.id === Number(id));
  openProductModal(product || null);
}

function submitProductForm() {
  const payload = {
    id: getValue("productId"),
    name: getValue("productName"),
    price: Number(getValue("productPrice")),
    image: getValue("productImage"),
    status: getValue("productStatus"),
  };

  handlePendingApi("products", payload);
}

function deleteProduct(id) {
  handlePendingApi("products.delete", { id });
}

function renderInventory(
  items = SAMPLE_DATA.products,
  logs = SAMPLE_DATA.inventoryLogs,
) {
  renderTable("inventoryTable", {
    columns: ["ID", "Sản phẩm", "Số lượng", "Trạng thái", "Thao tác"],
    rows: items.map((item) => [
      `#${escapeHtml(item.id)}`,
      escapeHtml(item.name),
      formatNumber(item.quantity),
      getStatusBadge(item.status),
      actionButtons([
        actionButton(
          "Nhập",
          "bi-plus-circle",
          "success",
          `openInventoryModal('IMPORT', ${item.id})`,
        ),
        actionButton(
          "Điều chỉnh",
          "bi-sliders",
          "primary",
          `openInventoryModal('ADJUSTMENT', ${item.id})`,
        ),
      ]),
    ]),
    emptyMessage: "Chưa có dữ liệu tồn kho.",
  });

  renderTable("inventoryLogs", {
    columns: ["ID", "Sản phẩm", "Số lượng", "Loại", "Ngày"],
    rows: logs.map((log) => [
      `#${escapeHtml(log.id)}`,
      escapeHtml(log.productName),
      formatNumber(log.quantity),
      getStatusBadge(log.type),
      escapeHtml(log.date),
    ]),
    emptyMessage: "Chưa có lịch sử tồn kho.",
  });
}

function openInventoryModal(type = "IMPORT", productId = "") {
  const productSelect = document.getElementById("inventoryProductId");

  if (productSelect) {
    productSelect.innerHTML = SAMPLE_DATA.products
      .map(
        (product) =>
          `<option value="${product.id}">${escapeHtml(product.name)}</option>`,
      )
      .join("");
  }

  setValue("inventoryType", type);
  setValue("inventoryProductId", productId);
  setValue("inventoryQuantity", "");
  setText(
    "inventoryModalTitle",
    type === "IMPORT" ? "Nhập thêm sản phẩm" : "Điều chỉnh số lượng tồn kho",
  );

  bootstrap.Modal.getOrCreateInstance(
    document.getElementById("inventoryModal"),
  ).show();
}

function submitInventoryForm() {
  const payload = {
    type: getValue("inventoryType"),
    productId: getValue("inventoryProductId"),
    quantity: Number(getValue("inventoryQuantity")),
  };

  handlePendingApi("inventory", payload);
}

function renderOrders(orders = SAMPLE_DATA.orders) {
  renderTable("ordersTable", {
    columns: [
      "Mã đơn",
      "Ngày",
      "Tổng tiền",
      "Đơn hàng",
      "Thanh toán",
      "Thao tác",
    ],
    rows: orders.map((order) => [
      `#${escapeHtml(order.id)}`,
      escapeHtml(order.date),
      formatMoney(order.total),
      getStatusBadge(order.status),
      getStatusBadge(order.paymentStatus),
      actionButtons([
        actionButton(
          "Chi tiết",
          "bi-eye",
          "primary",
          `openOrderDetail(${order.id})`,
        ),
        actionButton(
          "Hủy",
          "bi-x-circle",
          "danger",
          `cancelOrder(${order.id})`,
        ),
      ]),
    ]),
    emptyMessage: "Chưa có dữ liệu đơn hàng.",
  });
}

function openOrderDetail(order = null) {
  const body = document.getElementById("orderDetailBody");
  if (!body) return;

  const orderData =
    typeof order === "object"
      ? order
      : SAMPLE_DATA.orders.find((item) => item.id === Number(order));

  body.innerHTML =
    order && typeof order === "object"
      ? renderOrderDetail(orderData)
      : orderData
        ? renderOrderDetail(orderData)
        : `<div class="empty-state">Chưa có dữ liệu chi tiết.</div>`;

  bootstrap.Modal.getOrCreateInstance(
    document.getElementById("orderDetailModal"),
  ).show();
}

function cancelOrder(id) {
  handlePendingApi("orders.cancel", { id });
}

function renderUsers(users = SAMPLE_DATA.users) {
  renderTable("usersTable", {
    columns: ["ID", "Username", "Role", "Trạng thái", "Thao tác"],
    rows: users.map((user) => [
      `#${escapeHtml(user.id)}`,
      escapeHtml(user.username),
      getStatusBadge(user.role),
      getStatusBadge(user.status),
      actionButtons([
        actionButton("Sửa", "bi-pencil", "primary", `editUser(${user.id})`),
      ]),
    ]),
    emptyMessage: "Chưa có dữ liệu tài khoản.",
  });
}

function openUserModal(user = null) {
  setValue("userId", user?.id || "");
  setValue("userUsername", user?.username || "");
  setValue("userPassword", "");
  setValue("userRole", user?.role || "STAFF");
  setValue("userStatus", user?.status || "ACTIVE");
  setText("userModalTitle", user ? "Cập nhật tài khoản" : "Tạo tài khoản mới");

  bootstrap.Modal.getOrCreateInstance(
    document.getElementById("userModal"),
  ).show();
}

function editUser(id) {
  const user = SAMPLE_DATA.users.find((item) => item.id === Number(id));
  openUserModal(user || null);
}

function submitUserForm() {
  const payload = {
    id: getValue("userId"),
    username: getValue("userUsername"),
    password: getValue("userPassword"),
    role: getValue("userRole"),
    status: getValue("userStatus"),
  };

  handlePendingApi("users", payload);
}

function renderMachines(machines = SAMPLE_DATA.machines) {
  renderTable("machinesTable", {
    columns: ["ID", "Tên máy", "Vị trí", "Sản phẩm", "Trạng thái"],
    rows: machines.map((machine) => [
      `#${escapeHtml(machine.id)}`,
      escapeHtml(machine.name),
      escapeHtml(machine.location),
      `<a class="btn btn-sm btn-outline-primary" href="inventory.html">
        <i class="bi bi-box-seam me-1"></i> Hiện có: 6
      </a>`,
      machineStatusActions(machine),
    ]),
    emptyMessage: "Chưa có dữ liệu máy bán nước.",
  });
}

function updateMachineStatus(id, status) {
  handlePendingApi("machines.status", { id, status });
}

function renderReports() {
  const validOrders = SAMPLE_DATA.orders.filter((order) =>
    ["COMPLETED", "PAID"].includes(order.status),
  );
  const totalRevenue = validOrders.reduce(
    (sum, order) => sum + Number(order.total),
    0,
  );
  const todayRevenue = validOrders
    .filter((order) => order.date.startsWith("2026-06-27"))
    .reduce((sum, order) => sum + Number(order.total), 0);
  const completedOrders = SAMPLE_DATA.orders.filter(
    (order) => order.status === "COMPLETED",
  ).length;
  const pendingOrders = SAMPLE_DATA.orders.filter(
    (order) => order.status === "PENDING",
  ).length;
  const topProducts = buildTopProducts(SAMPLE_DATA.orders);

  setHtml(
    "reportStats",
    `
    ${statCard("bi-cash", "Doanh thu ngày", formatMoney(todayRevenue))}
    ${statCard("bi-wallet2", "Doanh thu tháng", formatMoney(totalRevenue))}
    ${statCard("bi-check-circle", "Đơn hoàn tất", formatNumber(completedOrders))}
    ${statCard("bi-hourglass-split", "Đơn chờ xử lý", formatNumber(pendingOrders))}
  `,
  );

  setHtml(
    "topProducts",
    topProducts.length
      ? topProducts.map(renderTopProduct).join("")
      : '<div class="empty-state">Chưa có dữ liệu sản phẩm bán chạy.</div>',
  );

  renderTable("revenueTable", {
    columns: ["Mã đơn", "Ngày", "Trạng thái", "Doanh thu"],
    rows: validOrders.map((order) => [
      `#${escapeHtml(order.id)}`,
      escapeHtml(order.date),
      getStatusBadge(order.status),
      formatMoney(order.total),
    ]),
    emptyMessage: "Chưa có dữ liệu doanh thu.",
  });
}

function buildTopProducts(orders) {
  const counter = {};

  orders
    .filter((order) => ["COMPLETED", "PAID"].includes(order.status))
    .forEach((order) => {
      order.items.forEach((item) => {
        counter[item.name] = (counter[item.name] || 0) + Number(item.quantity);
      });
    });

  const max = Math.max(1, ...Object.values(counter));

  return Object.entries(counter)
    .map(([name, quantity]) => ({
      name,
      quantity,
      percent: Math.round((quantity / max) * 100),
    }))
    .sort((a, b) => b.quantity - a.quantity)
    .slice(0, 5);
}

function renderTopProduct(product, index) {
  return `
    <div class="mb-3">
      <div class="d-flex justify-content-between align-items-center mb-1">
        <div class="fw-bold">${index + 1}. ${escapeHtml(product.name)}</div>
        <div>${formatNumber(product.quantity)} lượt bán</div>
      </div>
      <div class="progress">
        <div class="progress-bar" style="width:${product.percent}%"></div>
      </div>
    </div>
  `;
}

function renderTable(targetId, { columns, rows = [], emptyMessage }) {
  const headerHtml = columns
    .map((column) => `<th>${escapeHtml(column)}</th>`)
    .join("");
  const bodyHtml = rows.length
    ? rows.map((row) => renderTableRow(columns, row)).join("")
    : renderEmptyRow(columns.length, emptyMessage);

  setHtml(
    targetId,
    `
    <div class="table-responsive">
      <table class="table table-hover mb-0">
        <thead>
          <tr>${headerHtml}</tr>
        </thead>
        <tbody>${bodyHtml}</tbody>
      </table>
    </div>
  `,
  );
}

function renderTableRow(columns, row) {
  return `
    <tr>
      ${row
        .map(
          (cell, index) => `
        <td data-label="${escapeHtml(columns[index])}">${cell}</td>
      `,
        )
        .join("")}
    </tr>
  `;
}

function renderEmptyRow(colspan, message) {
  return `
    <tr>
      <td colspan="${colspan}">
        <div class="empty-state">${escapeHtml(message)}</div>
      </td>
    </tr>
  `;
}

function actionButtons(buttons) {
  return `<div class="btn-group btn-group-sm">${buttons.join("")}</div>`;
}

function actionButton(label, icon, variant, handler) {
  return `
    <button class="btn btn-outline-${variant}" onclick="${handler}">
      <i class="bi ${icon}"></i> ${escapeHtml(label)}
    </button>
  `;
}

function machineStatusActions(machine) {
  return actionButtons([
    machineStatusButton(machine, "ACTIVE", "Active", "success"),
    machineStatusButton(machine, "MAINTENANCE", "Bảo trì", "warning"),
    machineStatusButton(machine, "OFFLINE", "Offline", "danger"),
  ]);
}

function machineStatusButton(machine, status, label, variant) {
  const className =
    machine.status === status ? `btn-${variant}` : `btn-outline-${variant}`;

  return `
    <button class="btn ${className}" onclick="updateMachineStatus(${machine.id}, '${status}')">
      ${escapeHtml(label)}
    </button>
  `;
}

function productNameCell(product) {
  return `
    <div class="fw-bold">${escapeHtml(product.name)}</div>
  `;
}

function renderOrderDetail(order) {
  const itemRows = order.items
    .map(
      (item) => `
    <tr>
      <td data-label="Sản phẩm">${escapeHtml(item.name)}</td>
      <td data-label="SL">${formatNumber(item.quantity)}</td>
      <td data-label="Giá">${formatMoney(item.price)}</td>
      <td data-label="Thành tiền">${formatMoney(item.price * item.quantity)}</td>
    </tr>
  `,
    )
    .join("");

  return `
    <div class="row g-3 mb-3">
      <div class="col-md-6">${detailBox("Mã đơn", `#${order.id}`)}</div>
      <div class="col-md-6">${detailBox("Ngày tạo", order.date)}</div>
      <div class="col-md-6">${detailBox("Trạng thái", getStatusBadge(order.status))}</div>
      <div class="col-md-6">${detailBox("Thanh toán", getStatusBadge(order.paymentStatus))}</div>
    </div>

    <div class="table-responsive">
      <table class="table table-sm mb-0">
        <thead>
          <tr>
            <th>Sản phẩm</th>
            <th>SL</th>
            <th>Giá</th>
            <th>Thành tiền</th>
          </tr>
        </thead>
        <tbody>${itemRows}</tbody>
        <tfoot>
          <tr>
            <th colspan="3">Tổng</th>
            <th>${formatMoney(order.total)}</th>
          </tr>
        </tfoot>
      </table>
    </div>
  `;
}

function detailBox(label, value) {
  return `
    <div class="p-3 bg-light rounded-4">
      <div class="small text-muted">${escapeHtml(label)}</div>
      <div class="fw-bold">${value}</div>
    </div>
  `;
}

function statCard(icon, label, value) {
  return `
    <div class="col-12 col-sm-6 col-xl">
      <div class="stat-card">
        <div class="stat-icon"><i class="bi ${icon}"></i></div>
        <div class="stat-label">${escapeHtml(label)}</div>
        <div class="stat-value">${escapeHtml(value)}</div>
      </div>
    </div>
  `;
}

function handlePendingApi(action, payload) {
  console.info(`TODO fetch API: ${action}`, payload);
  alert("Chức năng này đang chờ gắn fetch API.");
}

function getValue(id) {
  return document.getElementById(id)?.value.trim() || "";
}

function setValue(id, value) {
  const input = document.getElementById(id);
  if (input) input.value = value;
}

function setText(id, value) {
  const target = document.getElementById(id);
  if (target) target.textContent = value;
}

function setHtml(id, html) {
  const target = document.getElementById(id);
  if (target) target.innerHTML = html;
}
