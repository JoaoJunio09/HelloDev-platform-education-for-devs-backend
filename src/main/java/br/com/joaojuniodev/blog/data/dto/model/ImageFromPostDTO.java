package br.com.joaojuniodev.blog.data.dto.model;

import br.com.joaojuniodev.blog.model.Post;
import br.com.joaojuniodev.blog.model.enums.PostImageCategory;

public class ImageFromPostDTO {

    private Long id;
    private String fileId;
    private String imageUrl;
    private PostImageCategory category;
    private Post post;

    public ImageFromPostDTO() {}

    public ImageFromPostDTO(Long id, String fileId, String imageUrl, PostImageCategory category, Post post) {
        this.id = id;
        this.fileId = fileId;
        this.imageUrl = imageUrl;
        this.category = category;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PostImageCategory getCategory() {
        return category;
    }

    public void setCategory(PostImageCategory category) {
        this.category = category;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
