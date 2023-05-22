
import { useQuery } from "react-query";

export function useProfileInfo() {
  return useQuery("profile-info", () => []);
}
