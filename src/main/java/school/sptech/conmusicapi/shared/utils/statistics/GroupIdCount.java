package school.sptech.conmusicapi.shared.utils.statistics;

public class GroupIdCount {
    private Integer id;
    private Long count;

    public GroupIdCount() {
    }

    public GroupIdCount(Integer id, Long count) {
        this.id = id;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
