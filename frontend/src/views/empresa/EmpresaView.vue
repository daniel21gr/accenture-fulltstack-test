<script setup lang="ts">
import { useGet } from '@/api/composables/useGet';
import ModelDatatable from '@/components/datatable/ModelDatatable.vue';
import type { Empresa } from './types';
import { onMounted, ref } from 'vue';
import type { Page } from '@/types/pagination';
import { useRouter } from 'vue-router';

const { data: empresas, loading, get } = useGet<Page<Empresa>>()
const lastPage = ref<number>(0)
const router = useRouter()

onMounted(() => {
  get("/empresas")
})

const onPagination = (page: number) => {
  get("/empresas", { page, size: 1 })
  lastPage.value = page
}

const refresh = () => {
  get("/empresas", { page: lastPage.value, size: 1})
}

const edit = (data: Empresa) => {
  router.push({ name: 'EmpresaEdit', params: { id: data.id } })
}

const create = () => {
  router.push({ name: 'EmpresaCreate' })
}
</script>

<template>
  <ModelDatatable title="Empresa" :itens="empresas?.content ?? []" :loading="loading" :total-records="empresas?.totalElements ?? 0" :onPagination="onPagination" :refresh="refresh" :edit="edit" :create="create">
    <template #columns>
      <Column field="cnpj" header="CNPJ" sortable></Column>
      <Column field="nomeFantasia" header="Nome Fantasia" sortable></Column>
      <Column header="Endereço">
        <template #body="slotProps">
          <div v-if="slotProps.data.endereco">
            {{ slotProps.data.endereco.rua }}, {{ slotProps.data.endereco.numero }} - {{ slotProps.data.endereco.bairro }}, {{ slotProps.data.endereco.cidade }} - {{ slotProps.data.endereco.uf }}
          </div>
        </template>
      </Column>
    </template>
  </ModelDatatable>
</template>