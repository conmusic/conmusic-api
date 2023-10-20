package school.sptech.conmusicapi.shared.utils.datafiles.importers;

import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

public abstract class TxtImporter<T> extends DataImporter<T> {
    public TxtImporter() {
        this.TYPE = DataFilesEnum.TXT;
    }
}
