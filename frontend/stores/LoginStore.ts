import create from "zustand";

export type LoginStore = {
  isLogin: boolean;
  setIsLogin: (isLogin: boolean) => void;
  phoneNumber: string | null;
  setPhoneNumber: (phoneNumber: string | null) => void;
};

export const useLoginStore = create<LoginStore>((set) => ({
  phoneNumber: null,
  setPhoneNumber: (phoneNumber: string | null) => set({ phoneNumber }),
  isLogin: false,
  setIsLogin: (isLogin: boolean) => set({ isLogin }),
}));
