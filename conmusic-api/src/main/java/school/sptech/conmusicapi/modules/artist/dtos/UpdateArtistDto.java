package school.sptech.conmusicapi.modules.artist.dtos;

import org.hibernate.validator.constraints.br.CPF;
import school.sptech.conmusicapi.modules.user.dtos.UpdateUserDto;

public class UpdateArtistDto extends UpdateUserDto {

    @CPF
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
