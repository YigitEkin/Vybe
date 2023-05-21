
import { useMutation } from "react-query";
import { fetchData } from "../../admin/config/request";
import { useLocalStorage } from "../../core/hooks/useLocalStorage";

const login = async ({
  email,
  password,
}: {
  email: string;
  password: string;
}): Promise<string> => {
  console.log(email, password);
  const data = await fetchData("/api/auth/signIn/venueAdmin", "POST", {
    username: email,
    password: password,
    code: ''
  });
  if (!data) {
    throw new Error("Invalid credentials");
  }
  localStorage.setItem("username", email);
  const userData = await fetchData(`/api/auth/venueAdmin/${email}`, 'GET')
  localStorage.setItem("venueInfo", JSON.stringify(userData));
  console.log(userData);
  return data;
};

export function useLogin() {
  const { isLoading, mutateAsync } = useMutation(login);

  return { isLoggingIn: isLoading, login: mutateAsync };
}
