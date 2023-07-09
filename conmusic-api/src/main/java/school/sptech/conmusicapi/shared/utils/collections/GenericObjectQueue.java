package school.sptech.conmusicapi.shared.utils.collections;

import school.sptech.conmusicapi.shared.utils.iterator.GenericObjectQueueIterator;
import school.sptech.conmusicapi.shared.utils.iterator.IGenericIterator;

import java.util.List;

public class GenericObjectQueue<Obj> implements IGenericObjectCollection {
    protected Obj[] queue;
    protected int size;

    public GenericObjectQueue(int maxSize) {
        this.queue = (Obj[]) new Object[maxSize];
        this.size = 0;
    }

    public void insert(Obj info) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        } else {
            queue[size++] = info;
        }
    }

    public Obj peek() {
        return queue[0];
    }

    public Obj poll() {
        Obj value = queue[0];
        for (int i = 0; i < size - 1; i++) {
            queue[i] = queue[i+1];
        }
        queue[--size] = null;
        return value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == queue.length;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public Obj getByIndex(int index) {
        if (isIndexInvalid(index)) {
            return null;
        }

        return queue[index];
    }

    @Override
    public Obj[] asArray() {
        return queue;
    }

    @Override
    public List<Obj> asList() {
        return List.of(queue);
    }

    @Override
    public void clear() {
        int originalMaxSize = queue.length;
        this.queue = (Obj[]) new Object[originalMaxSize];
        this.size = 0;
    }

    @Override
    public IGenericIterator<Obj> createIterator() {
        return new GenericObjectQueueIterator<Obj>(this);
    }

    private boolean isIndexInvalid(int index) {
        return index < 0 || index > size;
    }
}
