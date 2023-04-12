package school.sptech.conmusicapi.modules.artist.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import school.sptech.conmusicapi.modules.user.dtos.CreateUserDto;

public class CreateArtistDto extends CreateUserDto {
    @NotBlank
    @CPF
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
