import { useMutation } from "react-query";

const logout = async (): Promise<string> => {
  localStorage.clear();
  return null;
};

export function useLogout() {
  const { isLoading, mutateAsync } = useMutation(logout);

  return { isLoggingOut: isLoading, logout: mutateAsync };
}
