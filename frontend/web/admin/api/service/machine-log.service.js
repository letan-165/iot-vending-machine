import { request } from "../apiclient.js";

export function getLogMachine(machineId) {
  return request(`/inventory-logs/${machineId}`);
}
