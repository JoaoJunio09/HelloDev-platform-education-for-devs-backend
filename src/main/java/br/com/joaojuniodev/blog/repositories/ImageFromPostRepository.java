package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.ImageFromPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFromPostRepository extends JpaRepository<ImageFromPost, Long> {

    @Query("""
        DELETE i FROM ImageFromPost i WHERE i.postId = :postId
    """)
    void deleteByPostId(@Param("postId") Long postId);
}