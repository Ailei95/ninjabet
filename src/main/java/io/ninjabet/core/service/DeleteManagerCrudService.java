package io.ninjabet.core.service;

import io.ninjabet.core.entity.AbstractEntity;
import io.ninjabet.core.entity.DeleteManagerEntity;
import io.ninjabet.core.repository.DeleteManagerRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

@Deprecated
public abstract class DeleteManagerCrudService<T extends DeleteManagerEntity & AbstractEntity<ID>, ID,
        R extends CrudRepository<T, ID> & DeleteManagerRepository<T>> extends CrudService<T, ID, R> {

    protected static final Date CONSISTENT = new Date(0);

    public DeleteManagerCrudService(
            R crudRepository
    ) {
        super(crudRepository);
    }

    @Override
    public Iterable<T> findAll() {
        return this.crudRepository.findAllByDeleteDateEquals(CONSISTENT);
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<T> local = this.crudRepository.findById(id);

        if (local.isPresent() && local.get().getDeleteDate().getTime() == CONSISTENT.getTime()) { return local; }

        return Optional.empty();
    }

    @Override
    public T add(T t) { t.setDeleteDate(CONSISTENT); return this.crudRepository.save(t); }

    @Override
    public Optional<T> update(ID id, T t) {
        Optional<T> local = this.findById(id);

        if (!local.isPresent() || !t.getId().equals(id)) { return Optional.empty(); }

        if (t.getDeleteDate() == null) { t.setDeleteDate(CONSISTENT); }

        return Optional.of(this.crudRepository.save(t));
    }

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

        local.get().setDeleteDate(deleted ? new Date() : CONSISTENT);

        this.crudRepository.save(local.get());

        return true;
    }
}
