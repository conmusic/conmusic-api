package school.sptech.conmusicapi.modules.artist.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.gender.entities.Gender;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("artista")
public class Artist extends User {

    @ManyToMany
    @JoinTable(
            name = "usuario_genero",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Gender> musicalGenres = new ArrayList<>();

    public List<Gender> getMusicalGenres() {
        return musicalGenres;
    }

    public void addGenders(Gender gender) {
        this.musicalGenres.add(gender);
    }
}
