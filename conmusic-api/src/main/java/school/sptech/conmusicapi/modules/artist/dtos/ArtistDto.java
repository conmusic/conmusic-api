package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.user.dtos.UserDto;

public class ArtistDto extends UserDto {
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
