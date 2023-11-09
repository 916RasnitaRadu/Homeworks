import model.HashTable;
import model.Pair;
import model.SymbolTable;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // TESTS FOR HASHTABLE

        System.out.println("==== TESTS FOR HASHTABLE ====");
        HashTable<String> stringTable = new HashTable<>(10);
        HashTable<Integer> integerHashTable = new HashTable<>(10);
        try {
            Pair<Integer, Integer> result1 = stringTable.add("apple");
            Pair<Integer, Integer> result2 = stringTable.add("banana");
            Pair<Integer, Integer> result3 = integerHashTable.add(42);
            Pair<Integer, Integer> result4 = integerHashTable.add(17);

            assert result1.equals(new Pair<>(0, 0)) : "Test 1 failed";
            assert result2.equals(new Pair<>(0, 1)) : "Test 2 failed";
            assert result3.equals(new Pair<>(1, 0)) : "Test 3 failed";
            assert result4.equals(new Pair<>(1, 1)) : "Test 4 failed";
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("ADD tests passed!");

        // Test adding a duplicate element
        try {
            stringTable.add("apple");
            assert false : "Test 5 failed"; // Should throw an exception
        } catch (Exception e) {
            assert e.getMessage().equals("Key: apple is already taken!!") : "Test 5 failed";
        }

        // Test checking if elements exist
        assert stringTable.contains("apple") : "Test 6 failed";
        assert integerHashTable.contains(42) : "Test 7 failed";
        assert !stringTable.contains("cherry") : "Test 8 failed";
        assert !integerHashTable.contains(99) : "Test 9 failed";

        // Test getting the position of elements
        Pair<Integer, Integer> position1 = stringTable.getPosition("apple");
        Pair<Integer, Integer> position2 = stringTable.getPosition("banana");
        Pair<Integer, Integer> position3 = stringTable.getPosition("cherry");

        assert position1.equals(new Pair<>(0, 0)) : "Test 10 failed";
        assert position2.equals(new Pair<>(0, 1)) : "Test 11 failed";
        assert position3.equals(new Pair<>(-1, -1)) : "Test 12 failed";
        System.out.println("GET tests passed!");
        System.out.println("All tests passed!");




        // TESTS FOR SYMBOL TABLE
        System.out.println("==== TESTS FOR SYMBOL TABLE ====");

        SymbolTable symbolTable = new SymbolTable(50);
        try {
            System.out.println("IDENTIFIERS");
            System.out.println("c -> " + symbolTable.addIdentifier("c"));
            System.out.println("a -> " + symbolTable.addIdentifier("a"));
            System.out.println("radu -> " + symbolTable.addIdentifier("radu"));
            System.out.println("eu -> " + symbolTable.addIdentifier("eu"));
            System.out.println("=================\n");

            System.out.println("INT CONSTANTS");
            System.out.println("2 -> " + symbolTable.addIntConstant(2));
            System.out.println("12 -> " + symbolTable.addIntConstant(12));
            System.out.println("234 -> " + symbolTable.addIntConstant(234));
            System.out.println("10238 -> " + symbolTable.addIntConstant(10238));
            System.out.println("=================\n");

            System.out.println("STRING CONSTANTS");

            System.out.println("anaaremere -> " + symbolTable.addStringConstant("anaaremere"));
            System.out.println("anotherOne -> " + symbolTable.addStringConstant("anotherOne"));
            System.out.println("DjKhaled -> " + symbolTable.addStringConstant("DjKhaled"));
            System.out.println("euSuntBatman -> " + symbolTable.addStringConstant("euSuntBatman"));
            System.out.println("=================\n");

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(symbolTable);

        try {
            assert symbolTable.getPosStringConstant("anotherOne").equals(new Pair<>(43,0)) : "49 does not have position (43,0)";
            assert symbolTable.getPosIdentifier("radu").equals(new Pair<>(28,0)) : "radu doesn't have position (28,0)";
            assert symbolTable.getPosIntConstant(2).equals(new Pair<>(2,0)) : "2 doesn't have position (2,0)";
        }
        catch (AssertionError e) {
            System.out.println(e.getMessage());
        }

        System.out.println("ALL TEST HAVE PASSED");

    }


}