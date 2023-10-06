public class Node<T> {
    private T info;
    private Node next;

    public Node(T arg) {
        this.info = arg;
        this.next = null;
    }


    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
