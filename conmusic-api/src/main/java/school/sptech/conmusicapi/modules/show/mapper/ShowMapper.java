package school.sptech.conmusicapi.modules.show.mapper;

import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;

public class ShowMapper {
    public static Show fromDto(CreateShowDto dto) {
        Show entity = new Show();

        entity.setValue(dto.getValue());
        entity.setCoverCharge(dto.getCoverCharge());
        switch (dto.getSenderType()) {
            case "artist": entity.setStatus(ShowStatusEnum.ARTIST_PROPOSAL); break;
            case "manager": entity.setStatus(ShowStatusEnum.MANAGER_PROPOSAL); break;
            default: entity.setStatus(ShowStatusEnum.UNDEFINED); break;
        }

        return entity;
    }

    public static ShowDto toDto(Show entity) {
        ShowDto dto = new ShowDto();

        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        dto.setCoverCharge(entity.getCoverCharge());
        dto.setStatus(entity.getStatus());
        dto.setArtist(ArtistMapper.toShowArtistDto(entity.getArtist()));
        dto.setEvent(EventMapper.toDisplayScheduleDto(entity.getEvent()));
        dto.setSchedule(ScheduleMapper.toBasicDto(entity.getSchedule()));

        return dto;
    }
}
