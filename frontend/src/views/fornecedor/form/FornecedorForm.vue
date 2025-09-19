<template>
  <div class="surface-ground flex align-items-center justify-content-center p-4">
    <div class="surface-card p-4 shadow-2 border-round w-full">
      <Toast />

      <div class="text-center mb-5">
        <h1 class="text-2xl font-bold mb-2">Criar Novo Fornecedor</h1>
        <p class="text-500 font-medium">Insira os dados do novo fornecedor.</p>
      </div>

      <Form @submit="onFormSubmit" class="flex flex-column gap-4">

        <div class="p-4 border-1 border-gray-300 border-round">
          <h4 class="mb-3">Dados Principais</h4>
          <div class="p-fluid flex flex-column gap-3">
            <div>
              <label for="tipoFornecedor">Tipo de Fornecedor</label>
              <Dropdown id="tipoFornecedor" v-model="form.tipoFornecedor" :options="['PESSOA_FISICA', 'PESSOA_JURIDICA']" placeholder="Selecione um tipo" />
            </div>
            <div>
              <label for="nome">Nome</label>
              <InputText id="nome" name="nome" type="text" v-model="form.nome" />
              <Message v-if="formErrors.nome" severity="error" class="mt-1">{{ formErrors.nome }}</Message>
            </div>
            <div>
              <label for="documento">{{ form.tipoFornecedor === 'PESSOA_FISICA' ? 'CPF' : 'CNPJ' }}</label>
              <InputText id="documento" name="documento" type="text" v-model="form.documento" />
              <Message v-if="formErrors.documento" severity="error" class="mt-1">{{ formErrors.documento }}</Message>
            </div>
          </div>
        </div>

        <div v-if="form.tipoFornecedor === 'PESSOA_FISICA'" class="p-4 border-1 border-gray-300 border-round">
          <h4 class="mb-3">Dados Adicionais</h4>
          <div class="p-fluid flex flex-column gap-3">
            <div>
              <label for="rg">RG</label>
              <InputText id="rg" name="rg" type="text" v-model="form.rg" />
              <Message v-if="formErrors.rg" severity="error" class="mt-1">{{ formErrors.rg }}</Message>
            </div>
            <div>
              <label for="dataNascimento">Data de Nascimento</label>
              <Calendar id="dataNascimento" name="dataNascimento" v-model="form.dataNascimento" dateFormat="yy-mm-dd" />
              <Message v-if="formErrors.dataNascimento" severity="error" class="mt-1">{{ formErrors.dataNascimento }}</Message>
            </div>
          </div>
        </div>

        <div class="p-4 border-1 border-gray-300 border-round">
          <h4 class="mb-3">Endereço</h4>
          <div class="p-fluid flex flex-column gap-3">
            <div>
              <label for="cep">CEP</label>
              <InputText id="cep" name="endereco.cep" type="text" placeholder="99999-999" v-model="form.endereco.cep" />
              <Message v-if="formErrors['endereco.cep']" severity="error" class="mt-1">{{ formErrors['endereco.cep'] }}</Message>
            </div>
            <div class="flex flex-row gap-3">
                <div class="flex-1">
                    <label for="rua">Rua</label>
                    <InputText id="rua" name="endereco.rua" type="text" v-model="form.endereco.rua" />
                    <Message v-if="formErrors['endereco.rua']" severity="error" class="mt-1">{{ formErrors['endereco.rua'] }}</Message>
                </div>
                <div class="flex-1">
                    <label for="numero">Número</label>
                    <InputNumber id="numero" name="endereco.numero" v-model="form.endereco.numero" />
                    <Message v-if="formErrors['endereco.numero']" severity="error" class="mt-1">{{ formErrors['endereco.numero'] }}</Message>
                </div>
            </div>
            <div class="flex flex-row gap-3">
                <div class="flex-1">
                    <label for="bairro">Bairro</label>
                    <InputText id="bairro" name="endereco.bairro" type="text" v-model="form.endereco.bairro" />
                    <Message v-if="formErrors['endereco.bairro']" severity="error" class="mt-1">{{ formErrors['endereco.bairro'] }}</Message>
                </div>
                <div class="flex-1">
                    <label for="cidade">Cidade</label>
                    <InputText id="cidade" name="endereco.cidade" type="text" v-model="form.endereco.cidade" />
                    <Message v-if="formErrors['endereco.cidade']" severity="error" class="mt-1">{{ formErrors['endereco.cidade'] }}</Message>
                </div>
            </div>
            <div>
                <label for="uf">UF</label>
                <InputText id="uf" name="endereco.uf" type="text" v-model="form.endereco.uf" />
                <Message v-if="formErrors['endereco.uf']" severity="error" class="mt-1">{{ formErrors['endereco.uf'] }}</Message>
            </div>
          </div>
        </div>

        <Button type="submit" label="Salvar Fornecedor" class="w-full mt-2 p-3 font-bold" />
      </Form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';

const form = reactive({
    nome: '',
    documento: '',
    tipoFornecedor: 'PESSOA_JURIDICA', // Ou um select box para mudar
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

const formErrors = ref({});

const onFormSubmit = async () => {
    formErrors.value = {}; // Reset errors
    try {
        // await fornecedorService.criar(form);
        /* toast.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Fornecedor criado com sucesso!',
            life: 3000
        }); */
        // Clear form or redirect
    } catch (error: any) {
        if (error.response && error.response.status === 400) {
            formErrors.value = error.response.data;
        } else {
            /* toast.add({
                severity: 'error',
                summary: 'Erro',
                detail: 'Ocorreu um erro ao salvar o fornecedor.',
                life: 3000
            }); */
        }
    }
};
</script>