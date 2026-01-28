package br.com.joaojuniodev.blog.data.dto;

import br.com.joaojuniodev.blog.model.User;
import br.com.joaojuniodev.blog.model.enums.LikeTargetType;

import java.util.Objects;

public class LikeDTO {

    private Long id;
    private UserDTO user;
    private LikeTargetType targetType;
    private Long targetId;

    public LikeDTO() {}

    public LikeDTO(Long id, UserDTO user, LikeTargetType targetType, Long targetId) {
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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

        LikeDTO likeDTO = (LikeDTO) o;
        return Objects.equals(getId(), likeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
