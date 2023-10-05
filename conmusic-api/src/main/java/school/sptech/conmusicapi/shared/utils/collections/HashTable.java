package school.sptech.conmusicapi.shared.utils.collections;

import school.sptech.conmusicapi.modules.artist.entities.Artist;

import java.util.List;
import java.util.Optional;

public class HashTable {

    private LinkedList[] table;

    public HashTable(int tam) {
        this.table = new LinkedList[tam];
    }

    public int getPosHash(int info) {
        return info % this.table.length;
    }

    public void insert(Artist info) {
        int pos = this.getPosHash(info.getId());
        if (this.table[pos] == null) {
            this.table[pos] = new LinkedList();
        }

        this.table[pos].insertNode(info);
    }

    public Optional<Artist> search(int id) {
        int pos = this.getPosHash(id);

        return this.table[pos].searchNode(id) != null
                ? Optional.of(this.table[pos].searchNode(id).getInfo())
                : Optional.empty();
    }

    public void insertList(List<Artist> list, int index){
        if (index < list.size()) {
            this.insert(list.get(index));
            this.insertList(list, index + 1);
        }
    }
}