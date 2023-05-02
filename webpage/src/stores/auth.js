import { create } from 'zustand';

const useAuthStore = create((set) => ({
  email: '',
  password: '',
  isLoggedIn: true,
  setEmail: (email) => set(() => ({ email })),
  setPassword: (password) => set(() => ({ password })),
  setIsLoggedIn: (isLoggedIn) => set(() => ({ isLoggedIn })),
}));

export default useAuthStore;
