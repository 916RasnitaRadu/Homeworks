package model;

import java.util.*;

public class ProgramInternalForm {
    private final List<Pair<String, Pair<Integer, Integer>>> tokenPosition;

    public ProgramInternalForm() {
        this.tokenPosition = new ArrayList<>();
    }

    public void add(Pair<String, Pair<Integer, Integer>> pair, Type newType) {
        this.tokenPosition.add(pair);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Pair<String, Pair<Integer, Integer>> pair : this.tokenPosition) {
            result.append(pair.getFirst())
                    .append(" - ")
                    .append(pair.getSecond());
        }
        return result.toString();
    }
}
