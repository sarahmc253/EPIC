public class Matrices {

    public double[][] matrixAddition(double[][] a, double[][] b) { // method to add two matrices

        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices have to have the same dimensions.");
        }

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

    public double[][] matrixSubtraction(double[][] a, double[][] b) { // method to subtract two matrices

        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices have to have the same dimensions.");
        }

        double[][] c = new double[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through each row
            for (int j = 0; j < a[0].length; j++) { // iterating through each column in the row
                c[i][j] = a[i][j] - b[i][j]; // subtracting the elements a and b together and storing the result in c
            }
        }
        return c;
    }

    public double[][] matrixMultiplication(double[][] a, double[][] b) {

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

    public double[][] matrixTranspose(double[][] a) {

        double[][] c = new double[a[0].length][a.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                c[j][i] = a[i][j];
            }
        }
        return c;
    }

    public double matrixDeterminant(double[][] a) {
        if (a.length != 2 || a[0].length != 2 || a[1].length != 2) {
            throw new IllegalArgumentException("Matrix must be a 2 x 2.");
        }

        double c = (a[0][0] * a[1][1]) - (a[0][1] * a[1][0]);

        return c;
    }

    public double[][] matrixInverseTwoByTwo(double[][] a) {
        if (a.length != 2 || a[0].length != 2 || a[1].length != 2) {
            throw new IllegalArgumentException("Matrix must be a 2 x 2.");
        }

        double[][] c = new double[2][2];

        double det = 1.0 / matrixDeterminant(a);

        c[0][0] = a[1][1] * det;
        c[0][1] = -a[0][1] * det;
        c[1][0] = -a[1][0] * det;
        c[1][1] = a[0][0] * det;

        return c;
    }

    public double[][] matrixLinearEquation(double[][] a, double[][] b) {

        try { // try solving using the inverse method for 2x2 matrices
            double[][] inverseA = matrixInverseTwoByTwo(a);

            double[][] x = matrixMultiplication(inverseA, b);

            return x;
        }

        catch (Exception e) { // fallback to LU decomposition for larger matrices

            double[][] lowerTriangularMatrix = new double[a.length][a[0].length];
            double[][] upperTriangularMatrix = new double[a.length][a[0].length];
            double[][] c = new double[a.length][1];
            double[][] x = new double[a.length][1];

            // Ax = b
            // A = LU
            // LUx = b
            // Let Ux = c
            // Lc = b
            // Then solve Ux = c

            for (int i = 0; i < a.length; i++) { // initalizing lowerTriangularMatrix to an identity matrix
                lowerTriangularMatrix[i][i] = 1.0;
            }

            for (int i = 0; i < a.length; i++) {
                for (int j = i; j < a.length; j++) {  // creating the upperTriangularMatrix
                    double sum = 0.0;
                    for (int k = 0; k < i; k++) { //
                        sum += lowerTriangularMatrix[i][k] * upperTriangularMatrix[k][j];
                    }
                    upperTriangularMatrix[i][j] = a[i][j] - sum;
                }

                for (int j = i + 1; j < a.length; j++) {  // creating a lowerTriangularMatrix
                    double sum = 0.0;
                    for (int k = 0; k < i; k++) {
                        sum += lowerTriangularMatrix[j][k] * upperTriangularMatrix[k][i];
                    }
                    if (upperTriangularMatrix[i][i] == 0) {
                        throw new ArithmeticException("Matrix is singular");
                    }
                    lowerTriangularMatrix[j][i] = (a[j][i] - sum) / upperTriangularMatrix[i][i];
                }
            }

            for (int i = 0; i < a.length; i++) { // solving for Lc = b
                double sum = 0.0;
                for (int j = 0; j < i; j++) {
                    sum += lowerTriangularMatrix[i][j] * c[j][0];
                }
                c[i][0] = b[i][0] - sum;
            }

            for (int i = a.length - 1; i >= 0; i--) { // Solving for Ux = c
                double sum = 0.0;
                for (int j = i + 1; j < a.length; j++) {
                    sum += upperTriangularMatrix[i][j] * x[j][0];
                }
                x[i][0] = (c[i][0] - sum) / upperTriangularMatrix[i][i];
            }

            return x;
        }
    }


    public static double dotProduct(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same number of elements.");
        }

        double dotProduct = 0.0;
        for (int i = 0; i < a.length; i++) {
            dotProduct += a[i] * b[i];
        }

        return dotProduct;
    }

    public static double[] findEigenvalues(double[][] matrix) {
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
        double quadraticC = (a * d) - (b * c); // also determinant


        double eigenvalue1 = (-quadraticB + Math.sqrt((quadraticB * quadraticB) -
                (4 * quadraticA * quadraticC))) / (2 * quadraticA);
        double eigenvalue2 = (-quadraticB - Math.sqrt((quadraticB * quadraticB) -
                (4 * quadraticA * quadraticC))) / (2 * quadraticA);

        double eigenvalues[] = {eigenvalue1, eigenvalue2};

        return eigenvalues;
    }

    public String toString(double[][] matrix) {
        String string = "";
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                string = string + matrix[i][j] + " ";
            }
            string = string + "\n";
        }
        return string;
    }
}