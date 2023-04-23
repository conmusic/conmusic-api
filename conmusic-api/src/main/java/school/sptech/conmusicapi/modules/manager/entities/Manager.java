package school.sptech.conmusicapi.modules.manager.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@DiscriminatorValue("gerente")
public class Manager extends User {
}
