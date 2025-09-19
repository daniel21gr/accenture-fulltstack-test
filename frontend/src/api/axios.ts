import axios, {
  type AxiosInstance,
} from 'axios';

// Cria uma instância do Axios com configurações base
const axiosClient: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default axiosClient;
