package school.sptech.conmusicapi.modules.media.dtos;

import org.springframework.beans.factory.annotation.Value;

public class MediaArtistDto {

    private String url;

    private Integer artistId;
    
    @Value("${app.aws.backend.url}")
    private String baseUrl;

    public MediaArtistDto(String url, Integer artistId) {
        this.url = String.format("%s/media/%s", this.baseUrl, url);
        this.artistId = artistId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }
}
