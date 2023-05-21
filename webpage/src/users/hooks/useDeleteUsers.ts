
import { useMutation, useQueryClient } from "react-query";
import { removeMany } from "../../core/utils/crudUtils";
import { User } from "../types/user";

export function useDeleteUsers() {
  const queryClient = useQueryClient();

  const { isLoading, mutateAsync } = useMutation([], {
    onSuccess: (userIds: string[]) => {
      queryClient.setQueryData<User[]>(["users"], (oldUsers) =>
        removeMany(oldUsers, userIds)
      );
    },
  });

  return { isDeleting: isLoading, deleteUsers: mutateAsync };
}
