package finite_automata;

import finite_automata.Transition;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class FiniteAutomata {
    private List<String> states;
    private List<String> alphabet;
    private List<Transition> transitions;
    private List<String> outputStates;

    private String initialState;
    private String fileName;
    private final Pattern labelsRegex = Pattern.compile("^([a-z_]*)=");

    public FiniteAutomata(String fileName) {
        this.fileName = fileName;
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.outputStates = new ArrayList<>();
        this.initialState = "";

        try {
            init();
        }
        catch (Exception e) {
            System.out.println("Error when initializing");
        }

    }

    public boolean checkSequence(String word) {
        String currentState = initialState;
        for (String ch : word.split("")) {
            boolean found = false;
            for (Transition transition : transitions) {
                if (transition.getFrom().equals(currentState) && transition.getLabel().equals(ch)) {
                    currentState = transition.getDestination();
                    found = true;
                    break;
                }
            }
            if (!found) return false;

        }
        return outputStates.contains(currentState);
    }

    public String getNextAcceptedSequence(String word) {
        String currentState = this.initialState;
        StringBuilder acceptedWord = new StringBuilder();
        for (String ch : word.split("")) {
            String newState = null;
            for (Transition transition : transitions) {
                if (transition.getFrom().equals(currentState) && transition.getLabel().equals(ch)) {
                    newState = transition.getDestination();
                    acceptedWord.append(ch);
                    break;
                }
            }
            if (newState == null) {
                if (!outputStates.contains(currentState)) {
                    return null;
                }
                else {
                    return acceptedWord.toString();
                }
            }
            currentState = newState;
        }
        return acceptedWord.toString();
    }

    public void printStates() {
        StringBuilder stringBuilder = new StringBuilder("states = {");
        for (int i = 0; i < states.size(); i++) {
            if (i == states.size() - 1) {
                stringBuilder.append(states.get(i)).append("}");
            }
            else {
                stringBuilder.append(states.get(i)).append(", ");
            }
        }
        System.out.println(stringBuilder);
    }

    public void printAlphabet() {
        StringBuilder stringBuilder = new StringBuilder("alphabet = {");
        for (int i = 0; i < alphabet.size(); i++) {
            if (i == alphabet.size() - 1) {
                stringBuilder.append(alphabet.get(i)).append("}");
            }
            else {
                stringBuilder.append(alphabet.get(i)).append(", ");
            }
        }
        System.out.println(stringBuilder);
    }

    public void printOutputStates() {
        StringBuilder stringBuilder = new StringBuilder("output_states = {");
        for (int i = 0; i < outputStates.size(); i++) {
            if (i == outputStates.size() - 1) {
                stringBuilder.append(outputStates.get(i)).append("}");
            }
            else {
                stringBuilder.append(outputStates.get(i)).append(", ");
            }
        }
        System.out.println(stringBuilder);
    }

    public void printInitialState() {
        System.out.println(initialState);
    }

    public void printTransitions() {
        StringBuilder stringBuilder = new StringBuilder("transitions = {");
        for (int i = 0; i < transitions.size(); i++) {
            if (i == outputStates.size() - 1) {
                stringBuilder.append("(").append(transitions.get(i).getFrom()).append(", ").append(transitions.get(i).getDestination()).append(", ").append(transitions.get(i).getLabel()).append("); ");
            }
            else {
                stringBuilder.append("(").append(transitions.get(i).getFrom()).append(", ").append(transitions.get(i).getDestination()).append(", ").append(transitions.get(i).getLabel()).append(")");
            }
        }
        stringBuilder.append("}");
        System.out.println(stringBuilder);
    }

    private void init() throws Exception {
        for (String line : Files.readAllLines(Paths.get(fileName))) {
            var matcher = labelsRegex.matcher(line);
            var value = matcher.find();

            if (matcher.group(0) == null) {
                throw new Exception("ERROR: Invalid line: " + line);
            }

            switch (matcher.group(0)) {
                case "states=":
                    String statesBrackets = line.substring(line.indexOf("=") + 1);
                    String statesStr = statesBrackets.substring(1, statesBrackets.length() - 1).trim();
                    this.states = List.of(statesStr.split(", ?"));
                    break;
                case "alphabet=":
                    String alphabetBrackets= line.substring(line.indexOf("=") + 1);
                    String alphabet = alphabetBrackets.substring(1, alphabetBrackets.length() - 1).trim();
                    this.alphabet = List.of(alphabet.split(", ?"));
                    break;
                case "out_states=":
                    String outStatesBrackets = line.substring(line.indexOf("=") + 1);
                    String outStates = outStatesBrackets.substring(1, outStatesBrackets.length() - 1).trim();
                    this.outputStates = List.of(outStates.split(", ?"));
                    break;
                case "initial_state=":
                    this.initialState = line.substring(line.indexOf("=") + 1);
                    break;
                case "transitions=":
                    String transitionsBrackets = line.substring(line.indexOf("=") + 1);
                    String transitionsStr = transitionsBrackets.substring(1, transitionsBrackets.length() - 1).trim();
                    var transtitionList = List.of(transitionsStr.split("; *"));
                    for (String transition : transtitionList) {
                        var transitionWithoutParan = transition.substring(1, transition.length() - 1).trim();
                        var individualValues = List.of(transitionWithoutParan.split(", ?"));
                        this.transitions.add(new Transition(individualValues.get(0), individualValues.get(1), individualValues.get(2)));
                    }
                    break;

                default:
                    throw new Exception("ERROR: Invalid line in file");
            }
        }
    }
}
