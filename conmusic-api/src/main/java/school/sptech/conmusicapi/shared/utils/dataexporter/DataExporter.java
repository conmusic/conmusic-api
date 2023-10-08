package school.sptech.conmusicapi.shared.utils.dataexporter;

import java.io.InputStream;
import java.util.List;

public abstract class DataExporter<T> {
    protected DataExporterEnum TYPE = DataExporterEnum.UNDEFINED;

    public abstract List<T> read(InputStream data);

    public DataExporterEnum getTYPE() {
        return TYPE;
    }
}
