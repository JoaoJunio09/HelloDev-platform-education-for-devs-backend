package br.com.joaojuniodev.blog.services.contract;

import java.util.List;

public interface IService<D> {

    List<D> findAll();
    D findById(Long id);
    D create(D object);
    D update(D object);
    void delete(Long id);
}