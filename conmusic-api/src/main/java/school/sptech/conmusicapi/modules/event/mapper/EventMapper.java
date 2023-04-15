package school.sptech.conmusicapi.modules.event.mapper;

import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.entities.Evento;

public class EventMapper {
    public static Evento fromDto(CreateEventDto dto) {
        Evento event = new Evento();

        event.setCoverCharge(dto.getCoverCharge());
        event.setAbout(dto.getDescription());
        event.setInspirations(dto.getReferences());
        event.setValor(dto.getValue());
        event.setTechnicalDetails(dto.getTechnicalDetails());

        return event;
    }

    public static EventDto toDto(Evento event) {
        EventDto dto = new EventDto();

        dto.setCoverCharge(event.getCoverCharge());
        dto.setDescription(event.getAbout());
        dto.setReferences(event.getInspirations());
        dto.setValue(event.getValor());
        dto.setTechnicalDetails(event.getTechnicalDetails());
        dto.setId(event.getId());
        dto.setSchedule(event.getSchedule().stream().map(ScheduleMapper::toDto).toList());

        return dto;
    }
}
