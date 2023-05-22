
import { useQuery } from "react-query";
import { ActivityLog } from "../types/activityLog";

export function useActivityLogs() {
  return useQuery("activity-logs", () => []);
}
