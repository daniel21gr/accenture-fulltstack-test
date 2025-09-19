<script setup lang="ts">
import ModelDatatable from '@/components/datatable/ModelDatatable.vue';
import { onMounted, ref } from 'vue';
import type { Fornecedor } from './types';
import type { Page } from '@/types/pagination';
import { useGet } from '@/api/composables/useGet';
import { useRouter } from 'vue-router';

const { data: fornecedores, loading, get } = useGet<Page<Fornecedor>>()
const lastPage = ref<number>(0)
const router = useRouter()

onMounted(() => {
  get("/fornecedores")
})

const onPagination = (page: number) => {
  get("/fornecedores", { page, size: 1 })
  lastPage.value = page
}

const refresh = () => {
  get("/fornecedores", { page: lastPage.value, size: 1})
}

const edit = (data: Fornecedor) => {
  router.push({ name: 'FornecedorEdit', params: { id: data.id } })
}

const create = () => {
  router.push({ name: 'FornecedorCreate' })
}
</script>

<template>
  <ModelDatatable title="Fornecedor" :itens="fornecedores?.content ?? []" :loading="loading" :total-records="fornecedores?.totalElements ?? 0" :onPagination="onPagination" :refresh="refresh" :edit="edit" :create="create">
    <template #columns>
      <Column field="nome" header="Nome" sortable></Column>
      <Column field="documento" header="CPF/CNPJ" sortable></Column>
      <Column field="email" header="Email"></Column>
      <Column header="Tipo">
        <template #body="slotProps">
          {{ slotProps.data.tipoFornecedor === 'PESSOA_FISICA' ? 'Pessoa Física' : 'Pessoa Jurídica' }}
        </template>
      </Column>
      <Column header="Endereço">
        <template #body="slotProps">
          <div v-if="slotProps.data.endereco">
            {{ slotProps.data.endereco.rua }}, {{ slotProps.data.endereco.numero }} - {{ slotProps.data.endereco.cidade }} / {{ slotProps.data.endereco.uf }}
          </div>
        </template>
      </Column>
    </template>
  </ModelDatatable>
</template>