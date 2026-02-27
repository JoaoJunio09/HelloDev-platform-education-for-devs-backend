package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.Post;
import br.com.joaojuniodev.blog.model.enums.PostCategoryEnum;
import br.com.joaojuniodev.blog.model.enums.PostStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user
        ORDER BY p.date DESC
    """)
    Page<Post> findAllWithUser(Pageable pageable);

    @Query("""      
        SELECT DISTINCT p FROM Post p
        LEFT JOIN FETCH p.comments c
        WHERE p.id = :id
    """)
    Optional<Post> findByIdWithComments(@Param("id") Long id);

    @Query("""
        SELECT p FROM Post p
        WHERE p.status = :status
    """)
    Page<Post> findByStatus(@Param("status") PostStatusEnum status, Pageable pageable);

    @Query("""
        SELECT p FROM Post p
        WHERE p.category = :category
    """)
    Page<Post> findByCategory(@Param("category") PostCategoryEnum category, Pageable pageable);

    @Query("""
        SELECT p FROM Post p
        WHERE p.status = :status AND p.category = :category
    """)
    Page<Post> findByStatusAndCategory(
        @Param("status") PostStatusEnum status,
        @Param("category") PostCategoryEnum category,
        Pageable pageable
    );

    @Query("""
        SELECT p FROM Post p
        WHERE p.title LIKE CONCAT('%', :title, '%')
    """)
    Page<Post> searchByTitle(@Param("title") String title, Pageable pageable);
}