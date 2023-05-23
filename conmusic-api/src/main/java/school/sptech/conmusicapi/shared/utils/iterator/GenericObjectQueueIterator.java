package school.sptech.conmusicapi.shared.utils.iterator;

import school.sptech.conmusicapi.shared.utils.collections.GenericObjectCircularQueue;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectQueue;

public class GenericObjectQueueIterator<T> implements IGenericIterator<T> {
    private GenericObjectQueue<T> collection;
    private GenericObjectQueue<T> previous;
    private T currentElement;

    public GenericObjectQueueIterator(GenericObjectQueue<T> collection) {
        this.collection = collection;
        this.previous = new GenericObjectCircularQueue<>(collection.getSize());
        this.currentElement = null;
    }

    @Override
    public T getNext() {
        currentElement = collection.poll();
        previous.insert(currentElement);
        return currentElement;
    }

    @Override
    public boolean hasMore() {
        return !collection.isEmpty();
    }

    @Override
    public void reset() {
        while (hasMore()) {
            previous.insert(collection.poll());
        }

        this.collection = previous;
        this.previous = new GenericObjectCircularQueue<T>(collection.getSize());
        this.currentElement = null;
    }
}
