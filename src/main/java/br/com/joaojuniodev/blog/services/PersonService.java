package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.data.dto.model.PersonDTO;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.model.Person;
import br.com.joaojuniodev.blog.repositories.PersonRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IService<Person, PersonDTO> {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {

        logger.info("Finding all People");

        return repository.findAll()
            .stream()
            .map(ObjectConvertManually::convertToEntityToDto)
            .toList();
    }

    @Override
    public PersonDTO findById(Long id) {
        return null;
    }

    @Override
    public PersonDTO create(Person object) {
        return null;
    }

    @Override
    public PersonDTO update(Person object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}