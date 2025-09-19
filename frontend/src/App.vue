<script setup lang="ts">
import NavBar from './components/navbar/NavBar.vue';

</script>

<template>
  <Toast />
  <ConfirmDialog></ConfirmDialog>
  <NavBar />
  <div class="p-12">
    <RouterView />
  </div>
</template>

<style scoped></style>
