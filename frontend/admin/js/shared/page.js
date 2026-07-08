import { renderSidebar } from "./layout.js";

export function createPageShell({ page, alertId, alertTarget = ".panel" }) {
  const alertBox = document.createElement("div");
  alertBox.className = "alert alert-danger d-none mt-3";

  function injectAlert() {
    const target = document.querySelector(alertTarget);
    if (target && !document.getElementById(alertId)) {
      alertBox.id = alertId;
      target.prepend(alertBox);
    }
  }

  function showAlert(message) {
    alertBox.textContent = message;
    alertBox.classList.remove("d-none");
  }

  function hideAlert() {
    alertBox.textContent = "";
    alertBox.classList.add("d-none");
  }

  function bindActions(actions = {}) {
    Object.assign(window, actions);
  }

  async function mount(load, actions) {
    renderSidebar(page);
    injectAlert();
    bindActions(actions);
    if (load) {
      await load();
    }
  }

  return { mount, showAlert, hideAlert, alertBox };
}
