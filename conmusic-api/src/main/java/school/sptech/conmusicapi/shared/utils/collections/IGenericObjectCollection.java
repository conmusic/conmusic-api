package school.sptech.conmusicapi.shared.utils.collections;

import java.util.List;

public interface IGenericObjectCollection<Obj> {
    boolean isEmpty();
    boolean isFull();
    int getSize();
    Obj getByIndex(int index);
    Obj[] asArray();
    List<Obj> asList();
    void clear();
}
