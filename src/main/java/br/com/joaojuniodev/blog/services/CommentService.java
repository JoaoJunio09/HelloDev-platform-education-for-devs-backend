package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.controllers.CommentController;
import br.com.joaojuniodev.blog.data.dto.model.CommentDTO;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.repositories.CommentRepository;
import br.com.joaojuniodev.blog.repositories.PostRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class CommentService implements IService<CommentDTO> {

    private final Logger logger = LoggerFactory.getLogger(CommentService.class.getName());

    @Autowired
    CommentRepository repository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectConvertManually mapper;

    @Override
    public List<CommentDTO> findAll() {

        logger.info("Finding all Comment's");

        return repository.findAll()
            .stream()
            .map(entity -> addHateoas(mapper.convertCommentEntityToDto(entity)))
            .toList();
    }

    @Override
    public CommentDTO findById(Long id) {

        logger.info("Finding a one Comment");

        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found this ID : " + id));
        return addHateoas(mapper.convertCommentEntityToDto(entity));
    }

    @Override
    public CommentDTO create(CommentDTO comment) {

        logger.info("Creating a one Comment");

        if (comment == null) throw new ObjectIsNullException("The Object [Comment] is null");

        var entity = mapper.convertCommentDtoToEntity(comment);
        var post = postRepository.findById(entity.getPost().getId())
            .orElseThrow(() -> new NotFoundException("Not found this ID:" + entity.getPost().getId()));
        entity.setPost(post);
        var dto = mapper.convertCommentEntityToDto(repository.save(entity));

        return addHateoas(dto);
    }

    @Override
    public CommentDTO update(CommentDTO comment) {

        logger.info("Updating a exists Comment");

        var entity = repository.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("Not found this ID:" + comment.getId()));
        entity.setContent(comment.getContent());
        return addHateoas(mapper.convertCommentEntityToDto(repository.save(entity)));
    }

    @Override
    public void delete(Long id) {

        logger.info("Deleting a one Comment");

        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found this ID:" + id));
        repository.delete(entity);
    }

    private CommentDTO addHateoas(CommentDTO dto) {
        dto.add(linkTo(methodOn(CommentController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(CommentController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(CommentController.class).create(dto)).withRel("create").withType("Comment"));
        dto.add(linkTo(methodOn(CommentController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(CommentController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        return dto;
    }
}