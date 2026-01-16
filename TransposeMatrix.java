public class TransposeMatrix {
    public static void main(String[] args) {
        int[][] A = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int rows = A.length;
        int cols = A[0].length;
        int[][] T = new int[cols][rows];

        // Transpose logic
        for (int c = 0; c < cols; ++c) {
            for (int r = 0; r < rows; ++r) {
                T [c][r] = A[r][c];
            }
        }

        // Print transposed matrix
        System.out.println("Transposed Matrix:");
        for (int c = 0; c <T.length;c++) {
            for (int r = 0; r <T[0].length ; ++r) {
                System.out.print(T[c][r] + " ");
            }
            System.out.println();
        }
    }
}
