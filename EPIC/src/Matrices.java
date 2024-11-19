public class Matrices {

    public int[][] matrixAddition(int[][] a, int[][] b) { // method to add two matrices

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

        // creating a new 2d array to store the result and setting the
        // dimensions to the same as a and b
        int[][] c = new int[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) { // iterating through each row
            for (int j = 0; j < a[0].length; j++) { // iterating through each column in the row
                c[i][j] = a[i][j] - b[i][j]; // adding the elements a and b together and storing the result in c
            }
        }
        return c;
    }
}