package com.ecetasci.repo;

import java.util.List;
import java.util.Optional;

public interface ICrud<T, ID> {
    T save(T entity);

    Iterable<T> saveAll(Iterable<T> entities);

    boolean deleteById(ID id);

    T update(T entity);

    List<T> findAll();

    Optional<T> findById(ID id);

    boolean existsById(ID id);
}
