package school.sptech.conmusicapi.shared.utils.dataexporter;

public abstract class CsvDataExporter<T> extends DataExporter<T> {
    public CsvDataExporter() {
        this.TYPE = DataExporterEnum.CSV;
    }
}
