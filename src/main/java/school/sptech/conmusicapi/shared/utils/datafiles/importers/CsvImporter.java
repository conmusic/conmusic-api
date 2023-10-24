package school.sptech.conmusicapi.shared.utils.datafiles.importers;

import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

public abstract class CsvImporter<T> extends DataImporter<T> {
    public CsvImporter() {
        this.TYPE = DataFilesEnum.CSV;
    }
}
