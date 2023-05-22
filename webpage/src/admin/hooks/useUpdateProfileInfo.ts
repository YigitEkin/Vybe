
import { useMutation, useQueryClient } from "react-query";
import { ProfileInfo } from "../types/profileInfo";

export function useUpdateProfileInfo() {
  const queryClient = useQueryClient();

  const { isLoading, mutateAsync } = useMutation([], {
    onSuccess: (profileInfo: ProfileInfo) => {
      queryClient.setQueryData(["profile-info"], profileInfo);
    },
  });

  return { isUpdating: isLoading, updateProfileInfo: mutateAsync };
}
