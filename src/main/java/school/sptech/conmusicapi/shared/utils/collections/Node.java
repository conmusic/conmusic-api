package school.sptech.conmusicapi.shared.utils.collections;

import school.sptech.conmusicapi.modules.artist.entities.Artist;

public class Node {

    private Artist info;
    private Node next;

    public Node(Artist info) {
        this.info = info;
        this.next = null;
    }

    public Artist getInfo() {
        return this.info;
    }

    public void setInfo(Artist info) {
        this.info = info;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
