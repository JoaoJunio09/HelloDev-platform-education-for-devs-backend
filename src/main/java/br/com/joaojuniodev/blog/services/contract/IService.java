package br.com.joaojuniodev.blog.services.contract;

import br.com.joaojuniodev.blog.data.dto.model.PostDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface IService<D> {

    PagedModel<EntityModel<PostDTO>> findAll(Pageable pageable);
    D findById(Long id);
    D create(D object);
    D update(D object);
    void delete(Long id);
}
