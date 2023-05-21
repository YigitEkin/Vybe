
import { useMutation, useQueryClient } from "react-query";
import { updateOne } from "../../core/utils/crudUtils";
import { Event } from "../types/event";

export function useUpdateEvent() {
  const queryClient = useQueryClient();

  const { isLoading, mutateAsync } = useMutation([], {
    onSuccess: (event: Event) => {
      queryClient.setQueryData<Event[]>(["events"], (oldEvents) =>
        updateOne(oldEvents, event)
      );
    },
  });

  return { isUpdating: isLoading, updateEvent: mutateAsync };
}
