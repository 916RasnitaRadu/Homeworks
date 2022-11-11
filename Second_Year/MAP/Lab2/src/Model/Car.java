package Model;

public class Car extends Vehicle{
    private float cylindric_capacity;

    public Car(String color,int weight, float cc)
    {
        this.color = color;
        this.weight = weight;
        cylindric_capacity = cc;
    }

    public float getCylindric_capacity() { return cylindric_capacity;}

    public void setCylindric_capacity(float cc) { this.cylindric_capacity = cc;}

    @Override
    public String toString()
    {
        return "This car has the color " + this.color + " and the cc of " + this.cylindric_capacity + " with the weight of " + this.weight;
    }
}
