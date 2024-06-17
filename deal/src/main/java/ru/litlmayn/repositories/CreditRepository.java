package ru.litlmayn.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.litlmayn.models.Credit;

import java.util.UUID;

public interface CreditRepository extends CrudRepository<Credit, UUID> {
}
