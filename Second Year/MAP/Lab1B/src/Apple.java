public class Apple extends Object{
    private String color;


    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }



    @Override
    public String toString() {
        return "Apple{" +
                "color ='" + this.color + '\'' +
                ", weight = " + weight +
                '}';
    }
}
