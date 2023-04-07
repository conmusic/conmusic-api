package school.sptech.conmusicapi.modules.house.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@DiscriminatorValue("casa")
public class House extends User {
    @Column(length = 14, unique = true)
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
