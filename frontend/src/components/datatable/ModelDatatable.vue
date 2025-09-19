
<template>
  <div class="button-create flex w-full justify-between items-center">
    <span>{{ title }}</span>
    <Button icon="pi pi-plus" @click="create()"></Button>
  </div>
  <DataTable v-model:filters="filters" :value="itens" paginator :rows="20" tableStyle="min-width: 50rem"
    paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
    currentPageReportTemplate="{first} a {last} de {totalRecords}" lazy :loading="loading" :totalRecords="totalRecords"
    @page="(pageEvent: any) => { onPagination(pageEvent.page) }" filterDisplay="menu" @filter="(e: DataTableFilterEvent) => emit('applyFilter', e)">
    <template #paginatorstart>
      <Button @click="refresh()" type="button" icon="pi pi-refresh" text />
    </template>
    <template #paginatorend>
    </template>
    <slot name="columns" />
    <Column header="Ações">
      <template #body="slotProps">
        <div class="flex gap-2">
          <Button icon="pi pi-pencil" @click="edit(slotProps.data)" class="p-button-rounded p-button-info" />
          <Button icon="pi pi-trash" @click="del(slotProps.data)" class="p-button-rounded p-button-danger" />
          <slot name="extra-actions" :data="slotProps.data" />
        </div>
      </template>
    </Column>
  </DataTable>
</template>

<script setup lang="ts" generic="TModel">
import type { DataTableFilterEvent, DataTableFilterMeta } from 'primevue';
import { FilterMatchMode } from '@primevue/core/api'
import { ref } from 'vue';

type ModelDatatableProps = {
  title: string,
  itens: Array<TModel>,
  loading: boolean,
  totalRecords: number,
  onPagination: (page: number) => void,
  refresh: () => void,
  edit: (data: TModel) => void,
  create: () => void,
  del: (data: TModel) => void,
}

defineProps<ModelDatatableProps>();

const filters = ref<DataTableFilterMeta>({
  nome: { value: null, matchMode: FilterMatchMode.CONTAINS },
  documento: { value: null, matchMode: FilterMatchMode.CONTAINS },
})
const emit = defineEmits(['applyFilter'])
</script>
