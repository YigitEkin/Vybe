import { create } from 'zustand';

export type LoginStore = {
  walletId: any | null;
  isLogin: boolean;
  password: string | null;
  token: string | null;
  setIsLogin: (isLogin: boolean) => void;
  phoneNumber: string | null;
  selectedCode: any | null;
  setPhoneNumber: (phoneNumber: string | null) => void;
  setSelectedCode: (selectedCode: any | null) => void;
  setPassword: (password: string | null) => void;
  setWalletId: (walletId: any | null) => void;
};

export const useLoginStore = create<LoginStore>((set) => ({
  walletId: null,
  setWalletId: (walletId: any | null) => set({ walletId }),
  phoneNumber: null,
  password: null,
  token: null,
  setToken: (token: string | null) => set({ token }),
  setPassword: (password: string | null) => set({ password }),
  setPhoneNumber: (phoneNumber: string | null) => set({ phoneNumber }),
  isLogin: false,
  setIsLogin: (isLogin: boolean) => set({ isLogin }),
  selectedCode: {
    name: 'Turkey',
    flag: '🇹🇷',
    code: 'TR',
    dial_code: '+90',
  },
  setSelectedCode: (selectedCode: string | null) => set({ selectedCode }),
}));
