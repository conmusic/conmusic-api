package school.sptech.conmusicapi.modules.events.utils.datafiles;

import school.sptech.conmusicapi.modules.events.dtos.EventLineupExportDto;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;
import school.sptech.conmusicapi.shared.utils.datafiles.exporters.DataExporter;

public class EventLineupResolver {
    public static DataExporter<EventLineupExportDto> resolve(DataFilesEnum outputFormat) {
        return switch (outputFormat) {
            case CSV -> new EventLineupCsvExporter();
            case TXT -> new EventLineupTxtExporter();
            case UNDEFINED -> throw new BusinessRuleException("Invalid format selected");
        };
    }
}
