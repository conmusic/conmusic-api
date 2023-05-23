package school.sptech.conmusicapi.shared.utils.iterator;

public interface IGenericIterator<Obj> {
    Obj getNext();
    boolean hasMore();
    void reset();
}
