/**
 * Remove caracteres não numéricos de uma string com máscara.
 * Ex: '123.456.789-00' → '12345678900'
 *
 * @param input - String com máscara
 * @returns String somente com números
 */
export function sanitizeMaskedInput(input: string | null | undefined): string {
  if (!input) return '';
  return input.replace(/[^\d]/g, '');
}
