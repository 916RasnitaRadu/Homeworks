package Model;

public abstract class Vehicle {
    protected String color;
    protected int weight;

    public int getWeight() { return this.weight;}

    public String getColor() { return this.color;}

    public void setColor(String color) {this.color = color;}
}
