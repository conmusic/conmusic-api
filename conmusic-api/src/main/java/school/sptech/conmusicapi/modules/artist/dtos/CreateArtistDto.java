package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.user.dtos.CreateUserDto;

public class CreateArtistDto extends CreateUserDto {
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
