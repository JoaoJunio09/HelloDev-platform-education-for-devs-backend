package br.com.joaojuniodev.blog.model;

import br.com.joaojuniodev.blog.model.enums.LikeTargetType;

import java.util.Objects;

public class Like {

    private Long id;
    private User user;
    private LikeTargetType targetType;
    private Long targetId;

    public Like() {}

    public Like(Long id, User user, LikeTargetType targetType, Long targetId) {
        this.id = id;
        this.user = user;
        this.targetType = targetType;
        this.targetId = targetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LikeTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(LikeTargetType targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;
        return Objects.equals(getId(), like.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
