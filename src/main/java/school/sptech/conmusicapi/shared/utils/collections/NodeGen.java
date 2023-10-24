package school.sptech.conmusicapi.shared.utils.collections;

public class NodeGen {
    private TypeForDeletionEnum type;
    private Object info;
    private GenericObjectList<Object> list;

    public NodeGen(Object arg, TypeForDeletionEnum typeForDeletionEnum, int size) {
        this.type = typeForDeletionEnum;
        this.info = arg;
        this.list = new GenericObjectList<>(size);
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

    public GenericObjectList getList() {
        return list;
    }

    public void setList(GenericObjectList list) {
        this.list = list;
    }
}
