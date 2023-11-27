package school.sptech.conmusicapi.modules.admin.dtos;

public class AdminKpiDto {
    private String category;
    private Long total;
    private Long byArtist;
    private Long byManager;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getByArtist() {
        return byArtist;
    }

    public void setByArtist(Long byArtist) {
        this.byArtist = byArtist;
    }

    public Long getByManager() {
        return byManager;
    }

    public void setByManager(Long byManager) {
        this.byManager = byManager;
    }
}
