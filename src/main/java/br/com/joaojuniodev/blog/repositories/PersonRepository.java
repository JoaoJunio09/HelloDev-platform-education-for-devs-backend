package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}