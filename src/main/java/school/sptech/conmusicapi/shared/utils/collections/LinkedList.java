package school.sptech.conmusicapi.shared.utils.collections;

import school.sptech.conmusicapi.modules.artist.entities.Artist;

public class LinkedList {

    private Node head = new Node(null);

    public LinkedList() {
    }

    public void insertNode(Artist info) {
        Node novo = new Node(info);
        novo.setNext(this.head.getNext());
        this.head.setNext(novo);
    }

    public void show() {
        for(Node atual = this.head.getNext(); atual != null; atual = atual.getNext()) {
            System.out.println(atual.getInfo());
        }
    }

    public Node searchNode(int id) {
        for(Node atual = this.head.getNext(); atual != null; atual = atual.getNext()) {
            if (atual.getInfo().getId() == id) {
                return atual;
            }
        }

        return null;
    }

    public boolean removeNode(Artist info) {
        Node ant = this.head;

        for(Node atual = this.head.getNext(); atual != null; atual = atual.getNext()) {
            if (atual.getInfo() == info) {
                ant.setNext(atual.getNext());
                return true;
            }

            ant = atual;
        }

        return false;
    }
}
