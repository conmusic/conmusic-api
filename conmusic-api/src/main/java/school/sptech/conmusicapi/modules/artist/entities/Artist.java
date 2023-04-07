package school.sptech.conmusicapi.modules.artist.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@DiscriminatorValue("artista")
public class Artist extends User {
    @Column(length = 11, unique = true)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
