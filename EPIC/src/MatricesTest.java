import java.util.Scanner;

public class MatricesTest {
    public static void main(String[] args) {
        double[][] b = {{3, 2, 1}, {4, 2, 1}, {5, 2, 1}};
        double[][] a = {{3, 2, 1}, {4, 2, 1}, {5, 2, 1}};

        Matrices matrices = new Matrices();


        double[][] multiplication = matrices.matrixMultiplication(a,
                b);
        System.out.println("Multiplication:");
        System.out.print(matrices.toString(multiplication));
    }
}
