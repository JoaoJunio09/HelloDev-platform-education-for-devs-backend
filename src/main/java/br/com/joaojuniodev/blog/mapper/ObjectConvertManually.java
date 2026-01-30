package br.com.joaojuniodev.blog.mapper;

import br.com.joaojuniodev.blog.data.dto.model.PersonDTO;
import br.com.joaojuniodev.blog.model.Person;

public class ObjectConvertManually {

    public static PersonDTO convertPersonEntityToDto(Person entity) {
        return new PersonDTO(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getBirthDate(),
            entity.getPhone());
    }

    public static Person convertPersonDtoToEntity(PersonDTO dto) {
        return new Person(
            dto.getId(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getBirthDate(),
            dto.getPhone());
    }
}
