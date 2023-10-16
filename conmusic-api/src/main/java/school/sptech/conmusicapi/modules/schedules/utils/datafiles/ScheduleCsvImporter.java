package school.sptech.conmusicapi.modules.schedules.utils.datafiles;

import school.sptech.conmusicapi.modules.schedules.dtos.ReadScheduleDto;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.shared.exceptions.UnreadableFileException;
import school.sptech.conmusicapi.shared.utils.datafiles.importers.CsvImporter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleCsvImporter extends CsvImporter<ReadScheduleDto> {
    @Override
    public List<ReadScheduleDto> read(InputStream data) throws RuntimeException {
        List<ReadScheduleDto> schedules = new ArrayList<>();

        BufferedReader reader;
        InputStreamReader streamReader;

        try {
            streamReader = new InputStreamReader(data, StandardCharsets.UTF_8);
            reader = new BufferedReader(streamReader);
        }
        catch (Exception e) {
          throw new UnreadableFileException("Failed to open and read file");
        }

        try {
            reader.lines().forEach(line -> {
                String[] values = line.split(";");

                if (values.length != 3) {
                    throw new UnreadableFileException("File does not have right format and/or values");
                }

                Integer eventId = Integer.parseInt(values[0]);
                LocalDateTime startDateTime = LocalDateTime.parse(values[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime endDateTime = LocalDateTime.parse(values[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                schedules.add(ScheduleMapper.toReadScheduleDto(eventId, startDateTime, endDateTime));
            });
        }
        catch (DateTimeParseException e) {
            throw new UnreadableFileException("At least one datetime value is invalid");
        }
        catch (NumberFormatException e) {
            throw new UnreadableFileException("At least one eventId value is not a number");
        }
        catch (Exception e) {
            throw new UnreadableFileException("Unexpected exception: (" + e.getClass().getSimpleName() + ") " + e.getMessage());
        }

        return schedules;
    }
}
