
import { useQuery } from "react-query";
import { UserInfo } from "../types/userInfo";

export function useUserInfo(key?: string) {
  return useQuery(["user-info", key], () => console.log(), {
    enabled: !!key,
  });
}
