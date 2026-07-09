import { getHealth } from "./admin/api/service/health.service.js";

async function wakeUp() {
  try {
    await getHealth();
    window.location.href = "/admin/login.html";
  } catch (e) {
    console.log("Backend chưa sẵn sàng, thử lại...");
    setTimeout(wakeUp, 5000);
  }
}

wakeUp();
