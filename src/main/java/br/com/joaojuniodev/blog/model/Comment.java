package br.com.joaojuniodev.blog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comment {

    private Long id;
    private String content;
    private User user;
    private List<Like> likes = new ArrayList<>();
    private Comment parent;

    public Comment() {}

    public Comment(Long id, String content, User user, Comment parent) {
        this.parent = parent;
        this.user = user;
        this.content = content;
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
