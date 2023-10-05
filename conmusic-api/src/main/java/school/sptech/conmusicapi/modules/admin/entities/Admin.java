package school.sptech.conmusicapi.modules.admin.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import school.sptech.conmusicapi.modules.user.entities.User;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    @Override
    public String getUserType() {
        return "Admin";
    }
}