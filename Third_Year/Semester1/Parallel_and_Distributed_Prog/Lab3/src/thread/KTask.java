package thread;

import model.Matrix;
import model.Pair;

public class KTask extends MatrixTask {
    public KTask(int iStart, int jStart, int sizeOfTask, Matrix first, Matrix second, Matrix result, int k) {
        super(iStart, jStart, sizeOfTask, first, second, result, k);
    }

    @Override
    public void computeElements() {
        int i = iStart;
        int j = jStart;
        int size = sizeOfTask;
        while (size > 0 && i < result.rows) {
            pairs.add(new Pair<>(i, j));
            size--;
            i += (j + k) / result.cols;
            j = (j + k) % result.cols;
        }
    }
}
