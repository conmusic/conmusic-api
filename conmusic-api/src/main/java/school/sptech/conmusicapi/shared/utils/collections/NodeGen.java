package school.sptech.conmusicapi.shared.utils.collections;

public class NodeGen {
    private TypeForDeletionEnum type;
    private Object info;
    private NodeGen next;
    private NodeGen prev;

    public NodeGen(Object arg, TypeForDeletionEnum typeForDeletionEnum) {
        this.type = typeForDeletionEnum;
        this.info = arg;
        this.next = null;
        this.prev = null;
    }

    public TypeForDeletionEnum getType() {
        return type;
    }

    public void setType(TypeForDeletionEnum type) {
        this.type = type;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public NodeGen getnext() {
        return next;
    }

    public void setnext(NodeGen next) {
        this.next = next;
    }

    public NodeGen getprev() {
        return prev;
    }

    public void setprev(NodeGen prev) {
        this.prev = prev;
    }
}
