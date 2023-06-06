package school.sptech.conmusicapi.shared.utils.statistics;

public class GroupMonthCount {
    private String month;

    private Long count;

    public GroupMonthCount() {
    }

    public GroupMonthCount(String month, Long count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
