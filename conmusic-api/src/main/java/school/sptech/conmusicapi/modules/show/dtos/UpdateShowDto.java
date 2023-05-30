package school.sptech.conmusicapi.modules.show.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateShowDto {
    @NotNull
    @Positive
    @Min(200)
    private Double value;

    @NotNull
    @Positive
    @Max(100)
    private Double coverCharge;

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
}
