import model.MyScanner;

public class Main {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner();
        String prog1 = "p1.vtm";
        String prog2 = "p2.vtm";
        String prog3 = "p3.vtm";
        String prog1err = "p1err.vtm";
        scanner.scan(prog1);
        scanner.scan(prog2);
        scanner.scan(prog3);
        scanner.scan(prog1err);
    }
}