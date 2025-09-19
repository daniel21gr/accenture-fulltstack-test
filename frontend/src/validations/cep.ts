import { buscarCep } from "@/api/viaCep";

interface CEPResponse {
  cep: string;
  logradouro: string;
  complemento: string;
  bairro: string;
  localidade: string;
  uf: string;
  estado: string;
  regiao: string;
  ibge: string;
  gia: string;
  ddd: string;
  siafi: string;
  erro?: boolean
}

export async function validarCEP(cep: string): Promise<CEPResponse | boolean> {
  try {
    const data = await buscarCep(cep)

    // Verifica se a API retornou um erro
    if (data.erro) {
      // throw new Error('CEP não encontrado.');
      return false
    }

    // Retorna os dados do CEP
    return data;
  } catch (error) {
    throw new Error(error instanceof Error ? error.message : 'Erro desconhecido');
  }
}
