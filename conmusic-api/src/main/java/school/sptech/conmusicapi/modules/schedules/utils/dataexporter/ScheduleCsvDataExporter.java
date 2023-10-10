package school.sptech.conmusicapi.modules.schedules.utils.dataexporter;

import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.shared.exceptions.FailedImportException;
import school.sptech.conmusicapi.shared.exceptions.UnreadableFileException;
import school.sptech.conmusicapi.shared.utils.dataexporter.CsvDataExporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ScheduleCsvDataExporter extends CsvDataExporter<Schedule> {
    @Override
    public List<Schedule> read(InputStream data) throws RuntimeException {
        List<Schedule> schedules = new ArrayList<>();

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
            reader.lines().forEach(System.out::println);
        }
        catch (Exception e) {
            throw new RuntimeException("Unexpected exception: (" + e.getClass().getSimpleName() + ") " + e.getMessage());
        }

        return schedules;
    }
}
