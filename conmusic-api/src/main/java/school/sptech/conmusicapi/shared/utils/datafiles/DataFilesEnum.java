package school.sptech.conmusicapi.shared.utils.datafiles;

import org.springframework.http.MediaType;

public enum DataFilesEnum {
    UNDEFINED,
    CSV,
    TXT;

    public static DataFilesEnum getByName(String fileFormat) {
        return switch (fileFormat) {
            case "CSV" -> CSV;
            case "TXT" -> TXT;
            default -> UNDEFINED;
        };
    }

    public MediaType getContentType() {
        return switch (this) {
            case CSV -> MediaType.parseMediaType("text/csv");
            default ->  MediaType.TEXT_PLAIN;
        };
    }

    public String getExtension() {
        return switch (this) {
            case CSV -> ".csv";
            default -> ".txt";
        };
    }
}
