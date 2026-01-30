package br.com.joaojuniodev.blog.data.dto.model;

import br.com.joaojuniodev.blog.model.Person;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Objects;

public class PersonDTO extends RepresentationModel<PersonDTO> {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;

    public PersonDTO() {}

    public PersonDTO(Long id, String firstName, String lastName, LocalDate birthDate, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getBirthDate(), personDTO.getBirthDate()) && Objects.equals(getPhone(), personDTO.getPhone());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getFirstName());
        result = 31 * result + Objects.hashCode(getLastName());
        result = 31 * result + Objects.hashCode(getBirthDate());
        result = 31 * result + Objects.hashCode(getPhone());
        return result;
    }
}
