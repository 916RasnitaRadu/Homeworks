package model;

public class Cake extends entity{
    protected String flavour;

    public Cake(String cioco) {
        this.flavour = cioco;
    }

    public void Cake(String flavour) { this.flavour = flavour;}

    @Override
    public String toString() {
        return  "Cake{" +
                "flavour ='" + this.flavour + '\'' +
                ", weight = " + weight +
                '}';
    }
}
