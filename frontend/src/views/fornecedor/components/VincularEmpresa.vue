<script setup lang="ts">
import type { Empresa } from '@/views/empresa/types';
import GerenciarVinculos from './GerenciarVinculos.vue';
import { useToast } from 'primevue';
import { onMounted } from 'vue';
import { useGet } from '@/api/composables/useGet';
import type { Page } from '@/types/pagination';
import { usePost } from '@/api/composables/usePost';
import type { Fornecedor } from '../types';

const { fornecedor } = defineProps<{
  fornecedor: string,
}>()

const { data: empresas, loading, get } = useGet<Page<Empresa>>()
const { error, post } = usePost<Fornecedor, void>()
const toast = useToast()
const emit = defineEmits(["refresh"])

const onAcao = async (empresa: Empresa) => {
  await post(`/fornecedores/${fornecedor}/empresas/${empresa.id}`)
  if (error.value) {
    const errorResponse = (typeof error?.value?.response?.data == "object") ?
      Object.values(error?.value?.response?.data as Record<string, string>).join('\n') : error?.value?.response?.data?.toString()
    toast.add({
      severity: 'error',
      summary: errorResponse ?? '',
      life: 3000
    })
    return
  }
  toast.add({
      severity: 'success',
      summary: 'Empresa vinculada com sucesso.',
      life: 3000
  });
  emit("refresh")
}

onMounted(() => {
  get("/empresas", { size: 3 })
})

const onPagination = (page: number) => {
  get("/empresas", { page, size: 3 })
}
</script>

<template>
  <GerenciarVinculos :empresas="empresas?.content ?? []" :onAcao="onAcao" label="Vincular" icon="pi pi-user-plus" severity="info" :totalElements="empresas?.totalElements ?? 0" :onPagination="onPagination"
    :loading="loading" />
</template>