public class Matrices {

    public int[][] matrixAddition(int[][] a, int[][] b) { // method to add two matrices

        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices have to have the same dimensions.");
        }


        // creating a new 2d array to store the result and setting the
        // dimensions to the same as a and b
        int[][] c = new int[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through each row
            for (int j = 0; j < a[0].length; j++) { // iterating through each column in the row
                c[i][j] = a[i][j] + b[i][j]; // adding the elements a and b together and storing the result in c
            }
        }
        return c;
    }

    public int[][] matrixSubtraction(int[][] a, int[][] b) { // method to add two matrices

        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices have to have the same dimensions.");
        }

        int[][] c = new int[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through each row
            for (int j = 0; j < a[0].length; j++) { // iterating through each column in the row
                c[i][j] = a[i][j] - b[i][j]; // subtracting the elements a and b together and storing the result in c
            }
        }
        return c;
    }

    public int[][] matrixMultiplication(int[][] a, int[][] b) {

        if (a[0].length != b.length) {
            throw new IllegalArgumentException("The columns in matrix a must equal the rows in matrix b");
        }

        int[][] c = new int[a.length][b[0].length];

        for(int i = 0; i < a.length; i++) { // iterating through rows
            for(int j = 0; j < b[0].length; j++) { // iterating through columns
                for(int k = 0; k < b.length; k++) { // iterating through shared dimension
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public int[][] matrixTranspose(int[][] a) {
        int[][] c = new int[a[0].length][a.length];

        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
                c[j][i] = a[i][j];
            }
        }
        return c;
    }
}