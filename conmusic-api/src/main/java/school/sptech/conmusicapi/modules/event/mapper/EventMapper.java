package school.sptech.conmusicapi.modules.event.mapper;

import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.entities.Event;

public class EventMapper {
    public static Event fromDto(CreateEventDto dto) {
        Event event = new Event();

        event.setCoverCharge(dto.getCoverCharge());
        event.setDescription(dto.getDescription());
        event.setReferences(dto.getReferences());
        event.setValue(dto.getValue());
        event.setTechnicalDetails(dto.getTechnicalDetails());

        return event;
    }

    public static EventDto toDto(Event event) {
        EventDto dto = new EventDto();

        dto.setCoverCharge(event.getCoverCharge());
        dto.setDescription(event.getDescription());
        dto.setReferences(event.getReferences());
        dto.setValue(event.getValue());
        dto.setTechnicalDetails(event.getTechnicalDetails());
        dto.setId(event.getId());
        dto.setSchedule(event.getSchedule().stream().map(ScheduleMapper::toDto).toList());

        return dto;
    }
}
