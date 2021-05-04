package io.ninjabet.core.service;

import io.ninjabet.auth.service.NinjaBetUserDetailsService;
import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.repository.ActionLoggerRepository;
import org.springframework.data.repository.CrudRepository;

// TODO

@Deprecated
public class ActionLoggerCrudService
        <T extends AbstractEntity<ID>, ID, R extends CrudRepository<T, ID>>
        extends CrudService<T, ID, R> {

    private final ActionLoggerRepository actionLoggerRepository;
    private final Class<T> tClass;
    private final NinjaBetUserDetailsService ninjaBetUserDetailsService;

    public ActionLoggerCrudService(
            Class<T> tClass,
            ActionLoggerRepository actionLoggerRepository,
            R crudRepository,
            NinjaBetUserDetailsService ninjaBetUserDetailsService
    ) {
        super(crudRepository);
        this.tClass = tClass;
        this.actionLoggerRepository = actionLoggerRepository;
        this.ninjaBetUserDetailsService = ninjaBetUserDetailsService;
    }
}
