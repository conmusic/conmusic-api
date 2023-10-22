package school.sptech.conmusicapi.modules.manager.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("gerente")
public class Manager extends User {

    @Override
    public String getUserType() {
        return "Manager";
    }

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Establishment> establishments;

    public List<Establishment> getEstablishments() {
        return Objects.isNull(establishments) ? Collections.emptyList() : establishments;
    }

    public void setEstablishments(List<Establishment> establishments) {
        this.establishments = establishments;
    }
}
