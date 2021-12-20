package br.com.oi.batchvalidation.repository;

import br.com.oi.batchvalidation.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BatchRepository extends JpaRepository<Batch, UUID> {
}
