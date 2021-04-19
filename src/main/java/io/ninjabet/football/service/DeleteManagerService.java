package io.ninjabet.football.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.DeleteManagerEntity;
import io.ninjabet.football.repository.DeleteManagerRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Optional;

public abstract class DeleteManagerService<T extends DeleteManagerEntity, ID,
        R extends CrudRepository<T, ID> & DeleteManagerRepository<T>> {

    protected final R crudRepository;

    protected final UserService userService;

    public DeleteManagerService(
            R crudRepository,
            UserService userService
    ) {
        this.crudRepository = crudRepository;
        this.userService = userService;
    }

    protected boolean setEntityDeleted(ID id, boolean deleted) {
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
