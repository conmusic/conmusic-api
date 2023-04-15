package school.sptech.conmusicapi.modules.event.dtos;


import java.util.List;


public class EventDto {

    private Integer id;

    private String description;

    private String references;

    private String technicalDetails;

    private Double value;

    private Double coverCharge;

    private List<ScheduleDto> schedule;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


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


    public List<ScheduleDto> getSchedule() {
        return schedule;
    }


    public void setSchedule(List<ScheduleDto> schedule) {
        this.schedule = schedule;
    }
}
