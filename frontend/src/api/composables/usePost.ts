import { AxiosError } from "axios";
import { ref, type Ref } from "vue";
import axiosClient from "../axios";

export function usePost<T, P>() {
  const data: Ref<T | null> = ref(null);
  const loading: Ref<boolean> = ref(false);
  const error: Ref<AxiosError | null> = ref(null);

  const post = async (url: string, payload: P) => {
    loading.value = true;
    error.value = null;
    data.value = null;
    try {
      const response = await axiosClient.post<T>(url, payload);
      data.value = response.data;
    } catch (err) {
      if (err instanceof AxiosError) {
        error.value = err;
      }
    } finally {
      loading.value = false;
    }
  };

  return { data, loading, error, post };
}