<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import { useRoute } from 'vue-router';
import { useGet } from '@/api/composables/useGet';
import { useUpdate } from '@/api/composables/useUpdate';
import { sanitizeMaskedInput } from '@/utils/sanitizarMascaras';
import { useToast } from 'primevue';
import { TipoFornecedor, type Fornecedor } from './types';
import FornecedorForm from './form/FornecedorForm.vue';

const route = useRoute();
const toast = useToast();

const { data: fornecedor, get } = useGet<Fornecedor>();
const { error, put } = useUpdate<Fornecedor, Fornecedor>();

const emptyForm = reactive<Fornecedor>({
  id: '',
  documento: '',
  tipoFornecedor: TipoFornecedor.PESSOA_FISICA,
  nome: '',
  email: '',
  rg: '',
  dataNascimento: '',
  endereco: {
    cep: '',
    rua: '',
    numero: 0,
    bairro: '',
    cidade: '',
    uf: '',
    estado: ''
  },
  empresas: []
});

onMounted(async () => {
  const id = route.params.id;
  await get(`/fornecedores/${id}`);
});

const onFormSubmit = async ({ valid, values }: { valid: boolean; values: Record<string, any> }) => {
  if (valid) {
    values.documento = sanitizeMaskedInput(values.documento);
    values.endereco.cep = sanitizeMaskedInput(values.endereco.cep);

    await put(`/fornecedores/${route.params.id}`, values as Fornecedor);

    if (error.value) {
      const errorResponse =
        typeof error?.value?.response?.data === 'object'
          ? Object.values(error?.value?.response?.data as Record<string, string>).join('\n')
          : error?.value?.response?.data?.toString();

      toast.add({
        severity: 'error',
        summary: errorResponse ?? 'Erro ao atualizar fornecedor.',
        life: 3000
      });
      return;
    }

    toast.add({
      severity: 'success',
      summary: 'Fornecedor atualizado com sucesso.',
      life: 3000
    });
  }
};
</script>

<template>
  <FornecedorForm
    title="Editar fornecedor"
    description="Preencha as informações para editar um fornecedor."
    button-label="Editar fornecedor"
    :initialValues="fornecedor ?? emptyForm"
    :onFormSubmit
  />
</template>
