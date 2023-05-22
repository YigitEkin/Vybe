import { create } from 'zustand';

export type CheckedInStore = {
  isCheckIn: boolean;
  setIsCheckIn: (isCheckIn: boolean) => void;
};

export const useCheckedInStore = create<CheckedInStore>((set) => ({
  isCheckIn: false,
  setIsCheckIn: (isCheckIn: boolean) => set({ isCheckIn }),
}));
