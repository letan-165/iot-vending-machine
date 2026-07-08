export function escapeHtml(value) {
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

export function escapeAttr(value) {
  return String(value ?? "").replace(/'/g, "\\'");
}

export function formatMoney(value) {
  return `${Number(value || 0).toLocaleString("vi-VN")}đ`;
}

export function formatDateTime(value) {
  if (!value) return "--";
  return String(value).replace("T", " ").replace("Z", "");
}

export function maskId(value, size = 4) {
  const text = String(value ?? "");
  if (!text) return "--";
  return `....${escapeHtml(text.slice(-size))}`;
}

export function safeImage(url) {
  return url && url.trim()
    ? url.trim()
    : "https://placehold.co/96x96/f1f5f9/64748b?text=No+Image";
}

export function getStatusBadge(status) {
  const variants = {
    ACTIVE: "success",
    AVAILABLE: "success",
    CANCELLED: "secondary",
    COMPLETED: "success",
    DISABLED: "secondary",
    FAILED: "danger",
    LOCKED: "secondary",
    MAINTENANCE: "warning",
    OFFLINE: "danger",
    OUT_OF_STOCK: "warning",
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

  const variant = variants[status] || "secondary";
  return `<span class="badge rounded-pill text-bg-${variant}">${escapeHtml(status || "--")}</span>`;
}
