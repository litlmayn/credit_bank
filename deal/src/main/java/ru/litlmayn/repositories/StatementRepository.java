package ru.litlmayn.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.litlmayn.models.Statement;

import java.util.Optional;
import java.util.UUID;

public interface StatementRepository extends CrudRepository<Statement, UUID> {
    Optional<Statement> findStatementByStatementId(UUID statementId);
}
