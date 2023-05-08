package school.sptech.conmusicapi.modules.artist.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.gender.entities.Gender;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("artista")
public class Artist extends User {

    @ManyToMany( mappedBy = "artists")
    private List<Gender> genders = new ArrayList<>();

    public List<Gender> getGenders() {
        return genders;
    }

    public void addGenders(Gender gender) {
        this.genders.add(gender);
    }
}
