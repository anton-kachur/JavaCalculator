package MainClasses;

import MethodsWindows.AreaWindow;

import java.util.Scanner;

import static java.lang.Math.*;
import static java.lang.Math.toRadians;

public class ClassIntegral {
    private double a;
    private double b;
    private String function = "";
    private String[] splitFunction;
    int pos;
    Scanner in = new Scanner(System.in);

    public ClassIntegral() { }

    ClassIntegral(double a, double b, String function) {
        this.a = a;
        this.b = b;
        this.function = function;
    }


    public void createIntegral() {
        //System.out.println("Enter borders (a, b): ");
        a = in.nextInt();
        b = in.nextInt();
        //System.out.println("Enter function: ");
        //function = in.next();
        splitFunction = function.split(" ");
    }


    // ToDo Добавить больше функций + решить проблему со степенями
    public double createFunction(double x, int index) {
        switch (splitFunction[index]) {
            case "x": return x;
            case "x^2": return pow(x, 2);
            case "x^3": return pow(x, 3);
            case "x^4": return pow(x, 4);

            case "sin": return sin(toRadians(x));
            case "cos": return cos(toRadians(x));
            case "tan": return tan(toRadians(x));
            case "atan": return atan(toRadians(x));
        }
        return 1.0;
    }


    public void saveIntegral() {
        System.out.println("Save integral parameters (y/n)? ");
        if (in.next().equals("n")) {
            a = 0;
            b = 0;
            function = "";
        }
    }


    public double integral(double a, double b, Function function) {
        double INCREMENT = 1E-4;
        double area = 0;
        double modifier = 1;
        if(a > b) {
            double tempA = a;
            a = b;
            b = tempA;
            modifier = -1;
        }
        for(double i = a + INCREMENT; i < b; i += INCREMENT) {
            double dFromA = i - a;
            //System.out.println("FUNC: " + (function.f(a + dFromA)));
            area += (INCREMENT / 2) * (function.f(a + dFromA) + function.f(a + dFromA - INCREMENT));
        }
        return (round(area * 1000.0) / 1000.0) * modifier;
    }


    public double computeIntegral(String borderA, String borderB, String func) {
        this.function = func;
        this.a = Double.parseDouble(borderA);
        this.b = Double.parseDouble(borderB);
        this.splitFunction = function.split(" ");

        if (function.equals("")) {
            createIntegral();
        }

        double result = 0.0;

        while (pos < splitFunction.length) {
            result += integral(a, b, x -> createFunction(x, pos));
            pos++;
        }

        //saveIntegral();
        return result;
    }


    // ToDo Добавить больше функций
    public double computeArea(String areaType, double[] args) {
        //Scanner in = new Scanner(System.in);
        switch (areaType) {
            case "square":
                /*System.out.println("Enter length of a, b, c: ");
                l1 = in.nextDouble();
                l2 = in.nextDouble();
                double l3 = in.nextDouble();
                */
                return  pow(args[0], 2);
            case "circle":
                //System.out.println("Enter length of a: ");
                return PI*pow(args[0], 2);
            case "rectangle":
                /*System.out.println("Enter length of a, b: ");
                l1 = in.nextDouble();
                double l2 = in.nextDouble();*/
                return args[0] * args[1];
            case "triangle":
                /*System.out.println("Enter radius: ");
                l1 = in.nextDouble();
                */
                double p = (args[0]+args[1]+args[2])/2;
                return sqrt(p * (p - args[0]) * (p - args[1]) * (p - args[2]));
            //Coming soon...
        }
        return 0;
    }
}
