package io.ninjabet.football.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.DeleteManagerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Optional;

public abstract class DeleteManagerService<T extends DeleteManagerEntity, ID> {

    protected final CrudRepository<T, ID> crudRepository;

    private final UserService userService;

    public DeleteManagerService(
            CrudRepository<T, ID> crudRepository,
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

        if (deleted) {
            local.get().setDeleteDate(new Date());
        } else {
            local.get().setDeleteDate(null);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> user = userService.getUserByEmail(username);

//        if (user.isPresent()) {
//            local.get().setLastDeleteActionUser(user.get());
//        }

        user.ifPresent(value -> local.get().setLastDeleteActionUser(value));

        local.get().setDeleted(deleted);

        this.crudRepository.save(local.get());

        return true;
    }
}
