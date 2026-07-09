import { createPageShell } from "./shared/page.js";

const page = document.body.dataset.page;
const { mount } = createPageShell({ page, alertId: "dashboardAlert" });

document.addEventListener("DOMContentLoaded", () => mount());
