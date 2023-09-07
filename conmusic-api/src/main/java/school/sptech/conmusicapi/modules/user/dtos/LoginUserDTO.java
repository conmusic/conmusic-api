package school.sptech.conmusicapi.modules.user.dtos;

import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.user.entities.User;

public class LoginUserDTO extends UserDto {
    private String userType;
    private boolean needCompletion;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean getNeedCompletion() {
        return needCompletion;
    }

    public void setNeedCompletion(User user) {
        if (user instanceof Artist artist) {
            needCompletion = doesArtistNeedCompletion(artist);
        } else if (user instanceof Manager manager) {
            needCompletion = doesManagerNeedCompletion(manager);
        } else {
            needCompletion = false;
        }
    }

    private boolean doesArtistNeedCompletion(Artist artist) {
        return !artist.getMusicalGenres().isEmpty()
                && !artist.getAddress().isEmpty()
                && !artist.getCity().isEmpty()
                && !artist.getState().isEmpty()
                && !artist.getZipCode().isEmpty();
    }

    private boolean doesManagerNeedCompletion(Manager manager) {
        return !manager.getEstablishments().isEmpty();
    }
}
