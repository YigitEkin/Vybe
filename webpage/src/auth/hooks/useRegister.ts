
import { useMutation } from "react-query";
import { UserInfo } from "../types/userInfo";

export function useRegister() {
  const { isLoading, mutateAsync } = useMutation([]);
  return { isRegistering: isLoading, register: mutateAsync };
}
