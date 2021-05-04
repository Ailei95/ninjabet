package io.ninjabet.core.repository;

import io.ninjabet.core.entity.ActionLogger;
import org.springframework.data.repository.CrudRepository;

@Deprecated
public interface ActionLoggerRepository extends CrudRepository<ActionLogger, Long> {
}
