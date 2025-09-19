<template>
  <div class="surface-ground flex items-center justify-center">
    <div class="surface-card p-4 shadow-2 border-round w-full max-w-4xl">
      <Toast />

      <div class="text-center mb-5">
        <h1 class="text-2xl font-bold mb-2">{{ title }}</h1>
        <p class="text-500 font-medium">{{ description }}</p>
      </div>

      <Form v-slot="$form" ref="formRef" @submit="onFormSubmit" class="flex flex-col gap-4" :initialValues="initialValues" :resolver="resolver">

        <!-- Informações do Fornecedor -->
        <div class="p-4 border-1 border-gray-200 rounded-2xl flex flex-col gap-3">
          <h4 class="mb-2">Informações do Fornecedor</h4>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="tipoFornecedor">Tipo de Fornecedor</label>
              <Dropdown
                id="tipoFornecedor"
                name="tipoFornecedor"
                :options="['PESSOA_FISICA', 'PESSOA_JURIDICA']"
                placeholder="Selecione um tipo"
                class="w-full"
              />
              <Message v-if="$form.tipoFornecedor?.invalid" severity="error" class="mt-1">
                {{ $form.tipoFornecedor.error?.message }}
              </Message>
            </div>
            <div>
              <label for="documento">CNPJ ou CPF</label>
              <InputMask id="documento" name="documento" type="text" :mask="$form.tipoFornecedor?.value === TipoFornecedor.PESSOA_FISICA ? '999.999.999-99' : '99.999.999/9999-99'" class="w-full" />
              <Message v-if="$form.documento?.invalid" severity="error" class="mt-1">{{ $form.documento.error?.message }}</Message>
            </div>
            <div>
              <label for="nome">Nome</label>
              <InputText id="nome" name="nome" type="text" class="w-full" />
               <Message v-if="$form.nome?.invalid" severity="error" class="mt-1">{{ $form.nome.error?.message }}</Message>
            </div>
            <div>
              <label for="email">E-mail</label>
              <InputText id="email" name="email" type="email" class="w-full" />
               <Message v-if="$form.email?.invalid" severity="error" class="mt-1">{{ $form.email.error?.message }}</Message>
            </div>
          </div>
        </div>

        <!-- Dados Adicionais (Para Pessoa Física) -->
        <div v-if="$form.tipoFornecedor?.value === TipoFornecedor.PESSOA_FISICA" class="p-4 border-1 border-gray-200 rounded-2xl flex flex-col gap-3">
          <h4 class="mb-2">Dados Adicionais</h4>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="rg">RG</label>
              <InputText id="rg" name="rg" type="text" class="w-full" />
               <Message v-if="$form.rg?.invalid" severity="error" class="mt-1">{{ $form.rg.error?.message }}</Message>
            </div>
            <div>
              <label for="dataNascimento">Data de Nascimento</label>
              <Calendar id="dataNascimento" name="dataNascimento" dateFormat="yy-mm-dd" class="w-full" />
               <Message v-if="$form.dataNascimento?.invalid" severity="error" class="mt-1">{{ $form.dataNascimento.error?.message }}</Message>
            </div>
          </div>
        </div>

        <!-- Endereço -->
        <div class="p-4 border-1 border-gray-200 rounded-2xl flex flex-col gap-3">
          <h4 class="mb-2">Endereço</h4>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div>
              <label for="cep">CEP</label>
              <InputMask id="cep" name="endereco.cep" type="text" placeholder="99999-999" mask="99999-999" class="w-full" @value-change="(dados) => { onCepChange($form, dados) }" />
               <Message v-if="($form.endereco as any)?.cep?.invalid || erroCep" severity="error" class="mt-1">{{ erroCep ? "CEP inválido" : ($form.endereco as any)?.cep.error?.message }}</Message>
            </div>
            <div>
              <label for="rua">Rua</label>
              <InputText id="rua" name="endereco.rua" type="text" class="w-full" readonly />
            </div>
            <div>
              <label for="numero">Número</label>
              <InputNumber id="numero" name="endereco.numero" class="w-full" />
               <Message v-if="($form.endereco as any)?.numero?.invalid" severity="error" class="mt-1">{{ ($form.endereco as any)?.numero.error?.message }}</Message>
            </div>
            <div>
              <label for="bairro">Bairro</label>
              <InputText id="bairro" name="endereco.bairro" type="text" class="w-full" readonly />
            </div>
            <div>
              <label for="cidade">Cidade</label>
              <InputText id="cidade" name="endereco.cidade" type="text" class="w-full" readonly />
            </div>
            <div>
              <label for="uf">UF</label>
              <InputText id="uf" name="endereco.uf" type="text" class="w-full" readonly />
            </div>
            <div>
              <label for="estado">Estado</label>
              <InputText id="estado" name="endereco.estado" type="text" class="w-full" readonly />
            </div>
          </div>
        </div>

        <!-- Botões de Ação -->
        <div class="flex justify-center mt-4 gap-x-3">
          <Button @click="onClickReturn" label="Voltar para listagem" severity="secondary" outlined />
          <Button type="submit" :label="buttonLabel" class="p-button-lg" />
        </div>
      </Form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { buscarCep } from '@/api/viaCep';
