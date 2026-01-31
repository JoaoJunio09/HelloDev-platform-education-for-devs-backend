package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.controllers.LikeController;
import br.com.joaojuniodev.blog.data.dto.model.LikeDTO;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.repositories.LikeRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class LikeService implements IService<LikeDTO> {

    private final Logger logger = LoggerFactory.getLogger(LikeService.class.getName());

    @Autowired
    LikeRepository repository;

    @Autowired
    ObjectConvertManually mapper;
    
    @Override
    public List<LikeDTO> findAll() {

        logger.info("Finding all Like's");

        return repository.findAll()
            .stream()
            .map(entity -> addHateoas(mapper.convertLikeEntityToDto(entity)))
            .toList();
    }

    @Override
    public LikeDTO findById(Long id) {

        logger.info("Finding a one Like");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID : " + id));
        return addHateoas(mapper.convertLikeEntityToDto(entity));
    }

    @Override
    public LikeDTO create(LikeDTO like) {

        logger.info("Creating a one Like");

        if (like == null) throw new ObjectIsNullException("The Object [Like] is null");

        return addHateoas(mapper.convertLikeEntityToDto(
            repository.save(mapper.convertLikeDtoToEntity(like))));
    }

    @Override
    public LikeDTO update(LikeDTO like) {
        return null;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting a one Like");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID:" + id));
        repository.delete(entity);
    }

    private LikeDTO addHateoas(LikeDTO dto) {
        dto.add(linkTo(methodOn(LikeController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(LikeController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(LikeController.class).create(dto)).withRel("create").withType("Like"));
        dto.add(linkTo(methodOn(LikeController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(LikeController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        return dto;
    }
}