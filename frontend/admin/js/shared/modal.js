export function setFormValue(id, value) {
  const input = document.getElementById(id);
  if (input) input.value = value;
}

export function getFormValue(id) {
  return document.getElementById(id)?.value.trim() || "";
}

export function setText(id, value) {
  const el = document.getElementById(id);
  if (el) el.textContent = value;
}

export function openModal(modalElement) {
  if (!modalElement) return null;
  return bootstrap.Modal.getOrCreateInstance(modalElement);
}
