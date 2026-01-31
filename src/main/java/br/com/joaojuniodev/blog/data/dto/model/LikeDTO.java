package br.com.joaojuniodev.blog.data.dto.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class LikeDTO extends RepresentationModel<LikeDTO> {

    private Long id;
    private UserDTO user;
    private Long postId;
    private Long commentId;

    public LikeDTO() {}

    public LikeDTO(Long id, UserDTO user, Long postId, Long commentId) {
        this.id = id;
        this.user = user;
        this.postId = postId;
        this.commentId = commentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        LikeDTO likeDTO = (LikeDTO) o;
        return Objects.equals(getId(), likeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
