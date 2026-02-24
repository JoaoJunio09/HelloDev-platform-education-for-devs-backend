package br.com.joaojuniodev.blog.controllers;

import br.com.joaojuniodev.blog.controllers.docs.PostControllerDocs;
import br.com.joaojuniodev.blog.data.dto.model.PostDTO;
import br.com.joaojuniodev.blog.data.dto.storage.StoredFileResponse;
import br.com.joaojuniodev.blog.infrastructure.storage.cloud.B2ImageFromPostGateway;
import br.com.joaojuniodev.blog.mediatype.MediaTypes;
import br.com.joaojuniodev.blog.model.enums.PostCategoryEnum;
import br.com.joaojuniodev.blog.model.enums.PostImageCategoryEnum;
import br.com.joaojuniodev.blog.model.enums.PostStatusEnum;
import br.com.joaojuniodev.blog.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.List;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/posts/v1")
public class PostController implements PostControllerDocs {

    @Autowired
    private PostService service;

    @Autowired
    private B2ImageFromPostGateway cloudB2Gateway;

    @GetMapping(
        value = "/pageable",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PagedModel<EntityModel<PostDTO>>> findAllPageable(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "0") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok().body(service.findAllPageable(pageable));
    }

    @GetMapping(
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<List<PostDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/by-status",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PagedModel<EntityModel<PostDTO>>> findAllByStatus(
        @RequestParam(value = "status") PostStatusEnum status,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "0") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok().body(service.findAllByStatus(status, pageable));
    }

    @GetMapping(
        value = "/by-category",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PagedModel<EntityModel<PostDTO>>> findAllByCategory(
        @RequestParam(value = "category") PostCategoryEnum category,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "0") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok().body(service.findAllByCategory(category, pageable));
    }

    @GetMapping(
        value = "/by-status-and-category",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PagedModel<EntityModel<PostDTO>>> findAllByStatusAndCategory(
        @RequestParam(value = "status") PostStatusEnum status,
        @RequestParam(value = "category") PostCategoryEnum category,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "0") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok().body(service.findAllByStatusAndCategory(status, category, pageable));
    }

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PostDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping(
        consumes = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML },
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok().body(service.create(postDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(
        value = "/uploadImageFromPost/{postId}",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<StoredFileResponse> uploadImageFromPost(
        @RequestParam("image") MultipartFile image,
        @PathVariable("postId") Long postId,
        @RequestParam("category") PostImageCategoryEnum category
    ) {
        StoredFileResponse fileResponse = null;
        if (image != null || !image.isEmpty()) fileResponse = service.uploadImageFromPost(image, category, postId);
        return ResponseEntity.ok().body(fileResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(
        value = "/updateImageFromPost/{fileIdRemove}/{postId}",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    public ResponseEntity<StoredFileResponse> updateImageFromPost(
        @RequestParam("image") MultipartFile image,
        @PathVariable("fileIdRemove") String fileIdRemove,
        @PathVariable("postId") Long postId,
        @RequestParam("category") PostImageCategoryEnum category
    ) {
        StoredFileResponse fileResponse = null;
        if (image != null || !image.isEmpty()) fileResponse = service.updateImageFromPost(image, category, fileIdRemove, postId);
        return ResponseEntity.ok().body(fileResponse);
    }

    @GetMapping(
        value = "/getImageFromPost/{fileId}",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<Resource> getImageFromPost(@PathVariable("fileId") String fileId) {
        Resource image = service.getImageFromPost(fileId);

        var fileName = cloudB2Gateway.getFileName(fileId);

        String contentType = URLConnection.guessContentTypeFromName(fileName);
        contentType = contentType == null ? "application/octet-stream" : contentType;

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\""
            )
            .body(image);
    }

    @PutMapping(
        consumes = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML},
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<PostDTO> update(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok().body(service.update(postDTO));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
