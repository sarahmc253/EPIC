public class MatricesTest {
    public static void main(String[] args) {
        int[][] identityMatrix = {{1, 0}, {0, 1}};
        int[][] a = {{2, 2}, {2, 2}};
        Matrices matrices = new Matrices();

        int[][] c = matrices.matrixSubtraction(identityMatrix, a);

        for(int i = 0; i < c.length; i++) {
            for(int j = 0; j < c[i].length; j++) {
                System.out.print(c[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
