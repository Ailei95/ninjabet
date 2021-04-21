package io.ninjabet.core.service;

import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.entity.DeleteManagerEntity;
import io.ninjabet.core.repository.DeleteManagerRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public abstract class DeleteManagerCrudService<T extends DeleteManagerEntity & AbstractEntity<ID>, ID,
        R extends CrudRepository<T, ID> & DeleteManagerRepository<T>> extends CrudService<T, ID, R> {

    public DeleteManagerCrudService(
            R crudRepository
    ) {
        super(crudRepository);
    }

    @Override
    public Iterable<T> findAll() {
        return this.crudRepository.findAllByDeleteDateEquals(new Date(0));
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<T> local = this.crudRepository.findById(id);

        if (local.isPresent() && local.get().getDeleteDate().getTime() == new Date(0).getTime()) { return local; }

        return Optional.empty();
    }

    @Override
    public T add(T t) { t.setDeleteDate(new Date(0)); return this.crudRepository.save(t); }

    @Override
    public boolean delete(ID id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restore(ID id) { return this.setEntityDeleted(id, false); }

    private boolean setEntityDeleted(ID id, boolean deleted) {
        Optional<T> local = this.findById(id);

        if (!local.isPresent()) {
            return false;
        }

        local.get().setDeleteDate(deleted ? new Date() : new Date(0));

        this.crudRepository.save(local.get());

        return true;
    }
}
