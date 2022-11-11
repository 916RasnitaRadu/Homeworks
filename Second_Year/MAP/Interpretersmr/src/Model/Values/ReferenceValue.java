package Model.Values;

import Model.Types.*;

public class ReferenceValue implements Value{
    protected int address;
    Type location_type;

    public ReferenceValue(int adr, Type t) {
        this.address = adr;
        this.location_type = t;
    }

    public int getAddress() { return  this.address;}

    public Type getLocation_type() { return this.location_type;}

    @Override
    public Type getType() {
        return new ReferenceType(location_type);
    }

    @Override
    public Value clone() {
        return new ReferenceValue(this.address, this.location_type);
    }

    @Override
    public String toString() {
        return "(" + address + "," + location_type.toString() + ")";
    }
}
