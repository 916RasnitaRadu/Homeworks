import Grammar.Grammar;

import java.io.IOException;
import java.util.*;
import Grammar.Pair;

public class Main {

    public static void printMenuOperations() {
        System.out.println("Choose the next operation: ");
        System.out.println("1. Print grammar");
        System.out.println("2. Print productions for a non-terminal");
        System.out.println("0. Exit");
    }

    public static void printMenuGrammar() {
        System.out.println("Choose grammar file: ");
        System.out.println("1. g1");
        System.out.println("2. g2");
        System.out.println("0. Exit");
    }

    public static void grammar(String file) throws IOException {
        Grammar g = Grammar.fromFile(file);
        String option;
        Scanner scanner = new Scanner(System.in);
        boolean go = true;

        while (go) {
            printMenuOperations();
            System.out.print("> ");
            option = scanner.nextLine();
            switch (option) {
                case "1" -> System.out.println(g);
                case "2" -> {
                    System.out.print("Enter non-terminal: ");
                    String nonterm = scanner.nextLine();
                    List<String> productions = g.getProductionsFor(nonterm);
                    for (String p : productions) {
                        System.out.println(p);
                    }
                }
                case "0" -> go = false;
                default -> System.err.println("Invalid option");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean go = true;
        while (go) {
            printMenuGrammar();
            System.out.print("> ");
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> grammar("g1.txt");
                case "2" -> grammar("g2.txt");
                case "0" -> go = false;
                default -> System.err.println("Invalid option!");
            }
        }
    }
}