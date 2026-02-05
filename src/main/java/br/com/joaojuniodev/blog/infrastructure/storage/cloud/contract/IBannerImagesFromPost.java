package br.com.joaojuniodev.blog.infrastructure.storage.cloud.contract;

import br.com.joaojuniodev.blog.data.dto.storage.StoredFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IBannerImagesFromPost {

    StoredFileResponse uploadImage(MultipartFile image);
    Resource getImage(String fileId);
}
