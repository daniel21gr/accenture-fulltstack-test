<script setup lang="ts">
import ModelDatatable from '@/components/datatable/ModelDatatable.vue';
import { onMounted, ref } from 'vue';
import type { Fornecedor } from './types';
import type { Page } from '@/types/pagination';
import { useGet } from '@/api/composables/useGet';
import { useRouter } from 'vue-router';
import VincularEmpresa from './components/VincularEmpresa.vue';
import DesvincularEmpresa from './components/DesvincularEmpresa.vue';

const { data: fornecedores, loading, get } = useGet<Page<Fornecedor>>()
const lastPage = ref<number>(0)
const router = useRouter()
const visible = ref(false)

enum TipoVinculo {
  VINCULAR,
  DESVINCULAR
}

onMounted(() => {
  get("/fornecedores")
})

const onPagination = (page: number) => {
  get("/fornecedores", { page })
  lastPage.value = page
}

const refresh = () => {
  get("/fornecedores", { page: lastPage.value })
}

const edit = (data: Fornecedor) => {
  router.push({ name: 'FornecedorEdit', params: { id: data.id } })
}

const create = () => {
  router.push({ name: 'FornecedorCreate' })
}

const del = () => {}

const fornecedorAtual = ref<Fornecedor>()
const tipoVinculoAtual = ref<TipoVinculo>(TipoVinculo.VINCULAR)
const gerenciarEmpresa = (data: Fornecedor, tipoVinculo: TipoVinculo) => {
  fornecedorAtual.value = data
  tipoVinculoAtual.value = tipoVinculo

  visible.value = true
}

const applyFilter = (e: { filters: { nome: { value: string }, documento: { value: string }}}) => {
  const filtroNome = e.filters.nome.value
  const filtroDocumento = e.filters.documento.value

  get("/fornecedores", { page: lastPage.value,  nome: filtroNome, documento: filtroDocumento })
}
</script>

<template>
  <ModelDatatable title="Fornecedor" :itens="fornecedores?.content ?? []" :loading="loading" :total-records="fornecedores?.totalElements ?? 0" :onPagination="onPagination" :refresh="refresh" :edit="edit" :create="create" :del="del"
      @apply-filter="applyFilter">
    <template #columns>
      <Column field="nome" header="Nome" sortable :show-filter-match-modes="false">
        <template #filter="{ filterModel }">
          <InputText
            v-model="filterModel.value"
            type="text"
            placeholder="Buscar pelo nome"
          />
        </template>
      </Column>
      <Column field="documento" header="CPF/CNPJ" sortable :show-filter-match-modes="false">
        <template #filter="{ filterModel }">
          <InputText
            v-model="filterModel.value"
            type="text"
            placeholder="Buscar pelo documento"
          />
        </template>
      </Column>
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
      <Column header="Empresas Vinculadas">
        <template #body="slotProps">
          <ul v-if="slotProps.data.empresas && slotProps.data.empresas.length">
            <li v-for="empresa in slotProps.data.empresas" :key="empresa.id">
              <span class="pi pi-building"></span> {{ empresa.nomeFantasia }}
            </li>
          </ul>
          <span v-else>Nenhuma</span>
        </template>
      </Column>
    </template>

    <template #extra-actions="{data}">
      <Button v-tooltip="'Vincular Empresas'" icon="pi pi-user-plus" @click="gerenciarEmpresa(data, TipoVinculo.VINCULAR)" class="p-button-rounded p-button-secondary" />
      <Button v-tooltip="'Desvincular Empresas'" icon="pi pi-user-minus" @click="gerenciarEmpresa(data, TipoVinculo.DESVINCULAR)" class="p-button-rounded p-button-secondary" />
    </template>
  </ModelDatatable>

  <Dialog v-model:visible="visible" modal :header="(tipoVinculoAtual == TipoVinculo.VINCULAR ? 'Vincular empresa' : 'Desvincular empresa')" :style="{ width: '25rem' }">
    <VincularEmpresa v-if="tipoVinculoAtual == TipoVinculo.VINCULAR" :fornecedor="fornecedorAtual?.id ?? ''" @refresh="refresh" />
    <DesvincularEmpresa v-else :fornecedor="fornecedorAtual?.id ?? ''" :empresas="fornecedorAtual?.empresas ?? []" />
    <div class="flex justify-end gap-2">
      <Button type="button" label="Fechar" severity="secondary" @click="visible = false"></Button>
    </div>
  </Dialog>
</template>