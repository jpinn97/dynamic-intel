package com.dynamicintel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.dynamicintel.Main.MatrixFactory;

public class Task2 {
    public static void main(String[] args) {

        int size = 1000;
        MatrixFactory factory = new MatrixFactory(1234); // Factory pattern to produce matrices.
        int[][] matrixA = factory.createRandomMatrix(size, size);
        int[][] matrixB = factory.createRandomMatrix(size, size);
        int[][] matrixC = factory.createRandomMatrix(size, size);
        int[][] matrixD = factory.createRandomMatrix(size, size);

        Main.writeToFile(matrixA, "matrixA_task2.txt"); // Write to file using the method in Main.
        Main.writeToFile(matrixB, "matrixB_task2.txt");
        Main.writeToFile(matrixC, "matrixC_task2.txt");
        Main.writeToFile(matrixD, "matrixD_task2.txt");

        int numberOfThreads = 8; // Specifies thread count

        int[][] matrixAB = multiplyMatricesMultithreaded(matrixA, matrixB, numberOfThreads);

        Main.writeToFile(matrixAB, "matrixAB_task2.txt");

        int[][] matrixABC = multiplyMatricesMultithreaded(matrixAB, matrixC, numberOfThreads);

        Main.writeToFile(matrixABC, "matrixABC_task2.txt");

        int[][] matrixABCD = multiplyMatricesMultithreaded(matrixABC, matrixD, numberOfThreads);

        Main.writeToFile(matrixABCD, "matrixABCD_task2.txt");

        int[][] goldenStandardResult = readMatrix();

        if (compareMatrix(matrixABCD, goldenStandardResult)) {
            System.out.println("Matrices are identical");
        } else {
            System.out.println("Matrices are not identical");
        }
    }

    private static boolean compareMatrix(int[][] matrixABCD, int[][] goldenStandardResult) {
        for (int i = 0; i < matrixABCD.length; i++) {
            for (int j = 0; j < matrixABCD[i].length; j++) {
                if (matrixABCD[i][j] - goldenStandardResult[i][j] != 0) {
                    return false; // Non-zero result means matrices are not identical
                }
            }
        }
        return true;
    }

    private static int[][] readMatrix() {
        Scanner sc;
        try {
            sc = new Scanner(
                    new BufferedReader(new FileReader("matrixABCD_task1.txt"))); // Read from file using scanner. Must
                                                                                 // have try catch due to
                                                                                 // java.io.FileNotFoundException

            int[][] goldStandardResult = new int[1000][1000];
            while (sc.hasNextLine()) {
                for (int i = 0; i < goldStandardResult.length; i++) {
                    String[] line = sc.nextLine().trim().split(",");
                    for (int j = 0; j < line.length; j++) {
                        goldStandardResult[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
            sc.close();
            return goldStandardResult;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[][] multiplyMatricesMultithreaded(int[][] matrixA, int[][] matrixB, int numberOfThreads) {
        int rowCount = matrixA.length;
        int[][] result = new int[rowCount][matrixB[0].length];

        int rowsPerThread = rowCount / numberOfThreads;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            int startRow = i * rowsPerThread;
            int endRow = (i == numberOfThreads - 1) ? rowCount : startRow + rowsPerThread;
            threads[i] = new Thread(new MatrixMultiplicationTask(matrixA, matrixB, result, startRow, endRow));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) { // Wait for all threads to finish.
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    static class MatrixMultiplicationTask implements Runnable { // Runnable for multitheading, more flexible than
                                                                // extending Thread.
        private final int[][] matrixA;
        private final int[][] matrixB;
        private final int[][] resultMatrix;
        private final int startRow;
        private final int endRow;

        public MatrixMultiplicationTask(int[][] matrixA, int[][] matrixB, int[][] result, int startRow, int endRow) { // Constructor
                                                                                                                      // matrix
                                                                                                                      // multiplication
                                                                                                                      // task.
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.resultMatrix = result;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override // Override the abstract run method.
        public void run() {
            for (int i = startRow; i < endRow; i++) { // row
                for (int j = 0; j < matrixB[0].length; j++) { // column
                    for (int k = 0; k < matrixA[0].length; k++) { // multiply
                        resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        }
    }

}