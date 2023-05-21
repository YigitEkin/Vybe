import { useMutation } from "react-query";

export function useForgotPasswordSubmit() {
  const { isLoading, mutateAsync } = useMutation([]);
  return { isLoading, forgotPasswordSubmit: mutateAsync };
}
