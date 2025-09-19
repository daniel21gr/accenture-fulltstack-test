<script setup lang="ts">
import { usePost } from '@/api/composables/usePost';
import EmpresaForm from './form/EmpresaForm.vue';
import type { Empresa } from './types';
import { sanitizeMaskedInput } from '@/utils/sanitizarMascaras';
import { useToast } from 'primevue';
import { reactive } from 'vue';

const { post, error } = usePost<Empresa, Empresa>()
const toast = useToast()

const initialValues = reactive<Empresa>({
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

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const onFormSubmit = async ({ valid, values, reset }: { valid: boolean, values: Record<string, any>, reset: () => void }) => {
    if (valid) {
        values.cnpj = sanitizeMaskedInput(values.cnpj)
        values.endereco.cep = sanitizeMaskedInput(values.endereco.cep)
        await post("/empresas", values as Empresa)
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
            summary: 'Empresa criada com sucesso.',
            life: 3000
        });
        reset()
    }
};
</script>

<template>
  <EmpresaForm title="Criar nova empresa" description="Preencha as informações para registrar uma nova empresa." button-label="Salvar empresa" :initialValues :onFormSubmit />
</template>