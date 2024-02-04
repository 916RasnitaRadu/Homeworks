package finite_automata;

public class Transition {
    private final String from;
    private final String destination;
    private final String label;

    public Transition(String from, String destination, String label) {
        this.from = from;
        this.destination = destination;
        this.label = label;
    }

    public String getFrom() {
        return from;
    }

    public String getDestination() {
        return destination;
    }

    public String getLabel() {
        return label;
    }


}
