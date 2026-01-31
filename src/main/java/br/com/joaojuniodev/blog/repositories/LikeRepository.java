package br.com.joaojuniodev.blog.repositories;

import br.com.joaojuniodev.blog.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
