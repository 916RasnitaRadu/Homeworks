package model;

import java.util.Random;

public class Matrix {
    public final int rows;
    public final int cols;

    public int[][] matrix;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows][cols];
    }

    public int get(int row, int col) {
        return matrix[row][col];
    }

    public void set(int row, int col, int val) {
        this.matrix[row][col] = val;
    }

    public void populate() {
        Random random = new Random();
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
                matrix[i][j] = 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
