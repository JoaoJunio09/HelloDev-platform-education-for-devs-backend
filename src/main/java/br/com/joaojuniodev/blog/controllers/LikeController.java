package br.com.joaojuniodev.blog.controllers;

import br.com.joaojuniodev.blog.controllers.docs.LikeControllerDocs;
import br.com.joaojuniodev.blog.data.dto.model.LikeDTO;
import br.com.joaojuniodev.blog.mediatype.MediaTypes;
import br.com.joaojuniodev.blog.services.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Like")
@RestController
@RequestMapping("/api/like/v1")
public class LikeController implements LikeControllerDocs {

    @Autowired
    private LikeService service;

    @GetMapping(
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<List<LikeDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaTypes.APPLICATION_JSON,
            MediaTypes.APPLICATION_XML,
            MediaTypes.APPLICATION_YAML })
    @Override
    public ResponseEntity<LikeDTO> findById(@PathVariable Long id) {
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
    public ResponseEntity<LikeDTO> create(@RequestBody LikeDTO like) {
        return ResponseEntity.ok().body(service.create(like));
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
    public ResponseEntity<LikeDTO> update(@RequestBody LikeDTO like) {
        return ResponseEntity.ok().body(service.update(like));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
