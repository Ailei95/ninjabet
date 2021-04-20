package io.ninjabet.football.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.AbstractEntity;
import io.ninjabet.football.entity.DeleteManagerEntity;
import io.ninjabet.football.repository.DeleteManagerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Optional;

public abstract class DeleteManagerCrudService<T extends DeleteManagerEntity & AbstractEntity<ID>, ID,
        R extends CrudRepository<T, ID> & DeleteManagerRepository<T>> extends CrudService<T, ID, R> {

    protected final UserService userService;

    public DeleteManagerCrudService(
            R crudRepository,
            UserService userService
    ) {
        super(crudRepository);
        this.userService = userService;
    }

    @Override
    public Iterable<T> findAll() { return this.crudRepository.findAllByDeletedFalse(); }

    @Override
    public boolean delete(ID id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restore(ID id) { return this.setEntityDeleted(id, false); }

    private boolean setEntityDeleted(ID id, boolean deleted) {
        Optional<T> local = this.crudRepository.findById(id);

        if (!local.isPresent()) {
            return false;
        }

        local.get().setDeleteDate(deleted ? new Date() : null);

        Optional<User> user = this.getCurrentUser();

        user.ifPresent(value -> local.get().setLastDeleteActionUser(value));

        local.get().setDeleted(deleted);

        this.crudRepository.save(local.get());

        return true;
    }

    private Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userService.getUserByEmail(username);
    }
}
