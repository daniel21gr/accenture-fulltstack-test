<script setup lang="ts">
import EmpresaForm from './form/EmpresaForm.vue';

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
  <EmpresaForm title="Criar nova empresa" description="Preencha as informações para registrar uma nova empresa." button-label="Salvar empresa" :onFormSubmit />
</template>