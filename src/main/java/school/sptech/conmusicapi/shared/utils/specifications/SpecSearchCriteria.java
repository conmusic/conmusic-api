package school.sptech.conmusicapi.shared.utils.specifications;

public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public SpecSearchCriteria(boolean orPredicate, String key, SearchOperation operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.orPredicate = orPredicate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }
}
