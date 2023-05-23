package school.sptech.conmusicapi.shared.utils.collections;

import school.sptech.conmusicapi.shared.utils.iterator.GenericObjectStackIterator;
import school.sptech.conmusicapi.shared.utils.iterator.IGenericIterator;

import java.util.List;

public class GenericObjectStack<Obj> implements IGenericObjectCollection {
    private Obj[] stack;
    private int top;

    public GenericObjectStack(int capacity) {
        this.stack = (Obj[]) new Object[capacity];
        this.top = -1;
    }

    public void push(Obj info) {
        if (isFull()) {
            throw new IllegalStateException("Stack is already full");
        } else {
            stack[++top] = info;
        }
    }

    public Obj pop() {
        if (isEmpty()) {
            return null;
        }

        return stack[top--];
    }

    public Obj peek() {
        if (isEmpty()) {
            return null;
        }

        return stack[top];
    }

    @Override
    public Obj[] asArray() {
        return stack;
    }

    @Override
    public List<Obj> asList() {
        return List.of(stack);
    }

    @Override
    public void clear() {
        int originalCapacity = stack.length;
        this.stack = (Obj[]) new Object[originalCapacity];
        this.top = -1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public boolean isFull() {
        return top == (stack.length - 1);
    }

    @Override
    public int getSize() {
        return top + 1;
    }

    @Override
    public Object getByIndex(int index) {
        if (isIndexInvalid(index)) {
            return null;
        }

        GenericObjectStack<Obj> auxStack = new GenericObjectStack<Obj>(stack.length - index + 1);

        for (int i = 0; i < index; i++) {
            auxStack.push(this.pop());
        }

        Obj target = this.pop();

        do {
            this.push(auxStack.pop());
        } while (!auxStack.isEmpty());

        return target;
    }

    @Override
    public IGenericIterator<Obj> createIterator() {
        return new GenericObjectStackIterator<Obj>(this);
    }

    private boolean isIndexInvalid(int index) {
        return index < 0 || index > top;
    }
}
