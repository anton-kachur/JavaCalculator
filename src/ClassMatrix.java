import Jama.Matrix;

import java.util.Scanner;

public class ClassMatrix {
    private int sizeI;
    private int sizeJ;
    private double[][] coeffArray;
    private double[] row;
    private double[] resArray;
    private final Scanner in = new Scanner(System.in);


    ClassMatrix() { }

    ClassMatrix(int sizeI, int sizeJ, double[][] coeffArray, double[] row, double[] resArray) {
        this.sizeI = sizeI;
        this.sizeJ = sizeJ;
        this.coeffArray = new double[sizeI][sizeJ];
        this.resArray = new double[sizeI];
    }


    public void createMatrix() {
        System.out.println("Enter size of matrix (i, j): ");
        sizeI = in.nextInt();
        sizeJ = in.nextInt();

        coeffArray = new double[sizeI][sizeJ];
        row = new double[sizeJ];
        resArray = new double[sizeI];

        for (int i = 0; i < sizeI; i++) {
            System.out.printf("Enter %d row (%d left)\n", (i + 1), (sizeI - i - 1));
            for (int j = 0; j < sizeJ; j++) {
                System.out.printf("Element %d (%d left): ", (j + 1), (sizeI - j - 1));
                row[j] = in.nextInt();
            }
            coeffArray[i] = row;
            System.out.print("Result value: ");
            resArray[i] = in.nextInt();
            row = new double[sizeJ];
            System.out.println("\n");
        }
    }


    public void saveMatrix() {
        System.out.println("Save matrix (y/n)? ");
        if (in.next().equals("y")) {
            resArray = null;
            coeffArray = null;
        }
    }


    public double computeMatrix(String operation) {
        if (operation.equals("det"))
            return computeDet();
        else
            return computeEquations();
    }


    // ToDo Выяснить, можно ли добавить больше уравнений в систему
    public double computeEquations() {
        createMatrix();

        Matrix A = new Matrix(coeffArray);
        Matrix B = new Matrix(resArray, sizeJ);
        Matrix ans = A.solve(B);

        for (int i = 0; i < sizeJ; i++) {
            System.out.printf("x%d = %f\n", i, ans.get(i, 0));
        }

        saveMatrix();
        return 1.0;
    }


    // ToDo Упростить и оформить алгоритм
    public double computeDet() {
        if (coeffArray == null) {
            createMatrix();
        }

        double num1, num2, det = 1.0, total = 1.0;
        int index;
        row = new double[sizeI + 1];
        for (int i = 0; i < sizeI; i++) {
            index = i;

            while (coeffArray[index][i] == 0 && index < sizeI) {
                index++;
            }

            if (index == sizeI) {
                continue;
            }

            if (index != i) {
                for (int j = 0; j < sizeI; j++) {
                    swap(coeffArray, index, j, i, j);
                }
                det = (int)(det * Math.pow(-1, index - i));
            }

            for (int j = 0; j < sizeI; j++) {
                row[j] = coeffArray[i][j];
            }

            for (int j = i + 1; j < sizeI; j++) {
                num1 = row[i];
                num2 = coeffArray[j][i];
                for (int k = 0; k < sizeI; k++) {
                    coeffArray[j][k] = (num1 * coeffArray[j][k]) - (num2 * row[k]);
                    }
                    total = total * num1; // Det(kA)=kDet(A);
                }
            }

            for (int i = 0; i < sizeI; i++) {
                det = det * coeffArray[i][i];
            }

            saveMatrix();
            return (det / total); // Det(kA)/k=Det(A);
        }

        static double[][] swap(double[][] arr, int i1, int j1, int i2, int j2) {
            double temp = arr[i1][j1];
            arr[i1][j1] = arr[i2][j2];
            arr[i2][j2] = temp;
            return arr;
        }

}
