package io.codegitters.scs_reactive_demo_a.account;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface AccountRepository extends R2dbcRepository<Account, UUID> {
}
