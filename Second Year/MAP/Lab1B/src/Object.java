public abstract class Object {
    protected int weight;
    private static int no_objects = 0;

    static {
        no_objects += 1;
    }

    public Object() { }
    public int getWeight() { return weight;}

    public static int getNo_objects() { return no_objects;}

    public void setWeight(int w) { this.weight = w;}
}
