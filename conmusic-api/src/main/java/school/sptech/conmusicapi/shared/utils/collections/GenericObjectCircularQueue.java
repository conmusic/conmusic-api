package school.sptech.conmusicapi.shared.utils.collections;

import school.sptech.conmusicapi.shared.utils.iterator.GenericObjectQueueIterator;
import school.sptech.conmusicapi.shared.utils.iterator.IGenericIterator;

public class GenericObjectCircularQueue<Obj> extends GenericObjectQueue<Obj> {
    private int start;
    private int end;

    public GenericObjectCircularQueue(int maxSize) {
        super(maxSize);
        this.start = 0;
        this.end = 0;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public void insert(Obj element) {
        if (isFull()) {
            throw new IllegalStateException("Circular Queue is full");
        } else {
            queue[end] = element;
            end = (end + 1) % queue.length;
            size++;
        }
    }

    @Override
    public Obj peek() {
        if (isEmpty()) {
            return null;
        } else {
            return queue[start];
        }
    }

    @Override
    public Obj poll() {
        if (isEmpty()) {
            return null;
        } else {
            Obj value = queue[start];
            queue[start] = null;
            start = (start + 1) % queue.length;
            size--;
            return value;
        }
    }

    @Override
    public Obj[] asArray(){
        Obj[] array = (Obj[]) new Object[size];

        int index = 0;
        int i = start;
        while (index < array.length) {
            array[index++] = queue[i];
            i = (i + 1) % queue.length;
        }

        return array;
    }

    @Override
    public void clear() {
        super.clear();
        this.start = 0;
        this.end = 0;
    }

    @Override
    public Obj getByIndex(int index) {
        if (isIndexInvalid(index)) {
            return null;
        }

        Obj value = null;

        int i = start;
        for (int numberOfLaps = index; numberOfLaps >= 0; numberOfLaps--) {
            value = queue[i];
            i = (i + 1) % queue.length;

        }

        return value;
    }

    @Override
    public IGenericIterator<Obj> createIterator() {
        return new GenericObjectQueueIterator<Obj>(this);
    }

    private boolean isIndexInvalid(int index) {
        return index < 0 || index >= size;
    }
}

