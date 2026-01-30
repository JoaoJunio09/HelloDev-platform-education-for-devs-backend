package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.controllers.PersonController;
import br.com.joaojuniodev.blog.data.dto.model.PersonDTO;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.repositories.PersonRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.joaojuniodev.blog.mapper.ObjectConvertManually.convertPersonDtoToEntity;
import static br.com.joaojuniodev.blog.mapper.ObjectConvertManually.convertPersonEntityToDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService implements IService<PersonDTO> {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {

        logger.info("Finding all People");

        return repository.findAll()
            .stream()
            .map(entity -> addHateoas(convertPersonEntityToDto(entity)))
            .toList();
    }

    @Override
    public PersonDTO findById(Long id) {
        return null;
    }

    @Override
    public PersonDTO create(PersonDTO person) {
        logger.info("Creating a one Person");

        if (person == null) {
            throw new ObjectIsNullException("The Object is null");
        }

        return addHateoas(convertPersonEntityToDto(
            repository.save(convertPersonDtoToEntity(person))
        ));
    }

    @Override
    public PersonDTO update(PersonDTO person) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private PersonDTO addHateoas(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        return dto;
    }
}