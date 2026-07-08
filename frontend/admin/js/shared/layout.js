const navItems = [
  { href: "dashboard.html", page: "dashboard", icon: "bi-speedometer2", label: "Dashboard" },
  { href: "products.html", page: "products", icon: "bi-cup-straw", label: "Sản phẩm" },
  { href: "orders.html", page: "orders", icon: "bi-receipt", label: "Đơn hàng" },
  { href: "users.html", page: "users", icon: "bi-people", label: "Tài khoản" },
  { href: "machines.html", page: "machines", icon: "bi-hdd-rack", label: "Máy bán nước" },
  { href: "reports.html", page: "reports", icon: "bi-bar-chart-line", label: "Báo cáo" },
];

export function renderSidebar(page) {
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
    </div>
    <nav class="nav flex-column">
      ${navItems
        .map(
          (item) => `
            <a class="nav-link ${page === item.page ? "active" : ""}" href="${item.href}">
              <i class="bi ${item.icon}"></i>
              <span>${item.label}</span>
            </a>
          `,
        )
        .join("")}
    </nav>
    <div class="sidebar-footer">
      <button class="btn btn-outline-light btn-sm w-100" onclick="handleLogout()">
        <i class="bi bi-box-arrow-right me-1"></i> Đăng xuất
      </button>
    </div>
  `;
}
