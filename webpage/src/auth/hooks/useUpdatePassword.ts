import { useMutation } from "react-query";

export function useUpdatePassword() {
  const { isLoading, mutateAsync } = useMutation([]);
  return { isUpdating: isLoading, updatePassword: mutateAsync };
}
