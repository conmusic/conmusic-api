package school.sptech.util;

public class PilhaObj<T> {
    private T[] pilha;
    private int topo;

    public PilhaObj(int capacidade) {
        this.pilha = (T[]) new Object[capacidade];
        this.topo = -1;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == (pilha.length - 1);
    }

    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        } else {
            System.out.println("A pilha está cheia");
        }
    }

    public T pop() {
        if (!isEmpty()) {
            return pilha[topo--];
        }

        return null;
    }

    public T peek() {
        if (!isEmpty()) {
            return pilha[topo];
        }

        return null;
    }

    public void exibeLifo() {
        if (!isEmpty()) {
            System.out.println("Exibição LIFO");
            for (int i = topo; i >= 0; i--) {
                System.out.print(pilha[i] + "\n");
            }
        }
    }

    public void exibeFifo() {
        if (!isEmpty()) {
            System.out.println("Exibição FIFO");
            for (int i = 0; i < pilha.length; i++) {
                System.out.print(pilha[i] + "\n");
            }
        }
    }

    public T[] getPilha() {
        return pilha;
    }
}
