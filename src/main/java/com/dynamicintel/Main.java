package com.dynamicintel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Task1.main(args);

        System.err.println("Task 1 Time taken: " + (System.currentTimeMillis() - startTime) + "ms");

        startTime = System.currentTimeMillis();

        Task2.main(args);

        System.err.println("Task 2 Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void writeToFile(int[][] matrix, String filename) { // Write matrix to file
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

    static class MatrixFactory { // Factory pattern to produce matrices

        private Random random;

        public MatrixFactory(long seed) { // Constructor with seed for reproducability of random matrices
            random = new Random(seed);
        }

        public int[][] createRandomMatrix(int rows, int columns) {
            int[][] matrix = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = random.nextInt();
                }
            }
            return matrix;
        }
    }

}