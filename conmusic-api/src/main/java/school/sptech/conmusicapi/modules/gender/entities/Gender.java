package school.sptech.conmusicapi.modules.gender.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.artist.entities.Artist;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "usuario_genero",
            joinColumns = @JoinColumn(name = "fk_genero"),
            inverseJoinColumns = @JoinColumn(name = "fk_usuario")
    )
    private List<Artist> artists = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtistList() {
        return artists;
    }

    public void addGenderArtist(Artist artist) {
        this.artists.add(artist);
    }
}
