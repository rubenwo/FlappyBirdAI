package NN;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int rows, cols;
    private float[][] data;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new float[rows][cols];
    }

    public Matrix copy() {
        Matrix m = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                m.data[i][j] = this.data[i][j];
            }
        }
        return m;
    }

    public Matrix map(MatrixMappingFunction mappingFunction) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                float val = this.data[i][j];
                this.data[i][j] = mappingFunction.func(val, i, j);
            }
        }
        return this;
    }

    public static Matrix fromArray(float[] data) {
        return new Matrix(data.length, 1).map((e, i, j) -> data[i]);
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B.");
            return null;
        }
        return new Matrix(a.rows, a.cols)
                .map(((e, i, j) -> a.data[i][j] - b.data[i][j]));
    }

    public float[] toArray() {
        List<Float> arr = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                arr.add(this.data[i][j]);
            }
        }
        float[] result = new float[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            result[i] = arr.get(i);
        }
        return result;
    }

    public Matrix randomize() {
        return this.map((e, i, j) -> (float) Math.random() * 2 - 1);
    }

    public Matrix add(Matrix n) {
        if (this.rows != n.rows || this.cols != n.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B.");
            return null;
        }
        return this.map(((e, i, j) -> e + n.data[i][j]));
    }

    public static Matrix transpose(Matrix matrix) {
        return new Matrix(matrix.cols, matrix.rows).map(((e, i, j) -> matrix.data[j][i]));
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            System.out.println("Columns of A must match rows of B.");
            return null;
        }

        return new Matrix(a.rows, b.cols).map(((e, i, j) -> {
            int sum = 0;
            for (int k = 0; k < a.cols; k++) {
                sum += a.data[i][k] * b.data[k][j];
            }
            return sum;
        }));
    }

    public Matrix multiply(Matrix n) {
        if (this.rows != n.rows || this.cols != n.cols) {
            System.out.println("Columns and Rows of A must match Columns and Rows of B.");
            return null;
        }
        return this.map(((e, i, j) -> e * n.data[i][j]));
    }

    public Matrix multiply(float n) {
        return this.map((e, i, j) -> e * n);
    }

    public static Matrix map(Matrix matrix, MatrixMappingFunction mappingFunction) {
        return new Matrix(matrix.rows, matrix.cols)
                .map(((e, i, j) -> mappingFunction.func(matrix.data[i][j], i, j)));
    }

    public Matrix print() {
        System.out.println(this.data);
        return this;
    }
}
