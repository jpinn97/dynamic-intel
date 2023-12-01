package com.dynamicintel;

import com.dynamicintel.Main.MatrixFactory;

public class Task1 {
    public static void main(String[] args) {

        int size = 1000;
        MatrixFactory factory = new MatrixFactory(1234); // Factory pattern to produce matrices
        int[][] matrixA = factory.createRandomMatrix(size, size);
        int[][] matrixB = factory.createRandomMatrix(size, size);
        int[][] matrixC = factory.createRandomMatrix(size, size);
        int[][] matrixD = factory.createRandomMatrix(size, size);

        Main.writeToFile(matrixA, "matrixA_task1.txt"); // Write to file using the method in Main
        Main.writeToFile(matrixB, "matrixB_task1.txt");
        Main.writeToFile(matrixC, "matrixC_task1.txt");
        Main.writeToFile(matrixD, "matrixD_task1.txt");

        int[][] matrixAB = multiply(matrixA, matrixB);

        Main.writeToFile(matrixAB, "matrixAB_task1.txt");

        int[][] matrixABC = multiply(matrixAB, matrixC);

        Main.writeToFile(matrixABC, "matrixABC_task1.txt");

        int[][] matrixABCD = multiply(matrixABC, matrixD);

        Main.writeToFile(matrixABCD, "matrixABCD_task1.txt");
    }

    public static int[][] multiply(int[][] matrixA, int[][] matrixB) { // multiply two matrices
        int size = matrixA.length;

        int[][] resultMatrix = new int[size][size];
        for (int i = 0; i < size; i++) { // row
            for (int j = 0; j < size; j++) { // column
                for (int k = 0; k < size; k++) { // multiply
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return resultMatrix;
    }
}