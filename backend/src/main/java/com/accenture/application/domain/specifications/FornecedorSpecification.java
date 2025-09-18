package com.accenture.application.domain.specifications;

import com.accenture.application.domain.dtos.filters.FornecedorFiltroDTO;
import com.accenture.application.domain.models.Fornecedor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FornecedorSpecification {

    public static Specification<Fornecedor> byFilter(FornecedorFiltroDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Nome (case-insensitive)
            if (StringUtils.hasText(filter.getNome())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        "%" + filter.getNome().toLowerCase() + "%"
                ));
            }

            // Filtro por Documento (CPF/CNPJ)
            if (StringUtils.hasText(filter.getDocumento())) {
                String documentoLimpo = filter.getDocumento().replaceAll("[^0-9]", "");
                predicates.add(criteriaBuilder.equal(
                        root.get("documento"),
                        documentoLimpo
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}