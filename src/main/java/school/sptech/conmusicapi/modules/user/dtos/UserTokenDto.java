package school.sptech.conmusicapi.modules.user.dtos;

public class UserTokenDto {
    private Integer id;
    private String name;
    private String email;
    private String token;
    private LoginUserDTO user;

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

    public LoginUserDTO getUser() {
        return user;
    }

    public void setUser(LoginUserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
