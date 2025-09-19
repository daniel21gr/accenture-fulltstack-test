<script setup lang="ts">
import { usePost } from '@/api/composables/usePost';
import FornecedorForm from './form/FornecedorForm.vue';
import { TipoFornecedor, type Fornecedor } from './types';
import { sanitizeMaskedInput } from '@/utils/sanitizarMascaras';
import { useToast } from 'primevue';
import { reactive } from 'vue';

const { post, error } = usePost<Fornecedor, Fornecedor>();
const toast = useToast();

const initialValues = reactive<Fornecedor>({
  id: '',
  documento: '',
  tipoFornecedor: TipoFornecedor.PESSOA_JURIDICA,
  nome: '',
  email: '',
  endereco: {
    cep: '',
    rua: '',
    numero: 0,
    bairro: '',
    cidade: '',
    uf: '',
    estado: ''
  },
  rg: '',
  dataNascimento: '',
  empresas: []
});

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const onFormSubmit = async ({ valid, values, reset }: { valid: boolean, values: Record<string, any>, reset: () => void }) => {
  if (valid) {
    values.documento = sanitizeMaskedInput(values.documento);
    values.endereco.cep = sanitizeMaskedInput(values.endereco.cep);

    await post('/fornecedores', values as Fornecedor);

    if (error.value) {
      const errorResponse = typeof error?.value?.response?.data === 'object'
        ? Object.values(error?.value?.response?.data as Record<string, string>).join('\n')
        : error?.value?.response?.data?.toString();

      toast.add({
        severity: 'error',
        summary: errorResponse ?? '',
        life: 3000
      });

      return;
    }

    toast.add({
      severity: 'success',
      summary: 'Fornecedor criado com sucesso.',
      life: 3000
    });

    reset();
  }
};
</script>

<template>
  <FornecedorForm
    title="Criar novo fornecedor"
    description="Preencha as informações para cadastrar um novo fornecedor."
    button-label="Salvar fornecedor"
    :initialValues="initialValues"
    :onFormSubmit
  />
</template>
