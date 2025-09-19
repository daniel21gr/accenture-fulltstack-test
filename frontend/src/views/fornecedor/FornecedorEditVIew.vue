<script setup lang="ts">
import FornecedorForm from './form/FornecedorForm.vue';

const onFormSubmit = ({ valid }: { valid: boolean }) => {
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
  <FornecedorForm title="Editar fornecedor" description="Preencha as informações para editar um fornecedor." button-label="Editar fornecedor" :onFormSubmit />
</template>