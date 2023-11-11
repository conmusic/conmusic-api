package school.sptech.conmusicapi.shared.utils.datafiles.importers;

import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

import java.io.InputStream;
import java.util.List;

public abstract class DataImporter<T> {
    protected DataFilesEnum TYPE = DataFilesEnum.UNDEFINED;

    public abstract List<T> read(InputStream data);

    public DataFilesEnum getTYPE() {
        return TYPE;
    }
}
