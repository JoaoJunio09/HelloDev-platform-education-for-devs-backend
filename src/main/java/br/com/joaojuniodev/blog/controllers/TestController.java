package br.com.joaojuniodev.blog.controllers;

import br.com.joaojuniodev.blog.data.dto.*;
import br.com.joaojuniodev.blog.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog/test")
public class TestController {

    String firstName = "Lucas";
    String lastName = "Almeida";

    @GetMapping
    public PostDTO test() {

        User user1 = new User(1L, "email123@gmail.com", "123456", firstName + " " + lastName, true, true, true, true, false);
        Person person = new Person(1L, firstName, lastName, LocalDate.now(), "123", user1);

        Post post1 = new Post(1L, "Title", "Sub Title", "Content", LocalDate.now());
        post1.setUser(user1);

        for (int i = 1; i < 6; i++) {
            User user2 = new User((long) i + 2, "email123UserNovo@gmail.com", "123456", "Maria Fernanda " + i, true, true, true, true, false);
            Comment comment = new Comment((long) i, "Content " + i, user2, null);
            post1.addComment(comment);
        }

        return convertPostEntityToDto(post1);
    }

    private PostDTO convertPostEntityToDto(Post entity) {
        return new PostDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getSubTitle(),
            entity.getContent(),
            entity.getDate(),
            convertUserEntityToDto(entity.getUser()),
            convertCommentListEntityToDto(entity.getComments()),
            convertLikeListEntityToDto(entity.getLikes()));
    }

    private UserDTO convertUserEntityToDto(User entity) {
        return new UserDTO(entity.getfullName(), entity.getEmail());
    }

    private List<CommentDTO> convertCommentListEntityToDto(List<Comment> entities) {
        var list = new ArrayList<CommentDTO>();
        for (Comment entity : entities) {
            list.add(new CommentDTO(
                entity.getId(),
                entity.getContent(),
                convertUserEntityToDto(entity.getUser()),
                convertLikeListEntityToDto(entity.getLikes()),
                convertCommentEntityToParentCommentDto(entity.getParent())));
        }
        return list;
    }

    private List<LikeDTO> convertLikeListEntityToDto(List<Like> entities) {
        var list = new ArrayList<LikeDTO>();
        for (Like entity : entities) {
            list.add(new LikeDTO(
                entity.getId(),
                convertUserEntityToDto(entity.getUser()),
                entity.getTargetType(),
                entity.getTargetId()
            ));
        }
        return list;
    }

    private ParentCommentDTO convertCommentEntityToParentCommentDto(Comment entity) {
        if (entity == null) return null;
        return new ParentCommentDTO(entity.getId(), entity.getContent());
    }
}
