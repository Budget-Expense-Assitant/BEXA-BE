package de.bexa.repository;

import de.bexa.savings.entity.Savings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsRepository extends MongoRepository<Savings, String> {
    Optional<Savings> findByUserId(String userId);
}
