package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.ImageFromPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFromPostRepository extends JpaRepository<ImageFromPost, Long> {


}