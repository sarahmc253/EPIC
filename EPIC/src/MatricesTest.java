public class MatricesTest {
    public static void main(String[] args) {
        int[][] a = {{1, 0}, {0, 1}};
        int[][] b = {{2, 2}, {2, 2}};
        Matrices matrices = new Matrices();

        int[][] addition = matrices.matrixAddition(a, b);
        for(int i = 0; i < addition.length; i++) {
            for(int j = 0; j < addition[i].length; j++) {
                System.out.print(addition[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        int[][] subtraction = matrices.matrixSubtraction(a, b);
        for(int i = 0; i < subtraction.length; i++) {
            for(int j = 0; j < subtraction[i].length; j++) {
                System.out.print(subtraction[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        int[][] multiplication = matrices.matrixMultiplication(a, b);
        for(int i = 0; i < multiplication.length; i++) {
            for(int j = 0; j < multiplication[i].length; j++) {
                System.out.print(multiplication[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
