package school.sptech.conmusicapi.shared.utils.specifications;

import java.util.Arrays;

public enum SearchOperation {

    GREATER_THAN(">"),
    LESS_THAN("<"),
    EQUAL(":"),
    CONTAINS("~"),
    STARTS_WITH("^"),
    ENDS_WITH("$"),
    NOT_EQUAL("!="),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN_OR_EQUAL("<="),
    IN("in"),
    NOT_IN("not_in"),
    BETWEEN("between"),
    IS_NULL("is_null"),
    IS_NOT_NULL("is_not_null"),
    ZERO_OR_MORE_REGEX("*"),
    ONE_OR_MORE_REGEX("+"),
    ZERO_OR_ONE_REGEX("?");

    private final String operation;

    SearchOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public static SearchOperation getOperation(String operation) {
        return Arrays.stream(SearchOperation.values()).filter(ope -> ope.getOperation().equals(operation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Operação de pesquisa não suportada: " + operation));
    }
}
