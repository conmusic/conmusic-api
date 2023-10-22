package school.sptech.conmusicapi.shared.utils.datafiles.exporters;

import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

public abstract class CsvExporter<T> extends DataExporter<T> {
    public CsvExporter() {
        this.TYPE = DataFilesEnum.CSV;
    }
}
