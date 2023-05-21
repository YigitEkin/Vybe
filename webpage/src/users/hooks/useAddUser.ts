
import { useMutation, useQueryClient } from "react-query";
import { addOne } from "../../core/utils/crudUtils";
import { User } from "../types/user";


export function useAddUser() {
  const queryClient = useQueryClient();

  const { isLoading, mutateAsync } = useMutation([], {
    onSuccess: (user: User) => {
      queryClient.setQueryData<User[]>(["users"], (oldUsers) =>
        addOne(oldUsers, user)
      );
    },
  });

  return { isAdding: isLoading, addUser: mutateAsync };
}
