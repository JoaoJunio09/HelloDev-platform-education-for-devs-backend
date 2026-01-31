package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.controllers.PersonController;
import br.com.joaojuniodev.blog.data.dto.model.PersonDTO;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.repositories.PersonRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService implements IService<PersonDTO> {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    ObjectConvertManually mapper;

    public List<PersonDTO> findAll() {

        logger.info("Finding all People");

        return repository.findAll()
            .stream()
            .map(entity -> addHateoas(mapper.convertPersonEntityToDto(entity)))
            .toList();
    }

    @Override
    public PersonDTO findById(Long id) {

        logger.info("Finding a one Person");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID : " + id));
        return addHateoas(mapper.convertPersonEntityToDto(entity));
    }

    @Override
    public PersonDTO create(PersonDTO person) {

        logger.info("Creating a one Person");

        if (person == null) throw new ObjectIsNullException("The Object is null");

        return addHateoas(mapper.convertPersonEntityToDto(
            repository.save(mapper.convertPersonDtoToEntity(person))));
    }

    @Override
    public PersonDTO update(PersonDTO person) {

        logger.info("Updating a exists Person");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new NotFoundException("Not found this ID:" + person.getId()));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setBirthDate(person.getBirthDate());
        entity.setPhone(person.getPhone());
        return addHateoas(mapper.convertPersonEntityToDto(repository.save(entity)));
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting a one Person");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID:" + id));
        repository.delete(entity);
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