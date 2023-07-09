package school.sptech.conmusicapi.modules.show.util;

import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.time.LocalDateTime;

public class ShowUtil {
    public static ShowRecord createRecord(Show show, User user) {
        ShowRecord record = new ShowRecord();

        record.setStatus(show.getStatus());
        record.setValue(show.getValue());
        record.setCoverCharge(show.getCoverCharge());
        record.setStartDateTime(show.getSchedule().getStartDateTime());
        record.setEndDateTime(show.getSchedule().getEndDateTime());
        record.setDateAction(LocalDateTime.now());
        record.setShow(show);
        record.setUser(user);

        return record;
    }
}
