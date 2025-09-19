<script setup lang="ts">
import EmpresaForm from './form/EmpresaForm.vue';

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
  <EmpresaForm title="Editar empresa" description="Preencha as informações para editar uma empresa." button-label="Editar empresa" :onFormSubmit />
</template>