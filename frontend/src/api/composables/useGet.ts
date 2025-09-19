import { AxiosError } from "axios";
import { ref, type Ref } from "vue";
import axiosClient from "../axios";

export function useGet<T, P extends Record<string, string | number> = Record<string, string | number>>() {
  const data: Ref<T | null> = ref(null);
  const loading: Ref<boolean> = ref(false);
  const error: Ref<AxiosError | null> = ref(null);

  const get = async (url: string, params?: P) => {
    loading.value = true;
    error.value = null;
    try {
      const response = await axiosClient.get<T>(url, { params });
      data.value = response.data;
    } catch (err) {
      if (err instanceof AxiosError) {
        error.value = err;
      }
    } finally {
      loading.value = false;
    }
  };

  return { data, loading, error, get };
}