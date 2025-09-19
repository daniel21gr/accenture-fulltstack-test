import type { Empresa } from "../empresa/types";
import type { Endereco } from "../endereco/types";

export enum TipoFornecedor {
  PESSOA_FISICA = 'PESSOA_FISICA',
  PESSOA_JURIDICA = 'PESSOA_JURIDICA',
}

export interface Fornecedor {
  id: string;
  documento: string;
  tipoFornecedor: TipoFornecedor;
  nome: string;
  email: string;
  rg?: string; // Opcional para Pessoa Jurídica
  dataNascimento?: string; // Opcional para Pessoa Jurídica
  endereco: Endereco;
  empresas?: Empresa[]; // Opcional para a relação muitos-para-muitos
}

export interface FornecedorInput {
  documento: string;
  tipoFornecedor: TipoFornecedor;
  nome: string;
  email: string;
  rg?: string;
  dataNascimento?: string;
  endereco: Endereco;
  empresas?: string[]; // IDs de empresas
}