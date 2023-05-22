
import { useMutation, useQueryClient } from "react-query";
import { removeOne } from "../../core/utils/crudUtils";
import { Event } from "../types/event";

export function useDeleteEvent() {
  const queryClient = useQueryClient();

  const { isLoading, mutateAsync } = useMutation([], {
    onSuccess: (eventId: string) => {
      queryClient.setQueryData<Event[]>(["events"], (oldEvents) =>
        removeOne(oldEvents, eventId)
      );
    },
  });

  return { isDeleting: isLoading, deleteEvent: mutateAsync };
}
