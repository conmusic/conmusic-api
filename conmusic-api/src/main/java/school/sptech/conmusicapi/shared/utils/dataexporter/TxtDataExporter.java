package school.sptech.conmusicapi.shared.utils.dataexporter;

public abstract class TxtDataExporter<T> extends DataExporter<T> {
    public TxtDataExporter() {
        this.TYPE = DataExporterEnum.TXT;
    }
}
