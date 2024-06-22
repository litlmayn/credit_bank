package ru.litlmayn.deal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.litlmayn.deal.models.Credit;

import java.util.UUID;

public interface CreditRepository extends CrudRepository<Credit, UUID> {
}
