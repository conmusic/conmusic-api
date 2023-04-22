package school.sptech.conmusicapi.modules.event.mapper;

import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.entities.Event;

public class EventMapper {
    public static Event fromDto(CreateEventDto dto) {
        Event event = new Event();

        event.setCoverCharge(dto.getCoverCharge());
        event.setAbout(dto.getAbout());
        event.setInspirations(dto.getInspirations());
        event.setPaymentValue(dto.getPaymentValue());
        event.setTechnicalDetails(dto.getTechnicalDetails());

        return event;
    }

    public static EventDto toDto(Event event) {
        EventDto dto = new EventDto();

        dto.setCoverCharge(event.getCoverCharge());
        dto.setAbout(event.getAbout());
        dto.setInspirations(event.getInspirations());
        dto.setPaymentValue(event.getPaymentValue());
        dto.setTechnicalDetails(event.getTechnicalDetails());
        dto.setId(event.getId());
        dto.setSchedule(event.getSchedule().stream().map(ScheduleMapper::toDto).toList());

        return dto;
    }
}
