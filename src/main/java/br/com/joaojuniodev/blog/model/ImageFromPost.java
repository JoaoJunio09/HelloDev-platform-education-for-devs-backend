package br.com.joaojuniodev.blog.model;

import jakarta.persistence.*;

@Entity
@Table
public class ImageFromPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileId;

    @JoinColumn(name = "post_id")
    @OneToOne
    private Post post;

    public ImageFromPost() {}

    public ImageFromPost(Long id, String fileId, Post post) {
        this.id = id;
        this.fileId = fileId;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}