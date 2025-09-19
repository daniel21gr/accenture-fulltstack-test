<template>
    <div class="card">
        <Menubar :model="items" />
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const items = ref([
    {
        label: 'Empresas',
        icon: 'pi pi-building', // Exemplo de ícone, substitua se necessário
        command: () => { router.push({name: 'Empresa'}) }
    },
    {
        label: 'Fornecedores',
        icon: 'pi pi-users', // Exemplo de ícone, substitua se necessário
        command: () => { router.push({name: 'Fornecedor'}) }
    }
]);
</script>

<style scoped>
/* Estilos opcionais para o menu, se necessário */
</style>