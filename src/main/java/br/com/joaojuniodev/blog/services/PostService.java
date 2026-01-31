package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.data.dto.model.PostDTO;
import br.com.joaojuniodev.blog.mapper.ObjectConvertManually;
import br.com.joaojuniodev.blog.repositories.PostRepository;
import br.com.joaojuniodev.blog.services.contract.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IService<PostDTO> {

    private final Logger logger = LoggerFactory.getLogger(PostService.class.getName());

    @Autowired
    PostRepository repository;

    @Autowired
    ObjectConvertManually mapper;

    @Override
    public List<PostDTO> findAll() {
        logger.info("Finding all Post's");

        return repository.findAll()
            .stream()
            .map(entity -> addHateoas(mapper.convertPersonEntityToDto(entity)))
            .toList();
    }

    @Override
    public PostDTO findById(Long id) {
        return null;
    }

    @Override
    public PostDTO create(PostDTO object) {
        return null;
    }

    @Override
    public PostDTO update(PostDTO object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
