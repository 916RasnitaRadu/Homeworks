package Model.Values;

import Model.Types.Type;

public interface Value {
    boolean equals(Value anotherValue);
    Type getType();

    Value clone();

}
