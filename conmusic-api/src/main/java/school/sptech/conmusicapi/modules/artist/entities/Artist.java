package school.sptech.conmusicapi.modules.artist.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@DiscriminatorValue("artista")
public class Artist extends User {
}
