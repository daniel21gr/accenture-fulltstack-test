<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import EmpresaForm from './form/EmpresaForm.vue';
import { useRoute } from 'vue-router';
import { useGet } from '@/api/composables/useGet';
import type { Empresa } from './types';

const route = useRoute()
const { data: empresa, get } = useGet<Empresa>()

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

const onFormSubmit = ({ valid, reset }: { valid: boolean, reset: () => void }) => {
    if (valid) {
        /* toast.add({
            severity: 'success',
            summary: 'Form is submitted.',
            life: 3000
        }); */
        console.log('teste de edição');
    }
};
</script>

<template>
  <EmpresaForm title="Editar empresa" description="Preencha as informações para editar uma empresa." button-label="Editar empresa" :initialValues="empresa ?? emptyForm" :onFormSubmit />
</template>