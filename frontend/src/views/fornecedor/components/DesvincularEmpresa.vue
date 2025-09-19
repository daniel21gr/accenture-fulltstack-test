<script setup lang="ts">
import type { Empresa } from '@/views/empresa/types';
import GerenciarVinculos from './GerenciarVinculos.vue';
import { useDel } from '@/api/composables/useDel';
import { useToast } from 'primevue';

const { fornecedor } = defineProps<{
  fornecedor: string,
  empresas: Empresa[],
}>()

const { error, del } = useDel<void>()
const toast = useToast()

const onAcao = async (empresa: Empresa) => {
  await del(`/fornecedores/${fornecedor}/empresas/${empresa.id}`)
  if (!error.value) {
    toast.add({
      severity: 'success',
      summary: 'Vínculo excluído com sucesso.',
      life: 3000
    });
  }
}
</script>

<template>
  <GerenciarVinculos :empresas="empresas" :onAcao="onAcao" label="Desvincular" icon="pi pi-user-minus" severity="danger" />
</template>