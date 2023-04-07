package school.sptech.conmusicapi.modules.house.dtos;

import school.sptech.conmusicapi.modules.user.dtos.UserDto;

public class HouseDto extends UserDto {
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
