package br.com.joaojuniodev.blog.services.contract;

import java.util.List;

public interface IService<E, D> {

    List<D> findAll();
    D findById(Long id);
    D create(E object);
    D update(E object);
    void delete(Long id);
}
