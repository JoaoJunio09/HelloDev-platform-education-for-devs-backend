package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.controllers.PostController;
import br.com.joaojuniodev.blog.data.dto.model.PersonDTO;
import br.com.joaojuniodev.blog.data.dto.model.PostDTO;
import br.com.joaojuniodev.blog.data.dto.storage.StoredFileResponse;
import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.exceptions.storage.ErrorGettingFromB2Exception;
import br.com.joaojuniodev.blog.exceptions.storage.ErrorUploadingToB2Exception;
import br.com.joaojuniodev.blog.exceptions.storage.FileInvalidFormatException;
import br.com.joaojuniodev.blog.exceptions.storage.InvalidFileIdException;
import br.com.joaojuniodev.blog.infrastructure.storage.cloud.B2ImageFromPostGateway;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.model.ImageFromPost;
import br.com.joaojuniodev.blog.model.Post;
import br.com.joaojuniodev.blog.model.enums.PostImageCategoryEnum;
import br.com.joaojuniodev.blog.repositories.ImageFromPostRepository;
import br.com.joaojuniodev.blog.repositories.PostRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class PostService implements IService<PostDTO> {

    private final Logger logger = LoggerFactory.getLogger(PostService.class.getName());

    @Autowired
    ObjectConvertManually mapper;

    @Autowired
    PostRepository repository;

    @Autowired
    ImageFromPostRepository imageFromPostRepository;

    @Autowired
    private B2ImageFromPostGateway b2ImageFromPostGateway;

    @Autowired
    PagedResourcesAssembler<PostDTO> assembler;

    @Transactional
    @Override
    public PagedModel<EntityModel<PostDTO>> findAll(Pageable pageable) {

        logger.info("Finding all Post's");

        var posts = repository.findAllWithUser();
        return buildPagedModel(pageable, posts);
    }

    private PagedModel<EntityModel<PostDTO>> buildPagedModel(Pageable pageable, List<Post> posts) {
        var postsWithLinks = posts.stream()
            .map(entity -> addHateoas(mapper.convertPostEntityToDto(entity)))
            .toList();

        Link findAllLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PostController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel((Page<PostDTO>) postsWithLinks, findAllLink);
    }

    @Override
    public PostDTO findById(Long id) {

        logger.info("Finding a one Post");

        var entity = repository.findByIdWithComments(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID : " + id));
        return addHateoas(mapper.convertPostEntityToDto(entity));
    }

    @Override
    public PostDTO create(PostDTO post) {

        logger.info("Creating a one Post");

        if (post == null) throw new ObjectIsNullException("The Object [Post] is null");
        if (post.getUserDTO() == null) throw new ObjectIsNullException("The Object [UserDTO] is null");
        if (post.getDate() == null) post.setDate(LocalDate.now().toString());

        var entity = repository.save(mapper.convertPostDtoToEntity(post));
        return addHateoas(mapper.convertPostEntityToDto(entity));
    }

    public StoredFileResponse uploadImageFromPost(MultipartFile image, PostImageCategoryEnum category, Long postId) {

        logger.info("Uploading Image");

        if (image == null) throw new ErrorUploadingToB2Exception("The upload could not be completed because the image is null.");
        if (!validityTypeOfContent(image)) throw new FileInvalidFormatException("No other file formats are accepted besides -> jpeg and png");

        StoredFileResponse response = b2ImageFromPostGateway.uploadImage(image);

        var post = repository.findById(postId)
            .orElseThrow(() -> new NotFoundException("Not found this ID : " + postId));
        logger.info("Saving image metadata in the database");
        imageFromPostRepository.save(
            new ImageFromPost(null, response.getFileId(), buildImageUrl(response.getFileId()), category, post)
        );
        return response;
    }

    private String buildImageUrl(String fileId) {
        if (fileId != null) {
            final String BASE_URL = "http://localhost:8080";
            return BASE_URL + "/api/posts/v1/getImageFromPost/" + fileId;
        } else {
            return null;
        }
    }

    public Resource getImageFromPost(String fileId) {

        logger.info("Getting Image from Post by fileId");

        if (validityFileId(fileId)) throw new InvalidFileIdException("The fileid is Empty or Blank.");
        Resource image = b2ImageFromPostGateway.getImage(fileId);
        if (image == null || !image.exists()) throw new ErrorGettingFromB2Exception("The returned image is null");
        return image;
    }

    @Override
    public PostDTO update(PostDTO post) {

        logger.info("Updating a exists Post");

        var entity = repository.findById(post.getId())
            .orElseThrow(() -> new NotFoundException("Not found this ID:" + post.getId()));
        entity.setTitle(post.getTitle());
        entity.setSubTitle(post.getSubTitle());
        entity.setContent(post.getContent());
        return addHateoas(mapper.convertPostEntityToDto(repository.save(entity)));
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting a one Post");

        var entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID:" + id));
        repository.delete(entity);
    }

    private static boolean validityFileId(String fileId) {
        return StringUtils.isBlank(fileId) || StringUtils.isEmpty(fileId);
    }

    public boolean validityTypeOfContent(MultipartFile image) {
        return image.getContentType().equalsIgnoreCase("image/jpeg") ||
            image.getContentType().equalsIgnoreCase("image/png");
    }

    private PostDTO addHateoas(PostDTO dto) {
        dto.add(linkTo(methodOn(PostController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PostController.class).findAll(0, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PostController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PostController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PostController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        return dto;
    }
}