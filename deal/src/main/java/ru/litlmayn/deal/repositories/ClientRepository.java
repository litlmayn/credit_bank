package ru.litlmayn.deal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.litlmayn.deal.models.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {
    Optional<Client> findStatementByClientId(UUID clientId);
}
