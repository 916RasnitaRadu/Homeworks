//import grammar.Grammar;
//import parser.Parser;
//import syntax_tree.Tree;
//import utils.Pair;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.io.IOException;
//import java.util.Map;
//import java.util.Scanner;
//
//public class Main {
//    private static List<String> readSequence(String file) {
//        List<String> sequence = new ArrayList<>();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String symbol = line;
//                if (line.contains("-")) {
//                    symbol = List.of(line.split("-")).get(0);
//                }
//                sequence.add(symbol.trim());
//            }
//        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return sequence;
//    }
//    public static void main(String[] args) throws Exception {
//        Scanner scanner = new Scanner(System.in);
//
//        Test test = new Test();
//     //   test.testAll();
//        System.out.println("Tests passed!");
//
//        Grammar grammar;
//        Parser parser;
//        try {
//            while (true) {
//                System.out.println("Enter an option: ");
//                System.out.println("1. g1");
//                System.out.println("2. g2");
//                System.out.println("x. Exit");
//                System.out.print("> ");
//                String option = scanner.nextLine();
//
//                switch (option) {
//                    case "1":
//                        grammar = Grammar.fromFile("g1.txt");
//                        parser = new Parser(grammar);
//                        System.out.println(grammar);
//                        System.out.println("First: " + parser.getFirstSet());
//                        System.out.println("Follow: " + parser.getFollowSet());
//                        for (Map.Entry<String, String> k : parser.getTable().keySet()) {
//                            System.out.println("<" + k.getKey() + " ; " + k.getValue() + ">" + " -> " + parser.getTable().get(k));
//                        }
//                        String result = parser.evaluateSequence(readSequence("seq.txt"));
//                        if (result == null) {
//                            System.err.println("Sequence not accepted");
//                        }
//                        else {
//                            System.out.println(result);
//                            Tree tree = new Tree(grammar);
//                            tree.build(List.of(result.split(" ")));
//                            tree.printTable();
//                        }
//                        break;
//                    case "2":
//                        grammar = Grammar.fromFile("g4.txt");
//                        parser = new Parser(grammar);
//                        System.out.println(grammar);
//                        System.out.println("First: " + parser.getFirstSet());
//                        System.out.println("Follow: " + parser.getFollowSet());
//                        for (Map.Entry<String, String> k : parser.getTable().keySet()) {
//                            System.out.println("<" + k.getKey() + " ; " + k.getValue() + ">" + " -> " + parser.getTable().get(k));
//                        }
//                        String result2 = parser.evaluateSequence(readSequence("pifp1.txt"));
//                        if (result2 == null) {
//                            System.err.println("Sequence not accepted");
//                        }
//                        else {
//                            System.out.println(result2);
//                            Tree tree = new Tree(grammar);
//                            tree.build(List.of(result2.split(" ")));
//                            tree.printTable();
//                        }
//                        break;
//                    case "x":
//                        System.out.println("Exiting...");
//                        scanner.close();
//                        System.exit(0);
//                        break;
//                    default:
//                        System.out.println("Invalid option!");
//                        break;
//
//                }
//            }
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//    }
//}