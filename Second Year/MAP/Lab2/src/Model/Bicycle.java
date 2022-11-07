package Model;

public class Bicycle extends Vehicle {
    private float tire_width;

    public Bicycle(String color, int weight, float tw) {
        this.color = color;
        this.weight = weight;
        this.tire_width = tw;
    }

    @Override
    public String toString()
    {
        return "This bicycle has the color " + this.color + " with the tires width of " + this.tire_width + " with the weight of " + this.weight;
    }

}
