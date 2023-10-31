package school.sptech.conmusicapi.modules.show.dtos;

import jakarta.validation.constraints.*;

public class CreateShowDto {
    @Positive
    @Min(200)
    private Double value;

    @Positive
    @Max(100)
    private Double coverCharge;

    @NotNull
    @Positive
    private Integer eventId;

    @NotNull
    @Positive
    private Integer artistId;

    @NotNull
    @Positive
    private Integer scheduleId;

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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }
}
