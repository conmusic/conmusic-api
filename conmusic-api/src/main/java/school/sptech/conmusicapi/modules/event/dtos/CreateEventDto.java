package school.sptech.conmusicapi.modules.event.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreateEventDto{
    @NotBlank
    private String about;

    @NotBlank
    private String inspirations;

    @NotBlank
    private String technicalDetails;

    @Min(value = 300, message = "O valor mínimo para o show é de 300")
    private Double paymentValue;

    @Max(value = 100, message = "A taxa de couvert deve ser no máximo 100")
    private Double coverCharge;

    @Valid
    private List<CreateScheduleDto> schedule;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getInspirations() {
        return inspirations;
    }

    public void setInspirations(String inspirations) {
        this.inspirations = inspirations;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(String technicalDetails) {
        this.technicalDetails = technicalDetails;
    }

    public Double getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Double paymentValue) {
        this.paymentValue = paymentValue;
    }

    public Double getCoverCharge() {
        return coverCharge;
    }

    public void setCoverCharge(Double coverCharge) {
        this.coverCharge = coverCharge;
    }

    public List<CreateScheduleDto> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<CreateScheduleDto> schedule) {
        this.schedule = schedule;
    }
}
