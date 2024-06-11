package ru.litlmayn.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.litlmayn.models.Client;
import ru.litlmayn.models.Statement;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {
    Optional<Client> findStatementByClientId(UUID clientId);
}
