public class MatricesTest {
    public static void main(String[] args) {
        double[][] a = {{0.8, 0.3,}, {0.2, 0.7,}};
        double[][] b = {{3}, {3}, {-6}};

        String[][] eigenvalues = Matrices.findEigenvalues(a);

        System.out.println("X:");
        for (String[] string : eigenvalues) {
            for (String string1 : string) {
                System.out.print(string1);
                System.out.println();
            }
        }
    }
}
