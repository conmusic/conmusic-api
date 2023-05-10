package school.sptech.conmusicapi.shared.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class TimeDto {
    @NotNull
    @Min(0)
    @Max(23)
    private Integer hour;

    @NotNull
    @Min(0)
    @Max(59)
    private Integer minute;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public LocalTime asLocalTime() {
        return LocalTime.of(hour, minute);
    }
}
