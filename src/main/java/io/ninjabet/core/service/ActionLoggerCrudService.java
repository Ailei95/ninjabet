package io.ninjabet.core.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.repository.ActionLoggerRepository;
import org.springframework.data.repository.CrudRepository;

// TODO

public class ActionLoggerCrudService
        <T extends AbstractEntity<ID>, ID, R extends CrudRepository<T, ID>>
        extends CrudService<T, ID, R> {

    private final ActionLoggerRepository actionLoggerRepository;
    private final Class<T> tClass;
    private final UserService userService;

    public ActionLoggerCrudService(
            Class<T> tClass,
            ActionLoggerRepository actionLoggerRepository,
            R crudRepository,
            UserService userService
    ) {
        super(crudRepository);
        this.tClass = tClass;
        this.actionLoggerRepository = actionLoggerRepository;
        this.userService = userService;
    }
}
