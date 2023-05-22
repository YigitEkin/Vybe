import axios from 'axios';
import { useLoginStore } from '../stores/LoginStore';

export default function axiosConfig() {
  const { token } = useLoginStore((state: any) => {
    return {
      token: state.token,
    };
  });
  const instanceToken = axios.create({
    baseURL: 'http://139.179.55.69:8080',
    //  headers: { Authorization: `Bearer ${getToken() ? getToken() : ''}` },
  });
  instanceToken.interceptors.request.use(function (config) {
    console.log('anan123', token);
    config.headers['Authorization'] = token ? `Bearer ${token}` : 'anananan';
    return config;
  });
  return instanceToken;
}
