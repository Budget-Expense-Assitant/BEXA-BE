package de.bexa.repository;

import de.bexa.finances.entity.Finances;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceRepository extends MongoRepository<Finances, String> {
    Optional<Finances> findByUserId(String userId);
}
