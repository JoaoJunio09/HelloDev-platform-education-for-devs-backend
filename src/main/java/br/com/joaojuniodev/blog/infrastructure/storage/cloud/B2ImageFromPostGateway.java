package br.com.joaojuniodev.blog.infrastructure.storage.cloud;

import br.com.joaojuniodev.blog.config.B2Properties;
import br.com.joaojuniodev.blog.data.dto.storage.StoredFileResponse;
import br.com.joaojuniodev.blog.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.blog.exceptions.storage.ErrorReadingFilenameException;
import br.com.joaojuniodev.blog.exceptions.storage.ErrorUploadingToB2Exception;
import br.com.joaojuniodev.blog.exceptions.storage.ItWasNotPossibleToObtainImageInB2Exception;
import br.com.joaojuniodev.blog.infrastructure.storage.cloud.contract.IImageFromPost;
import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentHandlers.B2ContentSink;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class B2ImageFromPostGateway implements IImageFromPost {

    private final Logger logger = LoggerFactory.getLogger(B2ImageFromPostGateway.class.getName());

    private B2StorageClient client;
    private B2Properties props;

    @Override
    public StoredFileResponse uploadImage(MultipartFile image) {
        try {
            final B2Bucket bucket = getBucket();
            final String bucketId = bucket.getBucketId();
            final B2ByteArrayContentSource source = (B2ByteArrayContentSource) B2ByteArrayContentSource.build(image.getBytes());

            final B2UploadFileRequest request = B2UploadFileRequest
                .builder(bucketId, image.getOriginalFilename(), B2ContentTypes.B2_AUTO, source)
                .build();

            logger.info("Uploading an image to B2");

            var fileVersion = client.uploadSmallFile(request);

            return new StoredFileResponse(
                fileVersion.getFileName(),
                fileVersion.getContentType(),
                fileVersion.getContentLength(),
                fileVersion.getFileId()
            );
        }
        catch (Exception e) {
            throw new ErrorUploadingToB2Exception("The image could not be uploaded.");
        }
    }

    @Override
    public Resource getImage(String fileId) {
        try {
            B2FileVersion fileVersion = client.getFileInfo(fileId);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            B2ContentSink sink = (headers, inputStream) -> inputStream.transferTo(outputStream);
            client.downloadById(fileId, sink);

            byte[] data = outputStream.toByteArray();

            logger.info("Getting image from post in B2");

            return new ByteArrayResource(data);
        }
        catch (Exception e) {
            throw new ItWasNotPossibleToObtainImageInB2Exception("An error occurred while trying to retrieve the image from b2.");
        }
    }

    public InputStream getProfileImageInputStream(String fileId) throws IOException {
        var resource = getImage(fileId);
        return resource.getInputStream();
    }

    private B2Bucket getBucket() throws Exception {
        var bucket = client.getBucketOrNullByName(props.getBucketName());
        if (bucket == null) throw new ObjectIsNullException("The Bucket is not exists or null!");
        return bucket;
    }

    public String getFileName(String fileId) {
        try {
            B2FileVersion fileVersion = client.getFileInfo(fileId);
            return fileVersion.getFileName();
        }
        catch (Exception e) {
            logger.error("Error reading filename in B2 by fileId {}", fileId, e);
            throw new ErrorReadingFilenameException("Error reading filename in B2 by fileId");
        }
    }
}