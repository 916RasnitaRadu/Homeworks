package Model.Types;


import Model.Values.*;

public class BoolType implements Type
{
    public boolean equals(Object obj) {
        if (obj instanceof BoolType) return true;
        return false;
    }

    @Override
    public String toString() {
        return "bool ";
    }
    @Override
    public Value default_value() {
        return new BoolValue(false);
    }

    @Override
    public BoolType clone()
    {
        return new BoolType();
    }
}
