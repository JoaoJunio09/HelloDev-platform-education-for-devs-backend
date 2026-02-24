package br.com.joaojuniodev.blog.data.dto.model;

public class BannerDTO {

    private String url;
    private String fileId;

    public BannerDTO() {}

    public BannerDTO(String url, String fileId) {
        this.url = url;
        this.fileId = fileId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
