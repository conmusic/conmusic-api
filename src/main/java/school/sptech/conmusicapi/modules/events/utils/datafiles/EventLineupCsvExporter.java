package school.sptech.conmusicapi.modules.events.utils.datafiles;

import school.sptech.conmusicapi.modules.events.dtos.EventLineupExportDto;
import school.sptech.conmusicapi.shared.utils.datafiles.exporters.CsvExporter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventLineupCsvExporter extends CsvExporter<EventLineupExportDto> {
    @Override
    public String write(List<EventLineupExportDto> data) {
        Stream<String> lines = data.stream().map(this::buildLine);
        return lines.collect(Collectors.joining("\n"));
    }

    private String buildLine(EventLineupExportDto dto) {
        return dto.getEventName() +
                ';' + dto.getGenre() +
                ';' + dto.getEstablishmentName() +
                ';' + dto.getEstablishmentAddress() +
                ';' + dto.getEstablishmentCity() +
                ';' + dto.getEstablishmentState() +
                ';' + dto.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:00")) +
                ';' + dto.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:00")) +
                ';' + dto.getArtistName() +
                ';' + (dto.getArtistInstagram() != null ? dto.getArtistInstagram() : "");
    }
}
