<template>
  <div class="surface-ground flex items-center justify-center">
    <div class="surface-card p-4 shadow-2 border-round w-full max-w-4xl">
      <Toast />

      <div class="text-center mb-5">
        <h1 class="text-2xl font-bold mb-2">{{ title }}</h1>
        <p class="text-500 font-medium">{{ description }}</p>
      </div>

      <Form v-slot="$form" ref="formRef" @submit="onFormSubmit" class="flex flex-col gap-4" :initialValues :resolver>

        <!-- Informações da Empresa -->
        <div class="p-4 border-1 border-gray-200 rounded-2xl flex flex-col gap-3">
          <h4 class="mb-2">Informações da Empresa</h4>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="cnpj">CNPJ</label>
              <InputMask id="cnpj" name="cnpj" type="text" placeholder="99.999.999/9999-99" mask="99.999.999/9999-99" class="w-full" />
              <Message v-if="$form.cnpj?.invalid" severity="error" class="mt-1">{{ $form.cnpj.error?.message }}</Message>
            </div>
            <div>
              <label for="nomeFantasia">Nome Fantasia</label>
              <InputText id="nomeFantasia" name="nomeFantasia" type="text" class="w-full" />
               <Message v-if="$form.nomeFantasia?.invalid" severity="error" class="mt-1">{{ $form.nomeFantasia.error?.message }}</Message>
            </div>
          </div>
        </div>

        <!-- Endereço -->
        <div class="p-4 border-1 border-gray-200 rounded-2xl  flex flex-col gap-3">
          <h4 class="mb-2">Endereço</h4>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div>
              <label for="cep">CEP</label>
              <InputMask id="cep" name="endereco.cep" type="text" placeholder="99999-999" mask="99999-999" class="w-full" @value-change="(dados) => { onCepChange($form, dados) }" />
               <Message v-if="$form.endereco?.cep?.invalid || erroCep" severity="error" class="mt-1">{{ erroCep ? "CEP inválido" : $form.endereco.cep.error?.message }}</Message>
            </div>
            <div>
              <label for="rua">Rua</label>
              <InputText id="rua" name="endereco.rua" type="text" class="w-full" readonly />
            </div>
            <div>
              <label for="numero">Número</label>
              <InputNumber id="numero" name="endereco.numero" class="w-full" />
               <Message v-if="$form.endereco?.numero.invalid" severity="error" class="mt-1">{{ $form.endereco.numero.error?.message }}</Message>
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

        <!-- Botão de Submit -->
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
import type { Empresa } from '../types';
import { useRouter } from 'vue-router';
import type { FormInstance } from '@primevue/forms';

type EmpresaFormProps = {
  title: string,
  description: string,
  buttonLabel: string,
  initialValues: Empresa,
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onFormSubmit: ({ valid, values, reset }: { valid: boolean, values: Record<string, any>, reset: () => void }) => void
}

const { initialValues } = defineProps<EmpresaFormProps>();

const formEmpresaRef = useTemplateRef<FormInstance>('formRef')
const router = useRouter()
const erroCep = ref(false)

watch(() => initialValues, async () => {
  formEmpresaRef.value?.setValues(initialValues)
  formEmpresaRef.value?.setFieldValue('endereco.cep', initialValues.endereco.cep)
  formEmpresaRef.value?.setFieldValue('endereco.numero', initialValues.endereco.numero)
})


// eslint-disable-next-line @typescript-eslint/no-explicit-any
const resolver = async ({ values }: { values: Record<string, any> }) => {
  const errors: {
    cnpj?: Array<{ message: string }>,
    nomeFantasia?: Array<{ message: string }>
    endereco?: {
      cep?: Array<{ message: string }>
      numero?: Array<{ message: string }>
    }
  } = {}

  if (!validarCNPJ(values.cnpj)) {
    errors.cnpj = [{ message: 'CNPJ inválido!' }];
  }

  if (!values.cnpj) {
    errors.cnpj = [{ message: 'CNPJ é obrigatório.' }]
  }

  if (!values.nomeFantasia) {
    errors.nomeFantasia = [{ message: 'Nome fantasia é obrigatório.' }]
  }

  if (!values.endereco.cep) {
    errors.endereco = { cep: [{ message: 'CEP é obrigatório.' }] }
  }

  if (!values.endereco.numero) {
    errors.endereco = { ...errors.endereco, numero: [{ message: 'Número é obrigatório.' }] }
  }

  return {
    values,
    errors
  }
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const onCepChange = async (form: any, dados: string) => {
  if (dados === '') {
    return
  }

  const data = await buscarCep(dados)

  if (data.erro) {
    erroCep.value = true
    return
  }

  form.endereco.rua.value = data.logradouro
  form.endereco.bairro.value = data.bairro
  form.endereco.cidade.value = data.localidade
  form.endereco.uf.value = data.uf
  form.endereco.estado.value = data.estado
  erroCep.value = false
}

const onClickReturn = () => {
  router.push({ name: "Empresa"})
}
</script>