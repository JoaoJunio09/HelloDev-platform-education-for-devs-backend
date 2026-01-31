package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.controllers.PostController;
import br.com.joaojuniodev.blog.data.dto.model.PostDTO;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.repositories.PostRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PostService implements IService<PostDTO> {

    private final Logger logger = LoggerFactory.getLogger(PostService.class.getName());

    @Autowired
    PostRepository repository;

    @Autowired
    ObjectConvertManually mapper;

    @Override
    public List<PostDTO> findAll() {

        logger.info("Finding all Post's");

        return repository.findAll()
            .stream()
            .map(entity -> addHateoas(mapper.convertPostEntityToDto(entity)))
            .toList();
    }

    @Override
    public PostDTO findById(Long id) {

        logger.info("Finding a one Post");

        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found this ID : " + id));
        return addHateoas(mapper.convertPostEntityToDto(entity));
    }

    @Override
    public PostDTO create(PostDTO post) {

        logger.info("Creating a one Post");

        if (post == null) throw new ObjectIsNullException("The Object [Post] is null");
        if (post.getDate() == null) post.setDate(LocalDate.now().toString());

        var entity = repository.save(mapper.convertPostDtoToEntity(post));
        return addHateoas(mapper.convertPostEntityToDto(entity));
    }

    @Override
    public PostDTO update(PostDTO post) {

        logger.info("Updating a exists Post");

        var entity = repository.findById(post.getId())
                .orElseThrow(() -> new NotFoundException("Not found this ID:" + post.getId()));
        entity.setTitle(post.getTitle());
        entity.setSubTitle(post.getSubTitle());
        entity.setContent(post.getContent());
        return addHateoas(mapper.convertPostEntityToDto(repository.save(entity)));
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting a one Post");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID:" + id));
        repository.delete(entity);
    }

    private PostDTO addHateoas(PostDTO dto) {
        dto.add(linkTo(methodOn(PostController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PostController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PostController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PostController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PostController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        return dto;
    }
}