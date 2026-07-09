import { escapeHtml } from "./format.js";

export function renderTable(targetId, { columns, rows = [], emptyMessage }) {
  const headerHtml = columns.map((column) => `<th>${escapeHtml(column)}</th>`).join("");
  const bodyHtml = rows.length
    ? rows.map((row) => renderTableRow(columns, row)).join("")
    : renderEmptyRow(columns.length, emptyMessage);

  const target = document.getElementById(targetId);
  if (!target) return;

  target.innerHTML = `
    <div class="table-responsive">
      <table class="table table-hover mb-0">
        <thead>
          <tr>${headerHtml}</tr>
        </thead>
        <tbody>${bodyHtml}</tbody>
      </table>
    </div>
  `;
}

export function renderTableRow(columns, row) {
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

export function renderEmptyRow(colspan, message) {
  return `
    <tr>
      <td colspan="${colspan}">
        <div class="empty-state">${escapeHtml(message)}</div>
      </td>
    </tr>
  `;
}

export function actionButtons(buttons) {
  return `<div class="btn-group btn-group-sm">${buttons.join("")}</div>`;
}

export function actionButton(label, icon, variant, handler) {
  return `
    <button class="btn btn-outline-${variant}" onclick="${handler}">
      <i class="bi ${icon}"></i> ${escapeHtml(label)}
    </button>
  `;
}
