package school.sptech.conmusicapi.shared.utils.iterator;

import school.sptech.conmusicapi.shared.utils.collections.GenericObjectStack;

public class GenericObjectStackIterator<T> implements IGenericIterator<T> {
    private GenericObjectStack<T> collection;
    private GenericObjectStack<T> previous;
    private T currentElement;

    public GenericObjectStackIterator(GenericObjectStack<T> collection) {
        this.collection = collection;
        this.previous = new GenericObjectStack<T>(collection.getSize());
        this.currentElement = null;
    }

    @Override
    public T getNext() {
        currentElement = collection.pop();
        previous.push(currentElement);
        return currentElement;
    }

    @Override
    public boolean hasMore() {
        return !collection.isEmpty();
    }

    @Override
    public void reset() {
        while (hasMore()) {
            previous.push(collection.pop());
        }

        collection = previous;
        previous = new GenericObjectStack<T>(collection.getSize());
        currentElement = null;
    }
}
