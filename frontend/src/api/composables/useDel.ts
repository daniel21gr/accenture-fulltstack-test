import { AxiosError } from "axios";
import { ref, type Ref } from "vue";
import axiosClient from "../axios";

export function useDel<T>() {
  const data: Ref<T | null> = ref(null);
  const loading: Ref<boolean> = ref(false);
  const error: Ref<AxiosError | null> = ref(null);

  const del = async (url: string) => {
    loading.value = true;
    error.value = null;
    try {
      const response = await axiosClient.delete<T>(url);
      data.value = response.data;
    } catch (err) {
      if (err instanceof AxiosError) {
        error.value = err;
      }
    } finally {
      loading.value = false;
    }
  };

  return { data, loading, error, del };
}