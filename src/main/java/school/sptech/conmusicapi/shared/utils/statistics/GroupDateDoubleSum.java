package school.sptech.conmusicapi.shared.utils.statistics;

import java.time.LocalDate;

public class GroupDateDoubleSum {
    private LocalDate date;
    private Double count;

    public GroupDateDoubleSum() {}

    public GroupDateDoubleSum(LocalDate date, Double count) {
        this.date = date;
        this.count = count;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getCount() {
        return count;
    }
}
