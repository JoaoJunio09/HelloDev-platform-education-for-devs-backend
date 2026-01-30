package br.com.joaojuniodev.blog.data.dto.model;

public class UserDTO {

    private String fullName;
    private String email;

    public UserDTO() {}

    public UserDTO(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
