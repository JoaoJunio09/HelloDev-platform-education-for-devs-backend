package br.com.joaojuniodev.blog.data.dto;

import java.util.Objects;

public class ParentCommentDTO {

    private Long id;
    private String content;

    public ParentCommentDTO() {}

    public ParentCommentDTO(Long id, String content) {
        this.id = id;
        this.content = content;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ParentCommentDTO that = (ParentCommentDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getContent());
        return result;
    }
}
