/**
 * Interface para a ordenação do Spring Data.
 */
export interface Sort {
  sorted: boolean;
  empty: boolean;
  unsorted: boolean;
}

/**
 * Interface para a paginação do Spring Data.
 */
export interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  offset: number;
  paged: boolean;
  unpaged: boolean;
}

/**
 * Interface para o retorno de uma lista paginada.
 * O tipo genérico T representa a entidade contida na página (ex: Empresa, Fornecedor).
 */
export interface Page<T> {
  content: T[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: Sort;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}