const SAMPLE_DATA = {
  users: [
    {
      id: 1,
      username: "admin",
      password: "123456",
      role: "ADMIN",
      status: "ACTIVE",
    },
    {
      id: 2,
      username: "staff01",
      password: "123456",
      role: "STAFF",
      status: "ACTIVE",
    },
    {
      id: 3,
      username: "staff02",
      password: "123456",
      role: "STAFF",
      status: "LOCKED",
    },
  ],
  products: [
    {
      id: 1,
      name: "Coca Cola",
      price: 10000,
      quantity: 26,
      image: "https://placehold.co/96x96/e53935/ffffff?text=Coca",
      status: "AVAILABLE",
    },
    {
      id: 2,
      name: "Pepsi",
      price: 10000,
      quantity: 18,
      image: "https://placehold.co/96x96/1565c0/ffffff?text=Pepsi",
      status: "AVAILABLE",
    },
    {
      id: 3,
      name: "Sting Dâu",
      price: 12000,
      quantity: 9,
      image: "https://placehold.co/96x96/f06292/ffffff?text=Sting",
      status: "AVAILABLE",
    },
    {
      id: 4,
      name: "Aquafina",
      price: 8000,
      quantity: 0,
      image: "https://placehold.co/96x96/29b6f6/ffffff?text=Water",
      status: "OUT_OF_STOCK",
    },
    {
      id: 5,
      name: "Trà Xanh 0 Độ",
      price: 11000,
      quantity: 14,
      image: "https://placehold.co/96x96/43a047/ffffff?text=Tea",
      status: "AVAILABLE",
    },
  ],
  machines: [
    { id: 1, name: "VM-HCM-01", location: "Khu A - Tầng 1", status: "ACTIVE" },
    {
      id: 2,
      name: "VM-HCM-02",
      location: "Khu B - Tầng 2",
      status: "MAINTENANCE",
    },
    { id: 3, name: "VM-HCM-03", location: "Sảnh chính", status: "OFFLINE" },
  ],
  orders: [
    {
      id: 1001,
      date: "2026-06-27 09:20",
      total: 22000,
      status: "COMPLETED",
      paymentStatus: "SUCCESS",
      items: [
        { productId: 1, name: "Coca Cola", quantity: 1, price: 10000 },
        { productId: 3, name: "Sting Dâu", quantity: 1, price: 12000 },
      ],
    },
    {
      id: 1002,
      date: "2026-06-27 10:05",
      total: 10000,
      status: "PAID",
      paymentStatus: "SUCCESS",
      items: [{ productId: 2, name: "Pepsi", quantity: 1, price: 10000 }],
    },
    {
      id: 1003,
      date: "2026-06-27 11:45",
      total: 8000,
      status: "PENDING",
      paymentStatus: "PENDING",
      items: [{ productId: 4, name: "Aquafina", quantity: 1, price: 8000 }],
    },
    {
      id: 1004,
      date: "2026-06-26 18:30",
      total: 21000,
      status: "COMPLETED",
      paymentStatus: "SUCCESS",
      items: [
        { productId: 5, name: "Trà Xanh 0 Độ", quantity: 1, price: 11000 },
        { productId: 1, name: "Coca Cola", quantity: 1, price: 10000 },
      ],
    },
  ],
  inventoryLogs: [
    {
      id: 1,
      productId: 1,
      productName: "Coca Cola",
      quantity: 30,
      type: "IMPORT",
      date: "2026-06-27 08:00",
    },
    {
      id: 2,
      productId: 2,
      productName: "Pepsi",
      quantity: 20,
      type: "IMPORT",
      date: "2026-06-27 08:05",
    },
    {
      id: 3,
      productId: 4,
      productName: "Aquafina",
      quantity: -2,
      type: "ADJUSTMENT",
      date: "2026-06-27 13:20",
    },
  ],
};

const statusVariants = {
  ACTIVE: "success",
  AVAILABLE: "success",
  CANCELLED: "secondary",
  COMPLETED: "success",
  DISABLED: "secondary",
  FAILED: "danger",
  LOCKED: "secondary",
  MAINTENANCE: "warning",
  OFFLINE: "danger",
  PAID: "info",
  PENDING: "warning",
  REFUNDED: "secondary",
  STAFF: "info",
  ADMIN: "primary",
  SUCCESS: "success",
  CONNECTED: "success",
  DISCONNECTED: "danger",
  OK: "success",
  CLOSED: "success",
  OPEN: "warning",
  NORMAL: "success",
  NONE: "success",
  IMPORT: "success",
  ADJUSTMENT: "primary",
};

function formatMoney(value) {
  return Number(value || 0).toLocaleString("vi-VN") + "đ";
}

function formatNumber(value) {
  return Number(value || 0).toLocaleString("vi-VN");
}

function getStatusBadge(status) {
  const safeStatus = escapeHtml(status || "--");
  const variant = statusVariants[status] || "secondary";

  return `<span class="badge rounded-pill text-bg-${variant}">${safeStatus}</span>`;
}

function safeImage(url) {
  return url && url.trim()
    ? url.trim()
    : "https://placehold.co/96x96/f1f5f9/64748b?text=No+Image";
}

function escapeHtml(value) {
  return String(value ?? "").replace(
    /[&<>'"]/g,
    (char) =>
      ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        "'": "&#039;",
        '"': "&quot;",
      })[char],
  );
}
