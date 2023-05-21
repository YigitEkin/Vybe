
import { useQuery } from "react-query";
import { Notification } from "../types/notification";

export function useNotifications() {
  return useQuery("notifications", () => [], {
    suspense: false,
  });
}
