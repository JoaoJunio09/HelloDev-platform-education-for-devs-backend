package br.com.joaojuniodev.blog.data.dto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostDTO {

    private Long id;
    private String title;
    private String subTitle;
    private String content;
    private LocalDate date;
    private Long userId;
    private List<CommentDTO> comments = new ArrayList<>();
    private List<LikeDTO> likes = new ArrayList<>();

    public PostDTO() {}

    public PostDTO(Long id, String title, String subTitle, String content, LocalDate date, Long userId, List<CommentDTO> comments, List<LikeDTO> likes) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.date = date;
        this.userId = userId;
        this.comments = comments;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        PostDTO postDTO = (PostDTO) o;
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
