public class MaximumSumHourglass {
    public static int maximumSum(int[][] matrix) {
        if (matrix.length < 3 || matrix[0].length < 3) {
            throw new IllegalArgumentException("Matrix must be at least 3x3");
        }
        int best = Integer.MIN_VALUE;
        for (int row = 0; row <= matrix.length - 3; row++) 
            for (int column = 0; column <= matrix[0].length - 3; column++) {
                int sum = matrix[row][column] 
                        + matrix[row][column + 1] + matrix[row][column + 2]
                        + matrix[row + 1][column + 1]
                        + matrix[row + 2][column] + matrix[row + 2][column + 1]
                        + matrix[row + 2][column + 2];
                best = Math.max(best, sum);
            }
        return best;
    }

    public static void main(String[] args) {
        int rows = Integer.parseInt(args[0]);
        int columns = Integer.parseInt(args[1]);
        if (args.length != 2 + rows * columns) 
            throw new IllegalArgumentException("Incorrect matrix size");

        int[][] matrix = new int[rows][columns];
        for (int index = 0; index < rows * columns; index++) {
            matrix[index / columns]
                  [index % columns] = Integer.parseInt(args[index + 2]);
        }
        System.out.println(maximumSum(matrix));
    }
}