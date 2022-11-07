package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{
    private boolean value;

    public BoolValue(boolean value) {this.value = value;}

    public boolean getValue() { return value;}

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value clone() {
        return new BoolValue(value);
    }
}
