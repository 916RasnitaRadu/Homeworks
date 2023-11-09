package utils;

import model.Matrix;
import model.exception.MatrixException;
import thread.ColumnTask;
import thread.KTask;
import thread.MatrixTask;
import thread.RowTask;

public class Utils {

    public static int buildElement(Matrix a, Matrix b, int i, int j) throws MatrixException {
        if (i < a.rows && j < b.cols) {
            int elem = 0;
            for (int k = 0; k < a.rows; k++) {
                elem += a.get(i, k) * b.get(k, j);
            }
            return elem;
        }
        throw new MatrixException("Row/column out of bounds!");
    }

    public static MatrixTask initRowThread(int index, Matrix a, Matrix b, Matrix c, int nrThreads) {
        int resultSize = c.rows * c.cols;
        int count = resultSize / nrThreads;

        int iStart = count * index / c.rows;
        int jStart = count * index % c.rows;

        if (index == nrThreads - 1) {
            count += resultSize % nrThreads;
        }

        return new RowTask(iStart, jStart, count, a, b, c);
    }

    public static MatrixTask initColThread(int index, Matrix a, Matrix b, Matrix c, int nrThreads) {
        int resultSize = c.rows * c.cols;
        int count = resultSize / nrThreads;

        int iStart = count * index % c.rows;
        int jStart = count * index / c.rows;

        if (index == nrThreads - 1) {
            count += resultSize % nrThreads;
        }

        return new ColumnTask(iStart,jStart, count, a, b, c);
    }

    public static MatrixTask initKThread(int index, Matrix a, Matrix b, Matrix c, int nrThreads) {
        int resultSize = c.rows * c.cols;
        int count = resultSize / nrThreads;

        if (index < resultSize % nrThreads) {
            count++;
        }

        int iStart = index / c.cols;
        int jStart = index % c.cols;

        return new KTask(iStart, jStart, count, a, b, c, nrThreads);
    }
}
