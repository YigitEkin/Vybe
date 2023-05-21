
import { useQuery } from "react-query";
import { Event } from "../types/event";

export function useEvents() {
  return useQuery("events", () => console.log("hello"));
}
