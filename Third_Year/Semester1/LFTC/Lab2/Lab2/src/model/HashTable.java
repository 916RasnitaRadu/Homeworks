package model;

import java.util.ArrayList;

public class HashTable<T> {
    private final int size;
    private final ArrayList<ArrayList<T>> table;

    public HashTable(int size) {
        this.size = size;
        this.table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.table.add(new ArrayList<>());
        }
    }

    public int getSize() {
        return size;
    }

    public Pair<Integer, Integer> add(T key) throws Exception {
        int hashValue = getHashValue(key);
        if (!table.get(hashValue).contains(key)) {
            table.get(hashValue).add(key);
            return new Pair<>(hashValue,table.get(hashValue).indexOf(key));
        }
        throw new Exception("Key: " + key + " is already taken!!");
    }

    public Pair<Integer,Integer> getPosition(T item) {
        int position = getHashValue(item);

        if (!table.get(position).isEmpty()) {
            ArrayList<T> elems = this.table.get(position);

            for (int i = 0; i < elems.size(); i++) {
                if (elems.get(i).equals(item)) {
                    return new Pair<Integer, Integer>(position, i);
                }
            }
        }

        return new Pair<Integer,Integer>(-1,-1);
    }

    public boolean contains(T key) {
        int hashValue = getHashValue(key);
        return table.get(hashValue).contains(key);
    }

    @Override
    public String toString() {
        return "HashTable{" +
                "size=" + size +
                ", table=" + table +
                '}';
    }

    private int hash(String key) {
        int sumChars = 0;
        char[] keyCharacters = key.toCharArray();
        for (char c : keyCharacters) {
            sumChars += c;
        }
        return sumChars % size;
    }

    private int hash(int key) {
        return key % size;
    }

    // return the corresponding position in the symbol table according to the key
    private int getHashValue(T key) {
        int hashVal = -1;
        if (key instanceof Integer) {
            hashVal = hash((int) key);
        }
        else if (key instanceof String) {
            hashVal = hash((String) key);
        }
        return hashVal;
    }



}
