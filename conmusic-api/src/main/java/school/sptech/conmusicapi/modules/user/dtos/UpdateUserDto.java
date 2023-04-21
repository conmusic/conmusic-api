package school.sptech.conmusicapi.modules.user.dtos;

import jakarta.validation.constraints.*;

public class UpdateUserDto {

    @NotNull
    private Integer id;
    @Size(max = 45, min = 3)
    private String name;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})", message = "Indique um telefone válido")
    private String phoneNumber;

    @Size(max = 45)
    private String about;
    @Size(max = 45)
    private String instagram;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
