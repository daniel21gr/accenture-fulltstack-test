export function validarCNPJ(cnpj: string): boolean {
  // Remover caracteres não numéricos
  cnpj = cnpj.replace(/[^\d]+/g, '');

  // Verifica se o CNPJ tem 14 caracteres
  if (cnpj.length !== 14) return false;

  // Impede CNPJs com todos os dígitos iguais (exemplo: 11111111111111)
  if (/^(\d)\1{13}$/.test(cnpj)) return false;

  // Valida o primeiro dígito verificador
  let soma = 0;
  let peso = [5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];
  for (let i = 0; i < 12; i++) {
    soma += parseInt(cnpj.charAt(i)) * peso[i];
  }
  let digito1 = 11 - (soma % 11);
  if (digito1 === 10 || digito1 === 11) digito1 = 0;
  if (parseInt(cnpj.charAt(12)) !== digito1) return false;

  // Valida o segundo dígito verificador
  soma = 0;
  peso = [6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];
  for (let i = 0; i < 13; i++) {
    soma += parseInt(cnpj.charAt(i)) * peso[i];
  }
  let digito2 = 11 - (soma % 11);
  if (digito2 === 10 || digito2 === 11) digito2 = 0;
  if (parseInt(cnpj.charAt(13)) !== digito2) return false;

  return true;
}
