package school.sptech.conmusicapi.modules.house.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import school.sptech.conmusicapi.modules.user.dtos.UpdateUserDto;

public class UpdateHouseDto extends UpdateUserDto {

    @CNPJ
    @NotBlank
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
