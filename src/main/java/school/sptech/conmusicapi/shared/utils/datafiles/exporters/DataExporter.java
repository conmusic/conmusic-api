package school.sptech.conmusicapi.shared.utils.datafiles.exporters;

import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

import java.util.List;

public abstract class DataExporter<T> {
    protected DataFilesEnum TYPE = DataFilesEnum.UNDEFINED;

    public abstract String write(List<T> data);

    public DataFilesEnum getTYPE() {
        return TYPE;
    }
}
