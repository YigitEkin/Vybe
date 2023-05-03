import { create } from 'zustand';

export type SignUpStore = {
  email: string | null;
  password: string | null;
  username: string | null;
  phoneNumber: string | null;
  selectedCode: any | null;
  setEmail: (email: string | null) => void;
  setPassword: (password: string | null) => void;
  setUsername: (username: string | null) => void;
  setPhoneNumber: (phoneNumber: string | null) => void;
  setSelectedCode: (selectedCode: string | null) => void;
};

export const useSignUpStore = create((set) => ({
  email: null,
  password: null,
  username: null,
  phoneNumber: null,
  selectedCode: {
    name: 'Turkey',
    flag: '🇹🇷',
    code: 'TR',
    dial_code: '+90',
  },
  setSelectedCode: (selectedCode: string | null) => set({ selectedCode }),
  setEmail: (email: string | null) => set({ email }),
  setPassword: (password: string | null) => set({ password }),
  setUsername: (username: string | null) => set({ username }),
  setPhoneNumber: (phoneNumber: string | null) => set({ phoneNumber }),
}));
