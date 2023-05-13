package school.sptech.conmusicapi.modules.artist.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
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
    private List<Genre> musicalGenres = new ArrayList<>();

    public List<Genre> getMusicalGenres() {
        return musicalGenres;
    }

    public void addGenders(Genre genre) {
        this.musicalGenres.add(genre);
    }
}
