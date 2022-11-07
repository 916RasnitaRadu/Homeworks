public class Main {
    public static void main(String[] args) {
        Object apple1 = new Apple(100, "green");

        try {
            Object apple2 = new Apple(-100, "red");
            if (apple2.getWeight() < 0) throw new NegativeWeightException("The weight can not be ngative!");
        }
        catch (NegativeWeightException e) {
            System.out.println(e.get_message());
        }


        System.out.println("The number of total objects is: " + Object.getNo_objects());
    }
}