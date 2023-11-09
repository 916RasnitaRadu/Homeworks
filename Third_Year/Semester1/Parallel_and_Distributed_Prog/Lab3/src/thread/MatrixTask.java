package thread;

import model.Matrix;
import model.Pair;
import model.exception.MatrixException;
import utils.Utils;

import java.util.*;

abstract public class MatrixTask extends Thread {
    public List<Pair<Integer, Integer>> pairs;
    public final int iStart, jStart, sizeOfTask;
    public final Matrix first;
    public final Matrix second;
    public final Matrix result;
    public int k;

    public abstract void computeElements();

    public MatrixTask(int iStart, int jStart, int sizeOfTask, Matrix first, Matrix second, Matrix result, int k) {
        this.iStart = iStart;
        this.jStart = jStart;
        this.sizeOfTask = sizeOfTask;
        this.first = first;
        this.second = second;
        this.result = result;
        this.k = k;
        this.pairs = new ArrayList<>();
        computeElements();
    }

    public MatrixTask(int iStart, int jStart, int sizeOfTask, Matrix first, Matrix second, Matrix result) {
        this.iStart = iStart;
        this.jStart = jStart;
        this.sizeOfTask = sizeOfTask;
        this.first = first;
        this.second = second;
        this.result = result;
        this.pairs = new ArrayList<>();
        computeElements();
    }

    @Override
    public void run() {
        for (Pair<Integer,Integer> p : pairs) {
            int i = p.first;
            int j = p.second;
            try {
                result.set(i, j, Utils.buildElement(first,second,i,j));
            } catch (MatrixException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
