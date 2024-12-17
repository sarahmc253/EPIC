public class MatricesTest {
    public static void main(String[] args) {
        double[][] b = {{3, 2, 1}, {4, 2, 1}, {5, 2, 1}};
        double[][] a = {{3, 2, 1}, {4, 2, 1}, {5, 2, 1}};

        double[][] multiplication = Matrices.matrixMultiplication(a, b);

        System.out.println("Multiplication:");
        System.out.print(Matrices.toString(multiplication));
    }
}
