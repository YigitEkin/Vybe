
import { useQuery } from "react-query";
import { User } from "../types/user";

export function useUsers() {
  return useQuery("users", () => console.log("users"));
}
