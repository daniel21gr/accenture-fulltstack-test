package com.accenture.application.domain.repositories;

import com.accenture.application.domain.models.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID>, JpaSpecificationExecutor<Fornecedor> {
}