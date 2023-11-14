package school.sptech.conmusicapi.shared.utils.statistics;

import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;

public class StatusCount {
    private ShowStatusEnum status;
    private Long count;

    public StatusCount(ShowStatusEnum status, Long count) {
        this.status = status;
        this.count = count;
    }

    public ShowStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ShowStatusEnum status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
