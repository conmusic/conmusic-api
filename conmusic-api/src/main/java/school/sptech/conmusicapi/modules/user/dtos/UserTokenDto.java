package school.sptech.conmusicapi.modules.user.dtos;

public class UserTokenDto {
    private LoginUserDTO user;
    private String token;

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
