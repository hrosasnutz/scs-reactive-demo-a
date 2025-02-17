package io.codegitters.scs_reactive_demo_a.transaction;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface TransactionRepository extends R2dbcRepository<Transaction, UUID> {
}
