import java.util.Arrays;

public class MatrixElementWise {
    public static void main(String[] args) {
        int[][] A = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] B = {
            {7, 8, 9},
            {10, 11, 12}
        };

        int rows = A.length;
        int cols = A[0].length;
        int[][] C = new int[rows][cols];

        // Element-wise addition
        for (int c = 0; c < cols; ++c) {
            for (int r = 0; r < rows; ++r) {
                C[r][c] = A[r][c] + B[r][c];
            }
        }

        // Print result matrix
        for (int c = 0; c < cols; ++c) {
            for (int r = 0; r < rows; ++r) {
                System.out.print(C[r][c] + " ");
            }
            System.out.println();
        }
        
    }
}
