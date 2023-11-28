package school.sptech.conmusicapi.modules.user.dtos;

public class UserKpiDto {
    private long receivedProposals;
    private long negotiations;
    private long negotiationsStartedByYou;
    private long confirmed;
    private double percentageConfirmed;
    private long canceled;
    private double percentageCanceled;

    public long getReceivedProposals() {
        return receivedProposals;
    }

    public void setReceivedProposals(long receivedProposals) {
        this.receivedProposals = receivedProposals;
    }

    public long getNegotiations() {
        return negotiations;
    }

    public void setNegotiations(long negotiations) {
        this.negotiations = negotiations;
    }

    public long getNegotiationsStartedByYou() {
        return negotiationsStartedByYou;
    }

    public void setNegotiationsStartedByYou(long negotiationsStartedByYou) {
        this.negotiationsStartedByYou = negotiationsStartedByYou;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public double getPercentageConfirmed() {
        return percentageConfirmed;
    }

    public void setPercentageConfirmed(double percentageConfirmed) {
        this.percentageConfirmed = percentageConfirmed;
    }

    public long getCanceled() {
        return canceled;
    }

    public void setCanceled(long canceled) {
        this.canceled = canceled;
    }

    public double getPercentageCanceled() {
        return percentageCanceled;
    }

    public void setPercentageCanceled(double percentageCanceled) {
        this.percentageCanceled = percentageCanceled;
    }
}
