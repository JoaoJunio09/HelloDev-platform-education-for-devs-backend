package br.com.joaojuniodev.blog.data.dto.storage;

public class StoredFileResponse {

    private String fileName;
    private String contentType;
    private Long size;
    private String fileId;

    public StoredFileResponse() {}

    public StoredFileResponse(String fileName, String contentType, Long size, String fileId) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
