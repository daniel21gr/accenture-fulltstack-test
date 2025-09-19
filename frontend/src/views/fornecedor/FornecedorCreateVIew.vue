<script setup lang="ts">
import FornecedorForm from './form/FornecedorForm.vue';

const onFormSubmit = ({ valid }: { valid: boolean }) => {
    if (valid) {
        /* toast.add({
            severity: 'success',
            summary: 'Form is submitted.',
            life: 3000
        }); */
        console.log('teste de salvar');
    }
};
</script>

<template>
  <FornecedorForm title="Criar novo fornecedor" description="Preencha as informações para registrar um novo fornecedor." button-label="Salvar fornecedor" :onFormSubmit />
</template>