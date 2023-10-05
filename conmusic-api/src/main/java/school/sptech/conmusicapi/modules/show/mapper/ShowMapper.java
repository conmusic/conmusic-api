package school.sptech.conmusicapi.modules.show.mapper;

import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.entities.Show;

public class ShowMapper {
    public static Show fromDto(CreateShowDto dto) {
        Show entity = new Show();

        entity.setValue(dto.getValue());
        entity.setCoverCharge(dto.getCoverCharge());

        return entity;
    }

    public static ShowDto toDto(Show entity) {
        ShowDto dto = new ShowDto();

        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        dto.setCoverCharge(entity.getCoverCharge());
        dto.setStatus(entity.getStatus());
        dto.setArtist(ArtistMapper.toShowArtistDto(entity.getArtist()));
        dto.setManagerName(entity.getEvent().getEstablishment().getManager().getName());
        dto.setEvent(EventMapper.toDisplayScheduleDto(entity.getEvent()));
        dto.setSchedule(ScheduleMapper.toBasicDto(entity.getSchedule()));

        return dto;
    }

    public static void fromDtoUpdate(UpdateShowDto dto, Show show) {
        show.setValue(dto.getValue());
        show.setCoverCharge(dto.getCoverCharge());
    }
}
