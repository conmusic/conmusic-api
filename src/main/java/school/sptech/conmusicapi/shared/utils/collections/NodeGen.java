package school.sptech.conmusicapi.shared.utils.collections;

import java.util.ArrayList;
import java.util.List;

public class NodeGen {
    private TypeForDeletionEnum type;
    private Object info;
    private List list;

    public NodeGen(Object arg, TypeForDeletionEnum typeForDeletionEnum) {
        this.type = typeForDeletionEnum;
        this.info = arg;
        this.list = new ArrayList<>();
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

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
