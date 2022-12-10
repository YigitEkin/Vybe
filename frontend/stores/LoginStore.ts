import create from "zustand";

export type LoginStore = {
  phoneNumber: string | null;
  setPhoneNumber: (phoneNumber: string | null) => void;
};

export const useLoginStore = create((set) => ({
  phoneNumber: null,
  setPhoneNumber: (phoneNumber: string | null) => set({ phoneNumber }),
}));
