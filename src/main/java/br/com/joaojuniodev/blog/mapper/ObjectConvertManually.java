package br.com.joaojuniodev.blog.mapper;

import br.com.joaojuniodev.blog.data.dto.model.*;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.model.*;
import br.com.joaojuniodev.blog.repositories.CommentRepository;
import br.com.joaojuniodev.blog.repositories.LikeRepository;
import br.com.joaojuniodev.blog.repositories.PostRepository;
import br.com.joaojuniodev.blog.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ObjectConvertManually {

    private final Logger logger = LoggerFactory.getLogger(ObjectConvertManually.class.getName());

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public ObjectConvertManually(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public PersonDTO convertPersonEntityToDto(Person entity) {
        return new PersonDTO(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getBirthDate(),
            entity.getPhone());
    }

    public Person convertPersonDtoToEntity(PersonDTO dto) {
        return new Person(
            dto.getId(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getBirthDate(),
            dto.getPhone());
    }

    public Post convertPostDtoToEntity(PostDTO dto) {
        User user = null;
        try {
            user = userRepository.findByUsername(dto.getUserDTO().getUsername());
        } catch (Exception e) {
            throw new NotFoundException("User not found");
        }
        var date = LocalDate.parse(dto.getDate());
        return new Post(
            dto.getId(),
            dto.getTitle(),
            dto.getSubTitle(),
            dto.getDescription(),
            dto.getContent(),
            date,
            user,
            convertCommentListDtoToEntity(dto.getComments()),
            convertLikeListDtoToEntity(dto.getLikes()));
    }

    public PostDTO convertPostEntityToDto(Post entity) {
        return new PostDTO(
            entity.getId(),
            entity.getTitle(),
            entity.getSubTitle(),
            entity.getDescription(),
            entity.getContent(),
            entity.getDate().toString(),
            convertUserEntityToDto(entity.getUser()),
            entity.getImagesFromPost() == null ? null : buildImageUrl(entity.getImagesFromPost().getFileId()),
            convertCommentListEntityToDto(entity.getComments()),
            convertLikeListEntityToDto(entity.getLikes())
        );
    }

    private String buildImageUrl(String fileId) {
        if (fileId != null) {
            final String BASE_URL = "http://localhost:8080";
            return BASE_URL + "/api/posts/v1/getImageFromPost/" + fileId;
        } else {
            return null;
        }
    }

    public UserDTO convertUserEntityToDto(User entity) {
        return new UserDTO(entity.getFullName(), entity.getUsername());
    }

    public User convertUserDtoToEntity(UserDTO dto) {
        return userRepository.findByUsername(dto.getUsername());
    }

    public Comment convertCommentDtoToEntity(CommentDTO dto) {
        var post = postRepository.findById(dto.getPostId()).orElseThrow();

        return new Comment(
            dto.getId(),
            dto.getContent(),
            post,
            convertUserDtoToEntity(dto.getUser()),
            dto.getParent() == null ? null : convertParentCommentDtoToEntity(dto.getParent())
        );
    }

    public CommentDTO convertCommentEntityToDto(Comment entity) {
        return new CommentDTO(
            entity.getId(),
            entity.getContent(),
            convertUserEntityToDto(entity.getUser()),
            entity.getPost().getId(),
            convertLikeListEntityToDto(entity.getLikes()),
            convertCommentToParentCommentDto(entity.getParent())
        );
    }

    public ParentCommentDTO convertCommentToParentCommentDto(Comment comment) {
        if (comment == null) return null;
        return new ParentCommentDTO(comment.getId(), comment.getContent());
    }

    public List<Comment> convertCommentListDtoToEntity(List<CommentDTO> dtos) {
        if (dtos == null) return List.of();
        return dtos.stream()
            .map(this::convertCommentDtoToEntity)
            .toList();
    }

    public List<CommentDTO> convertCommentListEntityToDto(List<Comment> entities) {
       if (entities == null) return List.of();
       return entities.stream()
           .map(this::convertCommentEntityToDto)
           .toList();
    }

    public Like convertLikeDtoToEntity(LikeDTO dto) {
        Post post = dto.getPostId() != null
            ? postRepository.findById(dto.getPostId()).orElseThrow()
            : null;
        Comment comment = dto.getCommentId() != null
            ? commentRepository.findById(dto.getCommentId()).orElseThrow()
            : null;
        return new Like(dto.getId(), convertUserDtoToEntity(dto.getUser()), post, comment);
    }
    public LikeDTO convertLikeEntityToDto(Like entity) {
        return new LikeDTO(
            entity.getId(),
            convertUserEntityToDto(entity.getUser()),
            entity.getPost() == null ? null : entity.getPost().getId(),
            entity.getComment() == null ? null :entity.getComment().getId());
    }

    public List<Like> convertLikeListDtoToEntity(List<LikeDTO> dtos) {
        if (dtos == null) return List.of();
        return dtos.stream()
            .map(this::convertLikeDtoToEntity)
            .toList();
    }

    public List<LikeDTO> convertLikeListEntityToDto(List<Like> entities) {
        if (entities == null) return List.of();
        return entities.stream()
            .map(this::convertLikeEntityToDto)
            .toList();
    }

    public Comment convertParentCommentDtoToEntity(ParentCommentDTO dto) {
       return commentRepository.findById(dto.getId()).orElseThrow();
    }

    public List<Post> convertPostListDtoToEntity(List<PostDTO> dtos) {
        return dtos.stream().map(this::convertPostDtoToEntity).toList();
    }
}