package ru.litlmayn.deal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.litlmayn.deal.models.Statement;

import java.util.Optional;
import java.util.UUID;

public interface StatementRepository extends CrudRepository<Statement, UUID> {
    Optional<Statement> findStatementByStatementId(UUID statementId);
}
