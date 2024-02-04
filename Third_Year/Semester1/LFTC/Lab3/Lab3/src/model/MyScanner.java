package model;

import exceptions.ScannerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public class MyScanner {
    private final List<String> reservedWords = new ArrayList<>(
            List.of("if", "else", "while", "for", "integer", "string", "char", "read", "print", "return", "start", "arr")
    ); // defined reserved words

    private final List<String> otherTokens = new ArrayList<>(
            List.of("[","]","(", ")", "{", "}", " ", "\n", ",", ";","+", "-", "*", "/", "%", "==","!=", ">", "<", "<=", ">=", "=")
    ); // defined separators & operators


    private final Pattern regexInteger = Pattern.compile("^([+-]?[1-9][0-9]*|0)");

    private final Pattern regexString = Pattern.compile("^\"[a-zA-z0-9_ ?:*^+=.!]*\"");

    private final Pattern regexIdentifier = Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)");

    private String program; // the input program as a string (that will be read)
    private SymbolTable symbolTable;

    private List<Pair<String, Pair<Integer, Integer>>> programInternalForm;

    private int index = 0; // the current index (character) in the program

    private int currentLine = 1; // the current line in the program

    public MyScanner() {
        this.programInternalForm = new ArrayList<>();
        this.symbolTable = new SymbolTable(30);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public List<Pair<String, Pair<Integer, Integer>>> getProgramInternalForm() {
        return programInternalForm;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void scan(String fileName) {
        try {
            Path file = Path.of("D:\\Faculta\\LFTC\\SEM5-FLCD\\Lab3\\Lab3\\src\\input\\" + fileName);
            setProgram(Files.readString(file));

            index = 0;
            currentLine = 1;
            programInternalForm = new ArrayList<>();
            symbolTable = new SymbolTable(30);

            while (index < this.program.length()) {
                // scan tokens
                nextToken();
            }

            // write output pif
            FileWriter fileWriter = new FileWriter("PIF" + fileName.replace(".vtm", ".out"));
            for (var pair : programInternalForm) {
                fileWriter.write(
                        pair.getFirst() + " - " + pair.getSecond() + "\n"
                );
            }
            fileWriter.close();

            // write output symbol table
            fileWriter = new FileWriter("ST" + fileName.replace(".vtm", ".out"));
            fileWriter.write(symbolTable.toString());
            fileWriter.close();
            System.out.println("lexically correct!");

        }
        catch (IOException | ScannerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void nextToken() throws ScannerException {
        treatSpaces();

        if (index == program.length()) {
            return;
        }
        if (treatIntegerConstant()) {
            return;
        }
        if (treatStringConstant()) {
            return;
        }
        if (treatIdentifier()) {
            return;
        }
        if (treatFromTokenList()) {
            return;
        }
        throw new ScannerException("lexical error: invalid token at line " + currentLine + ", index " + index);
    }

    /*
        Function that skips spaces in the program string, i.e. the current index is increasing if it encounters a space
        at the beginning of the program
        also if there are any new lines it increases the current line
     */
    private void treatSpaces() {
        while (index < program.length() && Character.isWhitespace(program.charAt(index))) {
            if (program.charAt(index) == '\n') {
                currentLine++;
            }
            index++;
        }
    }

    private boolean treatIntegerConstant() {

        var matcher = regexInteger.matcher(program.substring(index));

        if (!matcher.find()) { return false; }

        // case when there are letters after the digits
        if (Pattern.compile("^([+-]?[1-9][0-9]*|0)[a-zA-Z_]").matcher(program.substring(index)).find()) {
            return false;
        }

        // we take the matched sequence and increase the index
        var intConst = matcher.group(1);
        index += intConst.length();
        Pair<Integer,Integer> position;
        try {
            position = symbolTable.addIntConstant(Integer.parseInt(intConst)); // we add it in the symbol table
        }
        catch (Exception e) {
            position = symbolTable.getPosIntConstant(Integer.parseInt(intConst)); // if it's already there we return the position
        }
        programInternalForm.add(new Pair<>("const", position));
        return true;
    }

    private boolean treatStringConstant() {
        var matcher = regexString.matcher(program.substring(index));

        if (!matcher.find()) {
            return false;
        }
        var stringConst = matcher.group(0);
        index += stringConst.length();
        Pair<Integer, Integer> position;

        try {
             position = symbolTable.addStringConstant(stringConst);
        }
        catch (Exception e) {
            position = symbolTable.getPosStringConstant(stringConst);
        }
        programInternalForm.add(new Pair<>("const", position));
        return true;
    }

    private boolean treatFromTokenList() {
        String token = program.substring(index).split(" ")[0]; // we take the next possible token
        for (String word : reservedWords) {
            if (token.startsWith(word)) {
                var regex = "^[a-zA-Z0-9]*" + word + "[a-zA-Z0-9]+";
                if (Pattern.compile(regex).matcher(token).find()) {
                    return false;
                }

                index += word.length();
                programInternalForm.add(new Pair<>(word, new Pair<>(-1,-1)));
                return true;
            }
        }

        for (String operator : otherTokens) {
            if (Objects.equals(token,operator)) {
                index += token.length();

                programInternalForm.add(new Pair<>(token, new Pair<>(-1,-1)));
                return true;
            }
            else if (token.startsWith(operator)) {
                index += operator.length();
                programInternalForm.add(new Pair<>(operator, new Pair<>(-1,-1)));
                return true;
            }
        }
        return false;
    }

    private boolean treatIdentifier() {
        var matcher = regexIdentifier.matcher(program.substring(index));

        if (!matcher.find()) { return false; }

        String identifier = matcher.group(1);

      //  if (reservedWords.contains(identifier)) { return false; }

        index += identifier.length();
        Pair<Integer,Integer> position;
        try {
            position = symbolTable.addIdentifier(identifier);
        }
        catch (Exception e) {
            position = symbolTable.getPosIdentifier(identifier);
        }

        programInternalForm.add(new Pair<>("id",position));
        return true;
    }


}
