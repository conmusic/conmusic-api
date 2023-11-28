package school.sptech.conmusicapi.modules.media.dtos;

public class MediaEstablishmentDto {

    private String url;

    private Integer establishmentId;

    public MediaEstablishmentDto(Integer mediaId, Integer establishmentId) {

        String baseUrl = "http://ec2-54-145-89-39.compute-1.amazonaws.com:8080";

        this.url = String.format("%s/establishments/media/%d", baseUrl, mediaId);
        this.establishmentId = establishmentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(Integer establishmentId) {
        this.establishmentId = establishmentId;
    }
}
