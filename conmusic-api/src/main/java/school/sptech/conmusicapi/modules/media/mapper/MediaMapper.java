package school.sptech.conmusicapi.modules.media.mapper;

import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.media.entities.Media;
import school.sptech.conmusicapi.modules.user.entities.User;

public class MediaMapper {

    public static Media mapArtist(User user, String type, String url) {
        Media media = new Media();
        media.setType(type);
        media.setUrl(url);
        media.setUser(user);
        return media;
    }

    public static Media mapEstablishment(Establishment establishment, String type, String url) {
        Media media = new Media();
        media.setType(type);
        media.setUrl(url);
        media.setEstablishment(establishment);
        return media;
    }
}
