
import { useMutation, useQueryClient } from "react-query";
import { addOne } from "../../core/utils/crudUtils";
import { Event } from "../types/event";

export function useAddEvent() {
  const queryClient = useQueryClient();

  const { isLoading, mutateAsync } = useMutation([], {
    onSuccess: (event: Event) => {
      queryClient.setQueryData<Event[]>(["events"], (oldEvents) =>
        addOne(oldEvents, event)
      );
    },
  });

  return { isAdding: isLoading, addEvent: mutateAsync };
}
