package school.sptech.conmusicapi.modules.user.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "nome", length = 45)
    private String name;

    @Column(length = 45, unique = true, nullable = false)
    private String email;

    @Column(name = "senha", length = 60, nullable = false)
    private String password;

    @Column(length = 11, unique = true)
    private String cpf;

    @Column(name = "telefone", length = 11)
    private String phoneNumber;

    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    @Column(name = "sobre", length = 45)
    private String about;

    @Column(name = "instagram", length = 45)
    private String instagram;

    @Column(name = "endereco", length = 45)
    private String address;

    @Column(name = "cidade", length = 45)
    private String city;

    @Column(name = "uf", length = 2)
    private String state;

    @Column(name = "cep", length = 8)
    private String zipCode;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public abstract String getUserType();
}