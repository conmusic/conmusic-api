package school.sptech.conmusicapi.shared.utils;

import java.lang.reflect.Method;
import java.util.List;

public class GenericObjectList<Obj> {
    private Obj[] array;
    private int pointer;

    public GenericObjectList(int size) {
        array = (Obj[]) new Object[size];
        pointer = 0;
    }

    public void add(Obj element) {
        if (pointer == array.length) {
            throw new IllegalStateException("List is already full");
        } else {
            array[pointer++] = element;
        }
    }

    public int search(Obj element) {
        for (int i = 0; i < pointer; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    public boolean remove(int index) {
        if (isIndexInvalid(index)) {
            return false;
        }

        for (int i = index; (i + 1) < pointer; i++) {
            array[i] = array[i + 1];
        }

        pointer--;
        return true;
    }

    public boolean remove(Obj element) {
        return remove(search(element));
    }

    public int getSize() {
        return pointer;
    }

    public Obj getElement(int index) {
        if (isIndexInvalid(index)) {
            return null;
        }

        return array[index];
    }

    public void clear() {
        int creationSize = array.length;
        array = (Obj[]) new Object[creationSize];
        pointer = 0;
    }

    public boolean swap(int index1, int index2) {
        if (isIndexInvalid(index1) || isIndexInvalid(index2)) {
            return false;
        }

        Obj aux = getElement(index1);
        array[index1] = getElement(index2);
        array[index2] = aux;
        return true;
    }

    public List<Obj> getElements() {
        return List.of(array);
    }

    private boolean isIndexInvalid(int index) {
        return index < 0 || index >= pointer;
    }
}
