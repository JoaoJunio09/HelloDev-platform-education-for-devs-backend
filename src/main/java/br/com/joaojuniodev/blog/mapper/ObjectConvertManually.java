package br.com.joaojuniodev.blog.mapper;

import br.com.joaojuniodev.blog.data.dto.model.*;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.model.*;
import br.com.joaojuniodev.blog.repositories.CommentRepository;
import br.com.joaojuniodev.blog.repositories.LikeRepository;
import br.com.joaojuniodev.blog.repositories.PostRepository;
import br.com.joaojuniodev.blog.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectConvertManually {

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
            user = userRepository.findById(dto.getUserId()).orElseThrow();
        } catch (Exception e) {
            throw new NotFoundException("User not found");
        }
        var date = LocalDate.parse(dto.getDate());
        return new Post(
            dto.getId(),
            dto.getTitle(),
            dto.getSubTitle(),
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
            entity.getContent(),
            entity.getDate().toString(),
            entity.getUser().getId(),
            convertCommentListEntityToDto(entity.getComments()),
            convertLikeListEntityToDto(entity.getLikes())
        );
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
            convertUserDtoToEntity(dto.getUser()),
            post,
            convertLikeListDtoToEntity(dto.getLikes()),
            convertParentCommentDtoToEntity(dto.getParent())
        );
    }

    public CommentDTO convertCommentEntityToDto(Comment entity) {
        return new CommentDTO(
            entity.getId(),
            entity.getContent(),
            convertUserEntityToDto(entity.getUser()), entity.getPost().getId(),
            convertLikeListEntityToDto(entity.getLikes()),
            convertCommentToParentCommentDto(entity.getParent())
        );
    }

    public ParentCommentDTO convertCommentToParentCommentDto(Comment comment) {
        return new ParentCommentDTO(comment.getId(), comment.getContent());
    }

    public List<Comment> convertCommentListDtoToEntity(List<CommentDTO> dtos) {
        List<Comment> entities = new ArrayList<>();
        dtos.stream().map(dto -> convertCommentDtoToEntity(dto));
        return entities;
    }

    public List<CommentDTO> convertCommentListEntityToDto(List<Comment> entity) {
        List<CommentDTO> dtos = new ArrayList<>();
        dtos.stream().map(dto -> convertCommentDtoToEntity(dto));
        return dtos;
    }

    public Like convertLikeDtoToEntity(LikeDTO dto) {
        var post = postRepository.findById(dto.getPostId()).orElseThrow();
        var comment = commentRepository.findById(dto.getCommentId()).orElseThrow();
        return new Like(dto.getId(), convertUserDtoToEntity(dto.getUser()), post, comment);
    }
    public LikeDTO convertLikeEntityToDto(Like entity) {
        return new LikeDTO(
            entity.getId(),
            convertUserEntityToDto(entity.getUser()),
            entity.getPost().getId(),
            entity.getComment().getId());
    }

    public List<Like> convertLikeListDtoToEntity(List<LikeDTO> dtos) {
        List<Like> entities = new ArrayList<>();
        dtos.stream().map(dto -> entities.add(convertLikeDtoToEntity(dto)));
        return entities;
    }

    public List<LikeDTO> convertLikeListEntityToDto(List<Like> entities) {
        List<LikeDTO> dtos = new ArrayList<>();
        entities.stream().map(entity -> dtos.add(convertLikeEntityToDto(entity)));
        return dtos;
    }

    public Comment convertParentCommentDtoToEntity(ParentCommentDTO dto) {
       return commentRepository.findById(dto.getId()).orElseThrow();
    }
}