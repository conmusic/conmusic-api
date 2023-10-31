package school.sptech.conmusicapi.modules.schedules.utils.datafiles;

import school.sptech.conmusicapi.modules.schedules.dtos.ReadScheduleDto;
import school.sptech.conmusicapi.shared.exceptions.UnreadableFileException;
import school.sptech.conmusicapi.shared.utils.datafiles.importers.DataImporter;

public class ScheduleImporterResolver {
    public static DataImporter<ReadScheduleDto> resolve(String filename) {
        String[] filenameParts = filename.split("\\.");
        String mimeType = filenameParts[filenameParts.length - 1].toLowerCase();

        return switch (mimeType) {
            case "csv" -> new ScheduleCsvImporter();
            case "txt" -> new ScheduleTxtImporter();
            default -> throw new UnreadableFileException("File extension is not valid for this operation");
        };
    }
}
