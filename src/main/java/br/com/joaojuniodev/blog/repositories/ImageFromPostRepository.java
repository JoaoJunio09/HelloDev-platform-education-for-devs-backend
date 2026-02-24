package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.ImageFromPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageFromPostRepository extends JpaRepository<ImageFromPost, Long> {

    @Transactional
    @Modifying
    @Query("""
        DELETE FROM ImageFromPost i
        WHERE i.post.id = :postId
        AND i.fileId = :fileId
    """)
    void deleteByPostId(@Param("postId") Long postId, @Param("fileId") String fileId);

    boolean existsByFileIdAndPost_Id(@Param("fileId") String fileId, @Param("postId") Long postId);
}