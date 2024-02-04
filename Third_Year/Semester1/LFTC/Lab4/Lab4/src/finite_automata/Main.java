package finite_automata;

import java.util.Objects;
import java.util.Scanner;
import java.util.List;

public class Main {


    public static void printMenu() {
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print output states");
        System.out.println("4. Print initial state");
        System.out.println("5. Print transitions");
        System.out.println("6. Check word");
        System.out.println("7. Get matching substring");
        System.out.println("0. Exit");
        System.out.print("> ");
    }

    public static void main(String[] args) {
        FiniteAutomata fa = new FiniteAutomata("src/finite_automata/resources/identifier.in");
        Scanner scanner = new Scanner(System.in);
        boolean ok = true;
        while (ok) {
            printMenu();
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    fa.printStates();
                    break;
                case 2:
                    fa.printAlphabet();
                    break;
                case 3:
                    fa.printOutputStates();
                    break;
                case 4:
                    fa.printInitialState();
                    break;
                case 5:
                    fa.printTransitions();
                    break;
                case 6:
                    String word = scanner.nextLine();
                    System.out.println(fa.checkSequence(word));
                    break;
                case 7:
                    word = scanner.nextLine();
                    String accepted = fa.getNextAcceptedSequence(word);
                    if (Objects.equals(accepted, "")) {
                        System.out.println("No matching substring");
                    }
                    else {
                        System.out.println(accepted);
                    }
                    break;
                case 0:
                    ok = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