import { validarCNPJ } from '@/validations/cnpj';
import { ref, useTemplateRef, watch } from 'vue';
import { useRouter } from 'vue-router';
import { TipoFornecedor, type Fornecedor } from '../types'; // Altere para o tipo Fornecedor no lugar de Empresa
import type { FormInstance } from '@primevue/forms';
import { validarCPF } from '@/validations/cpf';

type FornecedorFormProps = {
  title: string;
  description: string;
  buttonLabel: string;
  initialValues: Fornecedor; // Altere para o tipo Fornecedor
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onFormSubmit: ({ valid, values, reset }: { valid: boolean, values: Record<string, any>, reset: () => void }) => void;
}

const { initialValues } = defineProps<FornecedorFormProps>();

const formFornecedorRef = useTemplateRef<FormInstance>('formRef');
const router = useRouter();
const erroCep = ref(false);

watch(() => initialValues, async () => {
  formFornecedorRef.value?.setValues(initialValues);
  formFornecedorRef.value?.setFieldValue('endereco.cep', initialValues.endereco.cep);
  formFornecedorRef.value?.setFieldValue('endereco.numero', initialValues.endereco.numero);
});

// Resolver validação
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const resolver = async ({ values }: { values: Record<string, any> }) => {
  const errors: {
    documento?: Array<{ message: string }>;
    nome?: Array<{ message: string }>;
    email?: Array<{ message: string }>;
    rg?: Array<{ message: string }>;
    dataNascimento?: Array<{ message: string }>;
    endereco?: {
      cep?: Array<{ message: string }>;
      numero?: Array<{ message: string }>;
    };
  } = {};

  if (values.tipoFornecedor == TipoFornecedor.PESSOA_FISICA) {
    if (!validarCPF(values.documento)) {
      errors.documento = [{ message: 'CPF inválido!' }];
    }

    if (!values.rg) {
      errors.rg = [{ message: 'RG é obrigatório para Pessoa Física.' }];
    }
    if (!values.dataNascimento) {
      errors.dataNascimento = [{ message: 'Data de nascimento é obrigatória para Pessoa Física.'}];
    }

  } else {
    if (!validarCNPJ(values.documento)) {
      errors.documento = [{ message: 'CNPJ inválido!' }];
    }
  }

  if (!values.documento) {
    errors.documento = [{ message: 'Documento é obrigatório.' }];
  }

  if (!values.nome) {
    errors.nome = [{ message: 'Nome é obrigatório.' }];
  }

  if (!values.email) {
    errors.email = [{ message: 'E-mail é obrigatório.' }];
  }

  if (!values.endereco.cep) {
    errors.endereco = { cep: [{ message: 'CEP é obrigatório.' }] };
  }

  if (!values.endereco.numero) {
    errors.endereco = { ...errors.endereco, numero: [{ message: 'Número é obrigatório.' }] };
  }

  return {
    values,
    errors
  };
};

// Função para busca de CEP
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const onCepChange = async (form: any, dados: string) => {
  if (dados === '') return;

  const data = await buscarCep(dados);

  if (data.erro) {
    erroCep.value = true;
    return;
  }

  form.endereco.rua.value = data.logradouro;
  form.endereco.bairro.value = data.bairro;
  form.endereco.cidade.value = data.localidade;
  form.endereco.uf.value = data.uf;
  form.endereco.estado.value = data.estado;
  erroCep.value = false;
};

// Redirecionar para listagem
const onClickReturn = () => {
  router.push({ name: "Fornecedor" });
};
</script>
