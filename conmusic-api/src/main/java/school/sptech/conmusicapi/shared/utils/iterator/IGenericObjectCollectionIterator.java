package school.sptech.conmusicapi.shared.utils.iterator;

public interface IGenericObjectCollectionIterator<Obj> {
    Obj getNext();
    boolean hasMore();
}
