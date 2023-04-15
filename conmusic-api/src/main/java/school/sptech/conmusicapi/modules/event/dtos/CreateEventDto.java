package school.sptech.conmusicapi.modules.event.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateEventDto{

    @NotNull
    private String description;

    @NotNull
    private String references;

    @NotNull
    private String technicalDetails;

    @Min(value = 300, message = "O valor mínimo para o show é de 300")
    @NotNull(message = "O valor ou a taxa de couvert deve ser fornecido")
    private Double value;

    @Max(value = 100, message = "A taxa de couvert deve ser no máximo 100")
    @NotNull(message = "O valor ou a taxa de couvert deve ser fornecido")
    private Double coverCharge;

    private List<CreateScheduleDto> schedule;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getTechnicalDetails() {
        return technicalDetails;
    }

    public void setTechnicalDetails(String technicalDetails) {
        this.technicalDetails = technicalDetails;
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

    public List<CreateScheduleDto> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<CreateScheduleDto> schedule) {
        this.schedule = schedule;
    }
}
