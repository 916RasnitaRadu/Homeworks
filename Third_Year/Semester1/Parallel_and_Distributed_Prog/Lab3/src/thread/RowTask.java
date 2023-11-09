package thread;

import model.Matrix;
import model.Pair;

public class RowTask extends MatrixTask {

    public RowTask(int iStart, int jStart, int sizeOfTask, Matrix first, Matrix second, Matrix result) {
        super(iStart, jStart, sizeOfTask, first, second, result);
    }

    @Override
    public void computeElements() {
        int i = iStart;
        int j = jStart;
        int size = sizeOfTask;
        while (size > 0 && i < result.rows && j < result.cols) {
            pairs.add(new Pair<>(i, j));
            j++;
            size--;
            if (j == result.rows) {
                j = 0;
                i++;
            }
        }
    }
}
