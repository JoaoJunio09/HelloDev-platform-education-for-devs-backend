package br.com.joaojuniodev.blog.data.dto.model;

public class UserDTO {

    private String fullName;
    private String username;

    public UserDTO() {}

    public UserDTO(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
