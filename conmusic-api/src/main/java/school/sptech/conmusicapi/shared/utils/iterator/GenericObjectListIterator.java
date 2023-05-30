package school.sptech.conmusicapi.shared.utils.iterator;

import school.sptech.conmusicapi.shared.utils.collections.GenericObjectList;

public class GenericObjectListIterator<T> implements IGenericIterator<T> {
    private GenericObjectList<T> collection;
    private int currentIndex;

    public GenericObjectListIterator(GenericObjectList<T> collection) {
        this.collection = collection;
        this.currentIndex = 0;
    }

    @Override
    public T getNext() {
        if (hasMore()) {
            T element = collection.getByIndex(currentIndex);
            currentIndex++;
            return element;
        } else {
            return null;
        }
    }

    @Override
    public boolean hasMore() {
        return currentIndex < collection.getSize();
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }
}
