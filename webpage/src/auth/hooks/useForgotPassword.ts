
import { useMutation } from "react-query";

export function useForgotPassword() {
  const { isLoading, mutateAsync } = useMutation([]);
  return { isLoading, forgotPassword: mutateAsync };
}
