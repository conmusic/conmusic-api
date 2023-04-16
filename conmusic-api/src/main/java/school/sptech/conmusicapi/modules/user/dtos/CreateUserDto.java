package school.sptech.conmusicapi.modules.user.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public abstract class CreateUserDto {
    @NotBlank
    @Size(min = 2, max = 45)
    private String name;

    @NotBlank
    @Pattern(
        regexp = "^[-A-Za-z0-9!#$%&'*+\\/=?^_`{|}~]+(?:\\.[-A-Za-z0-9!#$%&'*+\\/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?$",
        message = "Email must be in RFC2822 e-mail"
    )
    @Size(min = 5, max = 45)
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(
        regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
        message = "Password must have at least one lowercase letter, one uppercase letter, one digit, one special character and 8 characters length"
    )
    private String password;

    @NotBlank
    @Pattern(
        regexp = "^\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$",
        message = "Phone Number must be only digits"
    )
    @Size(max = 11)
    private String phoneNumber;

    @NotNull
    @Past
    private LocalDate birthDate;

    @Size(min = 5, max = 45)
    private String about;

    @Size(max = 45)
    private String instagram;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
