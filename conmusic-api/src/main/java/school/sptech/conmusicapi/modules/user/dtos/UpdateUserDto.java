package school.sptech.conmusicapi.modules.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class UpdateUserDto {

    @NotNull
    @Positive
    private Integer id;
    @Size(max = 45, min = 3)
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^[-A-Za-z0-9!#$%&'*+\\/=?^_`{|}~]+(?:\\.[-A-Za-z0-9!#$%&'*+\\/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?$",
            message = "Email must be in RFC2822 e-mail"
    )
    @Size(min = 5, max = 45)
    @Schema(example = "email@email.com")
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$",
            message = "Phone Number must be only digits"
    )
    @Size(max = 11)
    @Schema(example = "11909090808")
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
