import type { Endereco } from "../endereco/types";

export interface Empresa {
  id: string;
  cnpj: string;
  nomeFantasia: string;
  endereco: Endereco;
}

export interface EmpresaInput {
  cnpj: string;
  nomeFantasia: string;
  endereco: Endereco;
}