package school.sptech.conmusicapi.shared.utils.statistics;

public class GroupEventsCount {
    private String establishmentName;
    private String eventName;
    private Long count;

    public GroupEventsCount() {}

    public GroupEventsCount(String establishmentName, String eventName, Long count) {
        this.establishmentName = establishmentName;
        this.eventName = eventName;
        this.count = count;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
