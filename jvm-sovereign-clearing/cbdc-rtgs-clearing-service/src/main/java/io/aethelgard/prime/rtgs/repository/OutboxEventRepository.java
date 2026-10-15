package io.aethelgard.prime.rtgs.repository;

import io.aethelgard.prime.rtgs.entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {}
