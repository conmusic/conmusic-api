package school.sptech.conmusicapi.modules.show.mapper;

import school.sptech.conmusicapi.modules.show.dtos.ShowRecordDto;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.util.LifeCycleEnum;
import school.sptech.conmusicapi.modules.show.util.RecordTypeEnum;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.time.LocalDateTime;

public class ShowRecordMapper {
    public static ShowRecord createRecord(Show show, User user, RecordTypeEnum recordType) {
        ShowRecord record = new ShowRecord();

        record.setShow(show);
        record.setValue(show.getValue());
        record.setCoverCharge(show.getCoverCharge());
        record.setStatus(show.getStatus());
        record.setLifeCycle(LifeCycleEnum.getLifeCycleBasedOnShowStatus(show.getStatus()));
        record.setRecordType(recordType);
        record.setUser(user);
        record.setDateAction(LocalDateTime.now());
        record.setStartDateTime(show.getSchedule().getStartDateTime());
        record.setEndDateTime(show.getSchedule().getEndDateTime());

        return record;
    }

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
