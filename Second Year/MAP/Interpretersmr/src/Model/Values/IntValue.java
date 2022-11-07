package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value{
    private int value;

    public IntValue(int v) { this.value = v;}

    public int getValue() { return this.value;}

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value clone() {
        return new IntValue(value);
    }
}
