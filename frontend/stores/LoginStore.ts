import { create } from "zustand";

export type LoginStore = {
  isLogin: boolean;
  setIsLogin: (isLogin: boolean) => void;
  phoneNumber: string | null;
  selectedCode: any | null;
  setPhoneNumber: (phoneNumber: string | null) => void;
  setSelectedCode: (selectedCode: any | null) => void;
};

export const useLoginStore = create<LoginStore>((set) => ({
  phoneNumber: null,
  setPhoneNumber: (phoneNumber: string | null) => set({ phoneNumber }),
  isLogin: false,
  setIsLogin: (isLogin: boolean) => set({ isLogin }),
  selectedCode: {
    name: "Afghanistan",
    flag: "🇦🇫",
    code: "AF",
    dial_code: "+93",
  },
  setSelectedCode: (selectedCode: string | null) => set({ selectedCode }),
}));
