package br.com.joaojuniodev.blog.data.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class PostDTO extends RepresentationModel<PostDTO> {

    private Long id;
    private String title;
    private String subTitle;
    private String description;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String date;
    private BannerDTO banner;
    private ThumbnailDTO thumbnail;
    private String status;
    private String category;
    private UserDTO userDTO;

    public PostDTO() {}

    public PostDTO(Long id, String title, String subTitle, String description, String content, String date, BannerDTO banner, ThumbnailDTO thumbnail, String status, String category, UserDTO userDTO) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.content = content;
        this.date = date;
        this.banner = banner;
        this.thumbnail = thumbnail;
        this.status = status;
        this.category = category;
        this.userDTO = userDTO;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BannerDTO getBanner() {
        return banner;
    }

    public void setBanner(BannerDTO banner) {
        this.banner = banner;
    }

    public ThumbnailDTO getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailDTO thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
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
