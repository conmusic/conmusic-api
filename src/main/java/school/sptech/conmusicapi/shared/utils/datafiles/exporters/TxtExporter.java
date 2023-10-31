package school.sptech.conmusicapi.shared.utils.datafiles.exporters;

import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

import java.time.format.DateTimeFormatter;

public abstract class TxtExporter<T> extends DataExporter<T> {
    public TxtExporter() {
        this.TYPE = DataFilesEnum.TXT;
    }
}
