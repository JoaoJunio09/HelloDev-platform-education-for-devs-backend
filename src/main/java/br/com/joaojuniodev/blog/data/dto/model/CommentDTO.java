package br.com.joaojuniodev.blog.data.dto.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CommentDTO extends RepresentationModel<CommentDTO> {

    private Long id;
    private String content;
    private UserDTO user;
    private Long postId;
    private List<LikeDTO> likes;
    private ParentCommentDTO parent;

    public CommentDTO() {}

    public CommentDTO(Long id, String content, UserDTO user, Long postId, List<LikeDTO> likes, ParentCommentDTO parent) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.postId = postId;
        this.likes = likes;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    public ParentCommentDTO getParent() {
        return parent;
    }

    public void setParent(ParentCommentDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CommentDTO that = (CommentDTO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
