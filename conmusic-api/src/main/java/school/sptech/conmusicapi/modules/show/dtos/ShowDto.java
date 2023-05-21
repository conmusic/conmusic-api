package school.sptech.conmusicapi.modules.show.dtos;

import school.sptech.conmusicapi.modules.artist.dtos.ShowArtistDto;
import school.sptech.conmusicapi.modules.events.dtos.DisplayScheduleEventDto;
import school.sptech.conmusicapi.modules.schedules.dtos.BasicScheduleDto;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;

public class ShowDto {
    private Integer id;
    private ShowStatusEnum status;
    private Double value;
    private Double coverCharge;
    private DisplayScheduleEventDto event;
    private ShowArtistDto artist;
    private BasicScheduleDto schedule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ShowStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ShowStatusEnum status) {
        this.status = status;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getCoverCharge() {
        return coverCharge;
    }

    public void setCoverCharge(Double coverCharge) {
        this.coverCharge = coverCharge;
    }

    public DisplayScheduleEventDto getEvent() {
        return event;
    }

    public void setEvent(DisplayScheduleEventDto event) {
        this.event = event;
    }

    public ShowArtistDto getArtist() {
        return artist;
    }

    public void setArtist(ShowArtistDto artist) {
        this.artist = artist;
    }

    public BasicScheduleDto getSchedule() {
        return schedule;
    }

    public void setSchedule(BasicScheduleDto schedule) {
        this.schedule = schedule;
    }
}
