package school.sptech.conmusicapi.modules.show.util;

public enum LifeCycleEnum {
    UNDEFINED,
    PROPOSAL,
    NEGOTIATION,
    CONFIRMED,
    CONCLUDED,
    REJECTED,
    WITHDRAW,
    CANCELED;

    public static LifeCycleEnum getLifeCycleBasedOnShowStatus(ShowStatusEnum status) {
        return switch (status) {
            case ARTIST_PROPOSAL, MANAGER_PROPOSAL -> PROPOSAL;
            case NEGOTIATION, ARTIST_ACCEPTED, MANAGER_ACCEPTED -> NEGOTIATION;
            case CONFIRMED -> CONFIRMED;
            case CONCLUDED -> CONCLUDED;
            case ARTIST_REJECTED, MANAGER_REJECTED -> REJECTED;
            case ARTIST_WITHDRAW, MANAGER_WITHDRAW -> WITHDRAW;
            case ARTIST_CANCELED, MANAGER_CANCELED -> CANCELED;
            default -> UNDEFINED;
        };
    }
}
