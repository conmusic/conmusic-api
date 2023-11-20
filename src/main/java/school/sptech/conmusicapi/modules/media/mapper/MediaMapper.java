package school.sptech.conmusicapi.modules.media.mapper;

import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.media.dtos.MediaArtistDto;
import school.sptech.conmusicapi.modules.media.dtos.MediaEstablishmentDto;
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

    public static MediaArtistDto mapToDto(Media media) {
        return new MediaArtistDto(media.getId(), media.getUser().getId());
    }

    public static MediaEstablishmentDto mapToDtoEstablishment(Media media) {
        return new MediaEstablishmentDto(media.getId(), media.getEstablishment().getId());
    }
}
