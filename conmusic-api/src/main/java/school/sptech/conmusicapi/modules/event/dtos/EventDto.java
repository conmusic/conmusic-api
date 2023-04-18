package school.sptech.conmusicapi.modules.event.dtos;


import java.util.List;


public class EventDto {

    private Integer id;

    private String about;

    private String inspirations;

    private String technicalDetails;

    private Double paymentValue;

    private Double coverCharge;

    private List<ScheduleDto> schedule;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


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


    public List<ScheduleDto> getSchedule() {
        return schedule;
    }


    public void setSchedule(List<ScheduleDto> schedule) {
        this.schedule = schedule;
    }
}
