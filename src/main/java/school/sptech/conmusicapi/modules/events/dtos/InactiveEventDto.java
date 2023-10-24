package school.sptech.conmusicapi.modules.events.dtos;

public class InactiveEventDto {
    private Integer id;
    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
