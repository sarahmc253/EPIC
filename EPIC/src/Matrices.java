import java.util.Scanner;

public class Matrices {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("1. Addition \n2. Subtraction \n3. Multiplication \n4. Transpose \n5. Determinant" +
                "\n6. Inverse (2x2) \n7. Matrix Linear Equation \n8. Dot Product \n9. Find Eigenvalues");
        System.out.print("Enter value of your desired method: ");

        int value = input.nextInt();

        System.out.println();

        switch (value) {
            case 1:
                System.out.printf("Enter matrix a:%n");
                double[][] a = inputMatrix();

                System.out.printf("Enter matrix b:%n");
                double[][] b = inputMatrix();

                double[][] answer = matrixAddition(a, b);

                System.out.print(toString(answer));
        }

    }

    public static double[][] inputMatrix() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = input.nextInt();
        System.out.printf("%nEnter the number of columns: ");
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

    public static double[][] matrixAddition(double[][] a, double[][] b) { // method to add two matrices

        validateMatrix(a, b);

        // creating a new 2d array to store the result and setting the
        // dimensions to the same as a and b
        double[][] c = new double[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through each row
            for (int j = 0; j < a[0].length; j++) { // iterating through each column in the row
                c[i][j] = a[i][j] + b[i][j]; // adding the elements a and b together and storing the result in c
            }
        }
        return c;
    }

    public static double[][] matrixSubtraction(double[][] a, double[][] b) { // method to subtract two matrices

        validateMatrix(a,b);

        double[][] c = new double[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through each row
            for (int j = 0; j < a[0].length; j++) { // iterating through each column in the row
                c[i][j] = a[i][j] - b[i][j]; // subtracting the elements a and b together and storing the result in c
            }
        }
        return c;
    }

    public static double[][] matrixMultiplication(double[][] a, double[][] b) {

        if (a == null || b == null) {
            throw new IllegalArgumentException("Matrices can't be null");
        }
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("The columns in matrix a must equal the rows in matrix b");
        }

        double[][] c = new double[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through rows
            for (int j = 0; j < b[0].length; j++) { // iterating through columns
                for (int k = 0; k < b.length; k++) { // iterating through shared dimension
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public static double[][] matrixScalarMultiplication(double[][] a, double b) {
        if (a == null) {
            throw new IllegalArgumentException("Matrix can't be null");
        }
        if (!isRectangular(a)) {
            throw new IllegalArgumentException("Matrix must be rectangular.");
        }

        double[][] c = new double[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[i][j] = a[i][j] * b;
            }
        }

        return c;
    }

    public static double[][] matrixTranspose(double[][] matrix) {

        if (matrix == null) {
            throw new IllegalArgumentException("Matrix can't be null.");
        }
        if (!isRectangular(matrix)) {
            throw new IllegalArgumentException("Matrix must be rectangular.");
        }

        double[][] c = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                c[j][i] = matrix[i][j];
            }
        }
        return c;
    }

    public static double matrixDeterminant(double[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix can't be null.");
        }
        if (!isRectangular(matrix)) {
            throw new IllegalArgumentException("Matrix must be rectangular.");
        }
        if (matrix.length != 2 || matrix[0].length != 2) {
            throw new IllegalArgumentException("Matrix must be 2 x 2.");
        }

        double c = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);

        return c;
    }

    public static double[][] matrixInverseTwoByTwo(double[][] matrix) {
        if (matrix.length != 2 || matrix[0].length != 2) {
            throw new IllegalArgumentException("Matrix must be a 2 x 2.");
        }

        double[][] c = new double[2][2];

        double det = matrixDeterminant(matrix);
        if (det == 0) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted.");
        }

        double inverseDet = 1 / det;

        c[0][0] = matrix[1][1] * inverseDet;
        c[0][1] = -matrix[0][1] * inverseDet;
        c[1][0] = -matrix[1][0] * inverseDet;
        c[1][1] = matrix[0][0] * inverseDet;

        return c;
    }

    public static double[][] matrixLinearEquation(double[][] a, double[][] b) {

        try { // try solving using the inverse method for 2x2 matrices
            double[][] inverseA = matrixInverseTwoByTwo(a);

            double[][] x = matrixMultiplication(inverseA, b);

            return x;
        } catch (Exception e) { // falls back to LU factorization for larger matrices

            // declaring matrices for the LU factorization process
            double[][] lowerTriangularMatrix = new double[a.length][a[0].length]; // stores the lower triangular matrix (L)
            double[][] upperTriangularMatrix = new double[a.length][a[0].length]; // stores the upper triangular matrix (U)
            double[][] c = new double[a.length][1]; // intermediate result matrix (c) for solving two systems of equations
            double[][] x = new double[a.length][1]; // solution vector (x)

            // Ax = b
            // A = LU
            // LUx = b
            // Ux = c
            // Lc = b
            // Solve for c
            // Ux = c
            // Solve for x

            // step 1: initialise L as an identity matrix (diagonal elements are 1)
            for (int i = 0; i < a.length; i++) {
                lowerTriangularMatrix[i][i] = 1.0; // setting diagonal elements of L to 1
            }

            // step 2: factorise matrix A into L (lower triangular) and U (upper triangular)
            for (int i = 0; i < a.length; i++) { // looping through rows of matrix A

                // build the upper triangular matrix (U)
                for (int j = i; j < a.length; j++) { // iterating over diagonal and above-diagonal elements of U
                    double sum = 0.0; // variable to accumulate intermediate sums
                    for (int k = 0; k < i; k++) { // multiplying corresponding L and U elements
                        sum += lowerTriangularMatrix[i][k] * upperTriangularMatrix[k][j];
                    }
                    upperTriangularMatrix[i][j] = a[i][j] - sum; // subtracting sum from the corresponding A element
                }

                // build the lower triangular matrix (L)
                for (int j = i + 1; j < a.length; j++) { // iterating over below-diagonal elements of L
                    double sum = 0.0; // variable to accumulate intermediate sums
                    for (int k = 0; k < i; k++) { // multiplying corresponding L and U elements
                        sum += lowerTriangularMatrix[j][k] * upperTriangularMatrix[k][i];
                    }
                    if (upperTriangularMatrix[i][i] == 0) { // prevent division by zero
                        throw new ArithmeticException("Matrix is singular"); // if U has a zero on the diagonal, the matrix is non-invertible
                    }
                    lowerTriangularMatrix[j][i] = (a[j][i] - sum) / upperTriangularMatrix[i][i]; // calculate L element
                }
            }

            // step 3: solve for intermediate vector c in the equation Lc = b using forward substitution
            for (int i = 0; i < a.length; i++) { // loop through each row of L and b
                double sum = 0.0; // accumulate sum of L(i,j) * c(j)
                for (int j = 0; j < i; j++) {
                    sum += lowerTriangularMatrix[i][j] * c[j][0];
                }
                c[i][0] = b[i][0] - sum; // Calculate the current element of c
            }

            // step 4: solve for the solution vector x in the equation Ux = c using backward substitution
            for (int i = a.length - 1; i >= 0; i--) { // start from the last row of U and work upwards
                double sum = 0.0; // accumulate sum of U(i,j) * x(j)
                for (int j = i + 1; j < a.length; j++) {
                    sum += upperTriangularMatrix[i][j] * x[j][0];
                }
                x[i][0] = (c[i][0] - sum) / upperTriangularMatrix[i][i]; // solve for the current x element
            }

            return x;
        }
    }


    public static double dotProduct(double[] a, double[] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Vectors cannot be null.");
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same number of elements.");
        }

        double dotProduct = 0.0;
        for (int i = 0; i < a.length; i++) {
            dotProduct += a[i] * b[i];
        }

        return dotProduct;
    }

    public static String[][] findEigenvalues(double[][] matrix) {
        if (matrix.length != 2 || matrix[0].length != 2) { // size validation
            throw new IllegalArgumentException("Matrix must be 2x2.");
        }

        double a = matrix[0][0]; // extract the matrix elements
        double b = matrix[0][1];
        double c = matrix[1][0];
        double d = matrix[1][1];

        // (a - lambda)(d - lambda) - bc
        // ad + lambda^2 - (d * lambda) - (a* lambda) - bc
        // lambda^2 - (a+d)lambda + (ad - bc)

        double quadraticA = 1; // coefficient of lambda squared
        double quadraticB = -(a+d); // coefficient of lambda
        double quadraticC = (a * d) - (b * c);

        double discriminant = (quadraticB * quadraticB) - (4 * quadraticA * quadraticC);
        if (discriminant < 0) {
            throw new IllegalArgumentException("This method does not handle complex roots");
        }

        double eigenvalue1 = (-quadraticB + Math.sqrt((quadraticB * quadraticB) -
                (4 * quadraticA * quadraticC))) / (2 * quadraticA); // basically minus b formula
        double eigenvalue2 = (-quadraticB - Math.sqrt((quadraticB * quadraticB) -
                (4 * quadraticA * quadraticC))) / (2 * quadraticA);

        String eigenvalues[][] = {{"Eigenvalue 1: " + eigenvalue1}, {"Eigenvalue 2: " + eigenvalue2}};

        return eigenvalues;
    }

    public static String toString(double[][] matrix) {
        String string = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                string = string + matrix[i][j] + " ";
            }
            string = string + "\n";
        }
        return string;
    }

    public static String toString(String[][] matrix) {
        String string = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                string = string + matrix[i][j] + " ";
            }
            string = string + "\n";
        }
        return string;
    }

    private static boolean isRectangular(double[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int columns = matrix[0].length;
        for (double[] row : matrix) {
            if (row.length != columns) {
                return false;
            }
        }
        return true;
    }

    private static void validateMatrix(double[][] a, double[][] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Input matrices cannot be null.");
        }
        if (!isRectangular(a) || !isRectangular(b)) {
            throw new IllegalArgumentException("Matrices must be rectangular.");
        }
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices must have the same dimensions.");
        }
    }


}