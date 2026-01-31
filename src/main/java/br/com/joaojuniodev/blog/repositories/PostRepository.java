package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""      
        SELECT DISTINCT p FROM Post p
        LEFT JOIN FETCH p.comments c
        WHERE p.id = :id
    """)
    Optional<Post> findByIdWithComments(@Param("id") Long id);
}