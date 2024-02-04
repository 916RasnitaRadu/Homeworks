package model;

public class SymbolTable {
    private final int size;
    private HashTable<String> identifierTable;
    private HashTable<String> stringConstantTable;
    private HashTable<Integer> intConstantTable;

    public SymbolTable(int size) {
        this.size = size;
        this.identifierTable = new HashTable<>(size);
        this.stringConstantTable = new HashTable<>(size);
        this.intConstantTable = new HashTable<>(size);
    }

    public Pair<Integer,Integer> addIdentifier(String key) throws Exception {
        return identifierTable.add(key);
    }

    public Pair<Integer,Integer> addStringConstant(String constant) throws Exception {
        return stringConstantTable.add(constant);
    }

    public Pair<Integer,Integer> addIntConstant(Integer constant) throws Exception {
        return intConstantTable.add(constant);
    }

    public boolean hasIdentifier(String key) {
        return identifierTable.contains(key);
    }

    public boolean hasStringConstant(String constant) {
        return stringConstantTable.contains(constant);
    }

    public boolean hasIntConstant(Integer constant) {
        return intConstantTable.contains(constant);
    }

    public Pair<Integer,Integer> getPosIdentifier(String key) {
        return identifierTable.getPosition(key);
    }

    public Pair<Integer,Integer> getPosStringConstant(String constant) {
        return stringConstantTable.getPosition(constant);
    }

    public Pair<Integer,Integer> getPosIntConstant(Integer constant) {
        return intConstantTable.getPosition(constant);
    }




    @Override
    public String toString() {
        return "SymbolTable{" +
                "size=" + size +
                "\n identifierTable=" + identifierTable +
                "\n stringConstantTable=" + stringConstantTable +
                "\n intConstantTable=" + intConstantTable +
                '}';
    }
}
