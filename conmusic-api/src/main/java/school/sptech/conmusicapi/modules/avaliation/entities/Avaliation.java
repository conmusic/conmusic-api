package school.sptech.conmusicapi.modules.avaliation.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@Table(name = "avaliacao")
public class Avaliation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nota")
    private Integer rating;

    @Column(name = "comentario", length = 45)
    private String comentary;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Establishment establishment;

    @ManyToOne
    @JoinColumn(name = "fk_show")
    private Show show;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComentary() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
