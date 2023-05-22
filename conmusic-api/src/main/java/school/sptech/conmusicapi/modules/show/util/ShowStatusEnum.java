package school.sptech.conmusicapi.modules.show.util;

public enum ShowStatusEnum {
    UNDEFINED,
    ARTIST_PROPOSAL,
    MANAGER_PROPOSAL,
    NEGOTIATION,
    ARTIST_ACCEPTED,
    MANAGER_ACCEPTED,
    CONFIRMED,
    CONCLUDED,
    ARTIST_REJECTED,
    MANAGER_REJECTED,
    ARTIST_WITHDRAW,
    MANAGER_WITHDRAW,
    ARTIST_CANCELED,
    MANAGER_CANCELED;

    public boolean isStatusChangeValid(ShowStatusEnum newStatus) {
        return switch (this) {
            case ARTIST_PROPOSAL -> newStatus.equals(ShowStatusEnum.NEGOTIATION)
                    || newStatus.equals(ShowStatusEnum.MANAGER_REJECTED);
            case MANAGER_PROPOSAL -> newStatus.equals(ShowStatusEnum.NEGOTIATION)
                    || newStatus.equals(ShowStatusEnum.ARTIST_REJECTED);
            case NEGOTIATION -> newStatus.equals(ShowStatusEnum.NEGOTIATION)
                    || newStatus.equals(ShowStatusEnum.ARTIST_ACCEPTED)
                    || newStatus.equals(ShowStatusEnum.MANAGER_ACCEPTED)
                    || newStatus.equals(ShowStatusEnum.ARTIST_WITHDRAW)
                    || newStatus.equals(ShowStatusEnum.MANAGER_WITHDRAW);
            case ARTIST_ACCEPTED, MANAGER_ACCEPTED -> newStatus.equals(ShowStatusEnum.NEGOTIATION)
                    || newStatus.equals(ShowStatusEnum.CONFIRMED)
                    || newStatus.equals(ShowStatusEnum.ARTIST_WITHDRAW)
                    || newStatus.equals(ShowStatusEnum.MANAGER_WITHDRAW);
            case CONFIRMED -> newStatus.equals(ShowStatusEnum.CONCLUDED)
                    || newStatus.equals(ARTIST_CANCELED)
                    || newStatus.equals(MANAGER_CANCELED);
            default -> false;
        };
    }

    public static ShowStatusEnum getStatusByName(String name) {
        return switch (name) {
            case "ARTIST_PROPOSAL" -> ShowStatusEnum.ARTIST_PROPOSAL;
            case "MANAGER_PROPOSAL" -> ShowStatusEnum.MANAGER_PROPOSAL;
            case "NEGOTIATION" -> ShowStatusEnum.NEGOTIATION;
            case "ARTIST_ACCEPTED" -> ShowStatusEnum.ARTIST_ACCEPTED;
            case "MANAGER_ACCEPTED" -> ShowStatusEnum.MANAGER_ACCEPTED;
            case "CONFIRMED" -> ShowStatusEnum.CONFIRMED;
            case "CONCLUDED" -> ShowStatusEnum.CONCLUDED;
            case "ARTIST_REJECTED" -> ShowStatusEnum.ARTIST_REJECTED;
            case "MANAGER_REJECTED" -> ShowStatusEnum.MANAGER_REJECTED;
            case "ARTIST_WITHDRAW" -> ShowStatusEnum.ARTIST_WITHDRAW;
            case "MANAGER_WITHDRAW" -> ShowStatusEnum.MANAGER_WITHDRAW;
            case "ARTIST_CANCELED" -> ShowStatusEnum.ARTIST_CANCELED;
            case "MANAGER_CANCELED" -> ShowStatusEnum.MANAGER_CANCELED;
            default -> ShowStatusEnum.UNDEFINED;
        };
    }
}
