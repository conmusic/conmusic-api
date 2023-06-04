package school.sptech.conmusicapi.shared.utils.statistics;

public class GroupMonthCount {
    private String month;

    private Integer count;

    public GroupMonthCount() {
    }

    public GroupMonthCount(String month, Integer count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
