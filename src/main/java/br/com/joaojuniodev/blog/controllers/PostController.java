package br.com.joaojuniodev.blog.controllers;

import br.com.joaojuniodev.blog.controllers.docs.PostControllerDocs;
import br.com.joaojuniodev.blog.data.dto.model.PostDTO;
import br.com.joaojuniodev.blog.mediatype.MediaTypes;
import br.com.joaojuniodev.blog.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/posts/v1")
public class PostController implements PostControllerDocs {

    @Autowired
    private PostService service;

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
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO PostDTO) {
        return ResponseEntity.ok().body(service.create(PostDTO));
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
    public ResponseEntity<PostDTO> update(@RequestBody PostDTO PostDTO) {
        return ResponseEntity.ok().body(service.update(PostDTO));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
