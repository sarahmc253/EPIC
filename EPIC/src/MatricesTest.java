public class MatricesTest {
    public static void main(String[] args) {
        double[][] a = {{1, 0}, {0, 1}};
        double[][] b = {{1, 4}, {5, 2}};
        Matrices matrices = new Matrices();

        int[][] addition = matrices.matrixAddition(a, b);
        System.out.println("Addition:");
        for (int i = 0; i < addition.length; i++) {
            for (int j = 0; j < addition[i].length; j++) {
                System.out.print(addition[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        int[][] subtraction = matrices.matrixSubtraction(a, b);
        System.out.println("Subtractions:");
        for (int i = 0; i < subtraction.length; i++) {
            for (int j = 0; j < subtraction[i].length; j++) {
                System.out.print(subtraction[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        int[][] multiplication = matrices.matrixMultiplication(a, b);
        System.out.println("Multiplication:");
        for (int i = 0; i < multiplication.length; i++) {
            for (int j = 0; j < multiplication[i].length; j++) {
                System.out.print(multiplication[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        int[][] transpose = matrices.matrixTranspose(b);
        System.out.println("Transpose:");
        for (int i = 0; i < transpose.length; i++) {
            for (int j = 0; j < transpose[i].length; j++) {
                System.out.print(transpose[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        int determinant = matrices.matrixDeterminant(b);
        System.out.printf("Determinant:%n%d%n", determinant);

        double[][] inverse = matrices.matrixInverseTwoByTwo(b);
        System.out.println("Inverse");
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j < inverse[i].length; j++) {
                System.out.print(inverse[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
