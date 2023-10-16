package school.sptech.conmusicapi.modules.events.dtos;

import java.time.LocalDateTime;

public class EventLineupExportDto {
    private String eventName;
    private String genre;
    private String establishmentName;
    private String establishmentAddress;
    private String establishmentCity;
    private String establishmentState;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String artistName;
    private String artistInstagram;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEstablishmentAddress() {
        return establishmentAddress;
    }

    public void setEstablishmentAddress(String establishmentAddress) {
        this.establishmentAddress = establishmentAddress;
    }

    public String getEstablishmentCity() {
        return establishmentCity;
    }

    public void setEstablishmentCity(String establishmentCity) {
        this.establishmentCity = establishmentCity;
    }

    public String getEstablishmentState() {
        return establishmentState;
    }

    public void setEstablishmentState(String establishmentState) {
        this.establishmentState = establishmentState;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistInstagram() {
        return artistInstagram;
    }

    public void setArtistInstagram(String artistInstagram) {
        this.artistInstagram = artistInstagram;
    }
}
