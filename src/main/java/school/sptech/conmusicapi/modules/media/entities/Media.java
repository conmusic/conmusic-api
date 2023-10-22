package school.sptech.conmusicapi.modules.media.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@Table(name = "Midia")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo", length = 45)
    private String type;

    private String url;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Establishment establishment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }
}
