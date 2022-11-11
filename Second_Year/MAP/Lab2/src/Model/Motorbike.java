package Model;

public class Motorbike extends Vehicle {
    private String brand;

    public Motorbike(String color, int weight, String brand) {
        this.color = color;
        this.weight = weight;
        this.brand = brand;
    }

    @Override
    public String toString()
    {
        return "This motorbike has the color " + this.color + " from the brand " + this.brand + " with the weight of " + this.weight;
    }
}
