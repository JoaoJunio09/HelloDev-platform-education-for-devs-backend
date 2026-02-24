package br.com.joaojuniodev.blog.model;

import br.com.joaojuniodev.blog.model.enums.PostImageCategoryEnum;
import jakarta.persistence.*;

@Entity
@Table
public class ImageFromPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileId;

    @Column
    private String imageUrl;

    @Column
    private PostImageCategoryEnum category;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public ImageFromPost() {}

    public ImageFromPost(Long id, String fileId, String imageUrl, PostImageCategoryEnum category, Post post) {
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

    public PostImageCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(PostImageCategoryEnum category) {
        this.category = category;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}