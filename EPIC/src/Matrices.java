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

        for(int i = 0; i < a.length; i++) { // iterating through rows
            for(int j = 0; j < b[0].length; j++) { // iterating through columns
                for(int k = 0; k < b.length; k++) { // iterating through shared dimension
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public double[][] matrixTranspose(double[][] a) {

        double[][] c = new double[a[0].length][a.length];

        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
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
        c[0][1] = - a[0][1] * det;
        c[1][0] = - a[1][0] * det;
        c[1][1] = a[0][0] * det;

        return c;
    }

    public double[][] matrixLinearEquation(double[][] a, double[][] b) {

        try { // try solving using the inverse method for 2x2 matrices
            double[][] inverseA = matrixInverseTwoByTwo(a);

            double[][] c = matrixMultiplication(inverseA, b);

            return c;
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
}