import java.util.Scanner;

public class MatrixOutput {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("1. Addition \n2. Subtraction \n3. Multiplication \n4. Transpose \n5. Determinant" +
                "\n6. Inverse (2x2) \n7. Find Eigenvalues");
        System.out.print("Enter value of your desired method: ");

        int value = input.nextInt();

        System.out.println();

        if (value == 1) {
            System.out.printf("Enter matrix a%n");
            double[][] a = inputMatrix();

            System.out.printf("Enter matrix b%n");
            double[][] b = inputMatrix();

            double[][] answer = Matrices.matrixAddition(a, b);

            System.out.println("Answer: ");
            System.out.print(Matrices.toString(answer));
        } else if (value == 2) {
            System.out.printf("Enter matrix a%n");
            double[][] a = inputMatrix();

            System.out.printf("Enter matrix b%n");
            double[][] b = inputMatrix();

            double[][] answer = Matrices.matrixSubtraction(a, b);

            System.out.println("Answer: ");
            System.out.print(Matrices.toString(answer));
        } else if (value == 3) {
            System.out.print("1. Matrix-Matrix Multiplication\n2. Scalar Matrix Multiplication");
            System.out.print("Enter value of your desired method: ");

            int multiplicationValue = input.nextInt();

            if (multiplicationValue == 1) {
                System.out.printf("Enter matrix a%n");
                double[][] a = inputMatrix();

                System.out.printf("Enter matrix b%n");
                double[][] b = inputMatrix();

                double[][] answer = Matrices.matrixMultiplication(a, b);

                System.out.println("Answer: ");
                System.out.print(Matrices.toString(answer));
            } else if (multiplicationValue == 2) {
                System.out.printf("Enter matrix%n");
                double[][] a = inputMatrix();

                System.out.printf("Enter scalar value: %n");
                double b = input.nextDouble();

                double[][] answer = Matrices.matrixScalarMultiplication(a, b);

                System.out.println("Answer: ");
                System.out.print(Matrices.toString(answer));
            } else {
                throw new IllegalArgumentException("Not a valid input");
            }
        }

        else if (value == 4) {
            System.out.printf("Enter matrix%n");
            double[][] a = inputMatrix();

            double[][] answer = Matrices.matrixTranspose(a);

            System.out.println("Answer: ");
            System.out.print(Matrices.toString(answer));
        }

        else if (value == 5) {
            System.out.printf("Enter matrix%n");
            double[][] a = inputMatrix();

            double answer = Matrices.matrixDeterminant(a);

            System.out.printf("Answer: %.2f", answer);
        }

        else if (value == 6) {
            System.out.printf("Enter matrix%n");
            double[][] a = inputMatrix();

            double[][] answer = Matrices.matrixInverseTwoByTwo(a);

            System.out.println("Answer: ");
            System.out.print(Matrices.toString(answer));
        }
        else if (value == 7) {
            System.out.printf("Enter matrix%n");
            double[][] a = inputMatrix();

            String[][] answer = Matrices.findEigenvalues(a);

            System.out.println("Answer: ");
            System.out.print(Matrices.toString(answer));
        }
        else {
            throw new IllegalArgumentException("Not a valid input type");
        }
    }



    public static double[][] inputMatrix() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = input.nextInt();
        System.out.print("Enter the number of columns: ");
        int columns = input.nextInt();

        double[][] matrix = new double[rows][columns];

        System.out.println("Enter the elements of the matrix row by row:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("Element [%d][%d]: ", i, j);
                matrix[i][j] = input.nextInt();
            }
        }

        return matrix;
    }
}
