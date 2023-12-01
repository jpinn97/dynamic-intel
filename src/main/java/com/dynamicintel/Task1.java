package com.dynamicintel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Task1 {
    public static void main(String[] args) {
        int size = 1000;
        int[][] matrixA = new int[size][size];
        int[][] matrixB = new int[size][size];
        int[][] matrixC = new int[size][size];
        int[][] matrixD = new int[size][size];

        Random random = new Random();
        for (int i = 0; i < size; i++) { // Initialize the matrices A and B to random values
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = random.nextInt();
                matrixB[i][j] = random.nextInt();
                matrixC[i][j] = random.nextInt();
                matrixD[i][j] = random.nextInt();
            }
        }

        writeToFile(matrixA, "matrixA_raw.txt");
        writeToFile(matrixB, "matrixB_raw.txt");
        writeToFile(matrixC, "matrixC_raw.txt");
        writeToFile(matrixD, "matrixD_raw.txt");

        int[][] matrixAB = multiply(matrixA, matrixB);

        writeToFile(matrixAB, "matrixAB_multiplied.txt");

        int[][] matrixABC = multiply(matrixAB, matrixC);

        writeToFile(matrixABC, "matrixABC_multiplied.txt");

        int[][] matrixABCD = multiply(matrixABC, matrixD);

        writeToFile(matrixABCD, "matrixABCD_multiplied.txt");
    }

    public static int[][] multiply(int[][] matrixA, int[][] matrixB) {
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

    public static void writeToFile(int[][] matrix, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(matrix[i][j] + ",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}