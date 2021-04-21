package io.ninjabet.core.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.entity.ActionLogger;
import io.ninjabet.core.entity.DeleteManagerEntity;
import io.ninjabet.core.repository.ActionLoggerRepository;
import io.ninjabet.core.repository.DeleteManagerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public abstract class DeleteLoggerDMCrudService
        <T extends DeleteManagerEntity & AbstractEntity<ID>, ID, R extends CrudRepository<T, ID> & DeleteManagerRepository<T>>
        extends DeleteManagerCrudService<T, ID, R> {

    private final ActionLoggerRepository actionLoggerRepository;
    private final Class<T> tClass;
    private final UserService userService;

    public DeleteLoggerDMCrudService(
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

    @Transactional
    @Override
    public boolean delete(ID id) {
        if (super.delete(id)) {
            saveAction(id, "delete");
            return true;

        } else {
            saveAction(id, "delete_fail");
            return false;
        }
    }

    @Transactional
    @Override
    public boolean restore(ID id) {
        if (super.restore(id)) {
            saveAction(id, "restore");
            return true;
        } else {
            saveAction(id, "restore_fail");
            return false;
        }
    }

    protected Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails) ?
                userService.getUserByEmail(((UserDetails) principal).getUsername()) : Optional.empty();
    }

    protected void saveAction(ID id, String action) {
        actionLoggerRepository.save(new ActionLogger(
                tClass.getSimpleName(),
                id.toString(),
                action,
                new Date(),
                getCurrentUser().isPresent() ? getCurrentUser().get() : null
        ));
    }
}
