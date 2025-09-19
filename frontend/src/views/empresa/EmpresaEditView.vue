<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import EmpresaForm from './form/EmpresaForm.vue';
import { useRoute } from 'vue-router';
import { useGet } from '@/api/composables/useGet';
import type { Empresa } from './types';
import { sanitizeMaskedInput } from '@/utils/sanitizarMascaras';
import { useUpdate } from '@/api/composables/useUpdate';
import { useToast } from 'primevue';

const route = useRoute()
const { data: empresa, get } = useGet<Empresa>()
const { error, put } = useUpdate<Empresa, Empresa>()
const toast = useToast()

const emptyForm = reactive<Empresa>({
    id: '',
    cnpj: '',
    nomeFantasia: '',
    endereco: {
        cep: '',
        rua: '',
        numero: 0,
        bairro: '',
        cidade: '',
        uf: '',
        estado: ''
    }
});

onMounted(async () => {
    const id = route.params.id
    await get(`/empresas/${id}`)
})

const onFormSubmit = async ({ valid, values }: { valid: boolean, values: Record<string, any> }) => {
    if (valid) {
        values.cnpj = sanitizeMaskedInput(values.cnpj)
        values.endereco.cep = sanitizeMaskedInput(values.endereco.cep)
        await put(`/empresas/${route.params.id}`, values as Empresa)
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
            summary: 'Empresa atualizada com sucesso.',
            life: 3000
        });
    }
};
</script>

<template>
  <EmpresaForm title="Editar empresa" description="Preencha as informações para editar uma empresa." button-label="Editar empresa" :initialValues="empresa ?? emptyForm" :onFormSubmit />
</template>