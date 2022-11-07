package model;


public class Apple extends entity {
    protected String color;

    public Apple(String color) { this.color = color;}

    @Override
    public String toString()
    {
        return "Apple{" +
                "color ='" + this.color + '\'' +
                ", weight = " + weight +
                '}';
    }
}
