<template>
  <div class="surface-ground flex items-center justify-center">
    <div class="surface-card p-4 shadow-2 border-round w-full max-w-4xl">
      <Toast />

      <div class="text-center mb-5">
        <h1 class="text-2xl font-bold mb-2">{{ title }}</h1>
        <p class="text-500 font-medium">{{ description }}</p>
      </div>

      <Form v-slot="$form" @submit="onFormSubmit" class="flex flex-col gap-4" :initialValues :resolver>

        <!-- Dados Principais -->
        <div class="p-4 border-1 border-gray-200 rounded-2xl flex flex-col gap-3">
          <h4 class="mb-2">Dados do Fornecedor</h4>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="tipoFornecedor">Tipo de Fornecedor</label>
              <Dropdown
                id="tipoFornecedor"
                name="tipoFornecedor"
                :options="['PESSOA_FISICA', 'PESSOA_JURIDICA']"
                placeholder="Selecione um tipo"
                v-model="initialValues.tipoFornecedor"
                class="w-full"
              />
            </div>
            <div>
              <label for="nome">Nome</label>
              <InputText id="nome" name="nome" type="text" class="w-full" />
              <Message v-if="$form.nome?.invalid" severity="error" class="mt-1">{{ $form.nome.error?.message }}</Message>
            </div>
            <div class="md:col-span-2">
              <label for="documento">{{ initialValues.tipoFornecedor === 'PESSOA_FISICA' ? 'CPF' : 'CNPJ' }}</label>
              <InputMask
                id="documento"
                name="documento"
                :mask="initialValues.tipoFornecedor === 'PESSOA_FISICA' ? '999.999.999-99' : '99.999.999/9999-99'"
                type="text"
                class="w-full"
              />
              <Message v-if="$form.documento?.invalid" severity="error" class="mt-1">{{ $form.documento.error?.message }}</Message>
            </div>
          </div>
        </div>

        <!-- Dados Adicionais PF -->
        <div v-if="initialValues.tipoFornecedor === 'PESSOA_FISICA'" class="p-4 border-1 border-gray-200 rounded-2xl flex flex-col gap-3">
          <h4 class="mb-2">Dados Pessoais</h4>
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
              <InputMask
                id="cep"
                name="endereco.cep"
                type="text"
                mask="99999-999"
                class="w-full"
                @value-change="(dados) => onCepChange($form, dados)"
              />
              <Message
                v-if="$form.endereco?.cep?.invalid || erroCep"
                severity="error"
                class="mt-1"
              >{{ erroCep ? 'CEP inválido' : $form.endereco.cep.error?.message }}</Message>
            </div>
            <div>
              <label for="rua">Rua</label>
              <InputText id="rua" name="endereco.rua" type="text" class="w-full" />
            </div>
            <div>
              <label for="numero">Número</label>
              <InputNumber id="numero" name="endereco.numero" class="w-full" />
              <Message v-if="$form.endereco?.numero?.invalid" severity="error" class="mt-1">{{ $form.endereco.numero.error?.message }}</Message>
            </div>
            <div>
              <label for="bairro">Bairro</label>
              <InputText id="bairro" name="endereco.bairro" type="text" class="w-full" />
            </div>
            <div>
              <label for="cidade">Cidade</label>
              <InputText id="cidade" name="endereco.cidade" type="text" class="w-full" />
            </div>
            <div>
              <label for="uf">UF</label>
              <InputText id="uf" name="endereco.uf" type="text" class="w-full" />
            </div>
            <div>
              <label for="estado">Estado</label>
              <InputText id="estado" name="endereco.estado" type="text" class="w-full" />
            </div>
          </div>
        </div>

        <!-- Botão -->
        <div class="flex justify-center mt-4">
          <Button type="submit" :label="buttonLabel" class="p-button-lg" />
        </div>
      </Form>
    </div>
  </div>
</template>


<script setup lang="ts">
import { ref, reactive } from 'vue';
import { buscarCep } from '@/api/viaCep';
import { validarCNPJ } from '@/validations/cnpj';
import { validarCPF } from '@/validations/cpf';

type FornecedorFormProps = {
  title: string,
  description: string,
  buttonLabel: string,
  onFormSubmit: ({ valid }: { valid: boolean }) => void
}

defineProps<FornecedorFormProps>();

const erroCep = ref(false)

const initialValues = reactive({
  tipoFornecedor: 'PESSOA_JURIDICA',
  nome: '',
  documento: '',
  rg: '',
  dataNascimento: '',
  endereco: {
    cep: '',
    rua: '',
    numero: null,
    bairro: '',
    cidade: '',
    uf: '',
    estado: ''
  }
});

// Validação do formulário
const resolver = async ({ values }: { values: Record<string, any> }) => {
  const errors: any = {}

  if (!values.nome) {
    errors.nome = [{ message: 'Nome é obrigatório.' }]
  }

  if (!values.documento) {
    errors.documento = [{ message: 'Documento é obrigatório.' }]
  } else {
    const tipo = values.tipoFornecedor
    const isValido = tipo === 'PESSOA_JURIDICA' ? validarCNPJ(values.documento) : validarCPF(values.documento)

    if (!isValido) {
      errors.documento = [{ message: tipo === 'PESSOA_JURIDICA' ? 'CNPJ inválido!' : 'CPF inválido!' }]
    }
  }

  if (!values.endereco?.cep) {
    errors.endereco = { ...errors.endereco, cep: [{ message: 'CEP é obrigatório.' }] }
  }

  if (!values.endereco?.numero) {
    errors.endereco = { ...errors.endereco, numero: [{ message: 'Número é obrigatório.' }] }
  }

  return {
    values,
    errors
  }
}

// Buscar CEP
const onCepChange = async (form: any, cep: string) => {
  const data = await buscarCep(cep)

  if (data.erro) {
    erroCep.value = true
    return
  }

  erroCep.value = false
  form.endereco.rua.value = data.logradouro
  form.endereco.bairro.value = data.bairro
  form.endereco.cidade.value = data.localidade
  form.endereco.uf.value = data.uf
  form.endereco.estado.value = data.estado ?? ''
}
</script>
