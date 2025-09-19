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

export async function buscarCep(cep: string): Promise<CEPResponse> {
  // Remover qualquer caractere não numérico do CEP
  const cepFormatado = cep.replace(/\D/g, '');

  // Verificar se o CEP tem 8 dígitos
  if (cepFormatado.length !== 8) {
    throw new Error('CEP inválido, deve ter 8 dígitos.');
  }

  const url = `https://viacep.com.br/ws/${cepFormatado}/json/`;

  try {
    // Realiza a requisição à API ViaCEP
    const response = await fetch(url);

    // Verifica se a resposta foi bem-sucedida
    if (!response.ok) {
      throw new Error('Erro ao buscar o CEP.');
    }

    // Converte a resposta para JSON
    const data: CEPResponse = await response.json();

    // Retorna os dados do CEP
    return data;
  } catch (error) {
    throw new Error(error instanceof Error ? error.message : 'Erro desconhecido');
  }
}
