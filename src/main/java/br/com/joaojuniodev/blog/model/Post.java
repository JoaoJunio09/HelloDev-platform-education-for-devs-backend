package br.com.joaojuniodev.blog.model;

import br.com.joaojuniodev.blog.model.enums.PostCategoryEnum;
import br.com.joaojuniodev.blog.model.enums.PostStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "sub_title", nullable = false)
    private String subTitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private PostStatusEnum status;

    @Column
    private PostCategoryEnum category;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post")
    private List<ImageFromPost> imagesFromPosts;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();
    
    public Post() {}

    public Post(Long id, String title, String subTitle, String content, LocalDate date) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.date = date;
    }

    public Post(Long id, String title, String subTitle, String description, String content, LocalDate date, PostStatusEnum status, PostCategoryEnum category, User user) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.content = content;
        this.date = date;
        this.status = status;
        this.category = category;
        this.user = user;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PostStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PostStatusEnum status) {
        this.status = status;
    }

    public PostCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(PostCategoryEnum category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ImageFromPost> getImagesFromPosts() {
        return imagesFromPosts;
    }

    public void setImagesFromPosts(List<ImageFromPost> imagesFromPosts) {
        this.imagesFromPosts = imagesFromPosts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
