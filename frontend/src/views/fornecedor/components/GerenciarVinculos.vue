<template>
  <DataView :value="empresas" paginator :rows="3" :total-records="totalElements ?? 0" @page="(pageEvent: any) => { onPagination ? onPagination(pageEvent.page) : null }"
      lazy :loading="loading">
    <template #list="slotProps">
      <div class="flex flex-col gap-y-3">
        <Card v-for="(empresa, index) in slotProps.items" :key="index" class="shadow-md">
          <template #title>
            <div class="flex justify-between">
              <div class="text-lg font-semibold text-gray-800">
                {{ empresa.nomeFantasia }}
              </div>
              <Button
                v-tooltip="label"
                :icon="icon"
                class="p-button-sm"
                :severity="severity"
                @click="() => onAcao(empresa)"
              />
            </div>
          </template>

          <template #content>
            <div class="text-sm text-gray-600">
              <p><strong>CNPJ:</strong> {{ empresa.cnpj }}</p>
              <p><strong>Cidade:</strong> {{ empresa.endereco.cidade }}</p>
              <p><strong>UF:</strong> {{ empresa.endereco.uf }}</p>
            </div>
          </template>
        </Card>
      </div>
    </template>
  </DataView>
</template>

<script setup lang="ts">
import type { Empresa } from '@/views/empresa/types';

defineProps<{
  empresas: Empresa[],
  label: string,
  icon: string,
  severity: string,
  totalElements: number,
  onPagination?: (page: number) => void,
  loading?: boolean,
  onAcao: (empresa: Empresa) => void
}>()
</script>
