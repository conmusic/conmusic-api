package school.sptech.conmusicapi.modules.genre.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.artist.entities.Artist;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer id;

    private String name;

    @ManyToMany( mappedBy = "musicalGenres")
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
