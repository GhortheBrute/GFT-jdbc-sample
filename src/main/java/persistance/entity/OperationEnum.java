package persistance.entity;

import java.util.stream.Stream;

public enum OperationEnum {

    INSERT,
    UPDATE,
    DELETE;

    public static OperationEnum getByOperation(String operation) {
        return Stream.of(OperationEnum.values())
                .filter(op -> op.name().startsWith(operation.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation: " + operation));
    }
}
