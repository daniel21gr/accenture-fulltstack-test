
<template>
  <div class="button-create flex w-full justify-between items-center">
    <span>{{ title }}</span>
    <Button icon="pi pi-plus" @click="create()"></Button>
  </div>
  <DataTable :value="itens" paginator :rows="20" tableStyle="min-width: 50rem"
    paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
    currentPageReportTemplate="{first} a {last} de {totalRecords}" lazy :loading="loading" :totalRecords="totalRecords"
    @page="(pageEvent: any) => { onPagination(pageEvent.page) }">
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
</script>
