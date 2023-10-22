package school.sptech.conmusicapi.modules.schedules.utils.datafiles;

import school.sptech.conmusicapi.modules.schedules.dtos.ReadScheduleDto;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.shared.exceptions.UnreadableFileException;
import school.sptech.conmusicapi.shared.utils.datafiles.importers.TxtImporter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTxtImporter extends TxtImporter<ReadScheduleDto> {
    private final int CURRENT_VERSION = 1;

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
                String type = line.substring(0, 2);
                if (type.equals("11")) {
                    handleHeader(line);
                } else if (type.equals("01")) {
                    schedules.add(handleBody(line));
                } else if (type.equals("00")) {
                    handleTrailer(line);
                } else {
                    throw new UnreadableFileException("File does not have right format and/or values");
                }
            });
        }
        catch (DateTimeParseException e) {
            throw new UnreadableFileException("At least one datetime value is invalid");
        }
        catch (NumberFormatException e) {
            throw new UnreadableFileException("At least one eventId value is not a number");
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new UnreadableFileException("Unexpected exception: (" + e.getClass().getSimpleName() + ") " + e.getMessage());
        }

        return schedules;
    }

    private int handleTrailer(String line) throws RuntimeException {
        if (line.length() != 5) {
            throw new UnreadableFileException("Trailer is above or below 5 chars length limit");
        }

        return Integer.parseInt(line.substring(2, 5));
    }

    private ReadScheduleDto handleBody(String line) throws RuntimeException {
        if (line.length() != 42) {
            throw new UnreadableFileException("At least one line in body content is invalid");
        }

        System.out.println(line.substring(2, 10));
        System.out.println(line.substring(10, 26));
        System.out.println(line.substring(26, 42));

        Integer eventId = Integer.parseInt(line.substring(2, 10));
        LocalDateTime startDateTime = LocalDateTime.parse(line.substring(10, 26), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endDateTime = LocalDateTime.parse(line.substring(26, 42), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return ScheduleMapper.toReadScheduleDto(eventId, startDateTime, endDateTime);
    }

    private void handleHeader(String line) throws RuntimeException {
        if (line.length() != 32) {
            throw new UnreadableFileException("Header is below or above 32 char length limit");
        }

        String fileType = line.substring(2, 11).trim();
        LocalDateTime date = LocalDateTime.parse(line.substring(11, 30), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Integer version = Integer.parseInt(line.substring(30, 32));

        if (!fileType.equals("AGENDA") && !fileType.equals("SCHEDULES")) {
            throw new UnreadableFileException("Invalid file identifier type in header");
        }

        if (version != CURRENT_VERSION) {
            throw new UnreadableFileException("Invalid file version in header");
        }
    }
}
