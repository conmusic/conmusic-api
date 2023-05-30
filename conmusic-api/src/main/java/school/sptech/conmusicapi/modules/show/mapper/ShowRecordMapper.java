package school.sptech.conmusicapi.modules.show.mapper;

import school.sptech.conmusicapi.modules.show.dtos.ShowRecordDto;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;

public class ShowRecordMapper {
    public static ShowRecordDto toDto(ShowRecord record) {
        ShowRecordDto dto = new ShowRecordDto();

        dto.setId(record.getId());
        dto.setValue(record.getValue());
        dto.setCoverCharge(record.getCoverCharge());
        dto.setStatus(record.getStatus());
        dto.setStartDateTime(record.getStartDateTime());
        dto.setEndDateTime(record.getEndDateTime());
        dto.setDateAction(record.getDateAction());
        dto.setUserId(record.getUser().getId());

        return dto;
    }
}
