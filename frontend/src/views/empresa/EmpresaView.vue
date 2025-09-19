<script setup lang="ts">
import { useGet } from '@/api/composables/useGet';
import ModelDatatable from '@/components/datatable/ModelDatatable.vue';
import type { Empresa } from './types';
import { onMounted, ref } from 'vue';
import type { Page } from '@/types/pagination';
import { useRouter } from 'vue-router';
import { useConfirm, useToast } from 'primevue';
import { useDel } from '@/api/composables/useDel';

const { data: empresas, loading, get } = useGet<Page<Empresa>>()
const lastPage = ref<number>(0)
const router = useRouter()
const confirm = useConfirm()
const { del: deletion, error } = useDel<Empresa>()
const toast = useToast()

onMounted(() => {
  get("/empresas")
})

const onPagination = (page: number) => {
  get("/empresas", { page })
  lastPage.value = page
}

const refresh = () => {
  get("/empresas", { page: lastPage.value })
}

const edit = (data: Empresa) => {
  router.push({ name: 'EmpresaEdit', params: { id: data.id } })
}

const create = () => {
  router.push({ name: 'EmpresaCreate' })
}

const deleteEmpresa = async (id: string) => {
  await deletion(`/empresas/${id}`)
  if (!error.value) {
    toast.add({
      severity: 'success',
      summary: 'Empresa excluída com sucesso.',
      life: 3000
    });
  }
  refresh()
}

const del = (data: Empresa) => {
  confirm.require({
    message: 'Tem certeja que deseja excluir?',
    header: 'Atenção!',
    icon: 'pi pi-exclamation-triangle',
    rejectProps: {
      label: 'Cancelar',
      severity: 'secondary',
      outlined: true
    },
    acceptProps: {
      label: 'Excluir',
      severity: 'danger'
    },
    accept: () => {
      deleteEmpresa(data.id)
    },
  })
}
</script>

<template>
  <ModelDatatable title="Empresa" :itens="empresas?.content ?? []" :loading="loading" :total-records="empresas?.totalElements ?? 0" :onPagination="onPagination" :refresh="refresh" :edit="edit" :create="create" :del="del">
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
      <Column header="Fornecedores Vinculadas">
        <template #body="slotProps">
          <ul v-if="slotProps.data.fornecedores && slotProps.data.fornecedores.length">
            <li v-for="fornecedor in slotProps.data.fornecedores" :key="fornecedor.id">
              <span class="pi pi-user"></span> {{ fornecedor.nome }}
            </li>
          </ul>
          <span v-else>Nenhuma</span>
        </template>
      </Column>
    </template>
  </ModelDatatable>
</template>