package school.sptech.conmusicapi.modules.events.utils.datafiles;

import school.sptech.conmusicapi.modules.events.dtos.EventLineupExportDto;
import school.sptech.conmusicapi.shared.utils.datafiles.exporters.TxtExporter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventLineupTxtExporter extends TxtExporter<EventLineupExportDto> {
    private final int CURRENT_VERSION = 1;

    @Override
    public String write(List<EventLineupExportDto> data) {
        String header = buildHeader();

        Stream<String> bodyLines = data.stream().map(this::buildLine);
        String body = bodyLines.collect(Collectors.joining("\n"));

        String trailer = buildTrailer(data.size());

        return header + "\n" + body + "\n" + trailer;
    }

    private String buildLine(EventLineupExportDto dto) {
        String line = "01";
        line += String.format("%-45.45s", dto.getEventName());
        line += String.format("%-25.25s", dto.getGenre());
        line += String.format("%-45.45s", dto.getEstablishmentName());
        line += String.format("%-45.45s", dto.getEstablishmentAddress());
        line += String.format("%-45.45s", dto.getEstablishmentCity());
        line += String.format("%-2.2s", dto.getEstablishmentState());
        line += dto.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:00"));
        line += dto.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:00"));
        line += String.format("%-45.45s", dto.getArtistName());
        line += String.format("%-45.45s", dto.getArtistInstagram());

        return line;
    }

    private String buildHeader() {
        return "11" + "LINEUP"
                + LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00"))
                + String.format("%02d", CURRENT_VERSION);
    }

    private String buildTrailer(int amountOfData) {
        return "00" + String.format("%05d", amountOfData);
    }
}
