package school.sptech.conmusicapi.modules.artist.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;
import school.sptech.conmusicapi.modules.user.dtos.UpdateUserDto;

public class UpdateArtistDto extends UpdateUserDto {

    @CPF
    @NotBlank
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
