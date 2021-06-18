package MainClasses;

import java.beans.Expression;
import java.util.*;
import java.util.List;
import static java.lang.Math.*;
import Jama.Matrix;
import MethodsWindows.AreaWindow;
import MethodsWindows.IntegralWindow;
import MethodsWindows.MatrixWindow;

public class Calculator {

    public enum LexemeType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIVISION, OP_POW, OP_FACT, OP_MOD,
        LOGX,
        NUMBER, KEYWORD, CONST,
        EOF
    }

    public static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType t, String v) {
            this.type = t;
            this.value = v;
        }

        public Lexeme(LexemeType t, Character v) {
            this.type = t;
            this.value = v.toString();
        }

        @Override
        public String toString() {
            return "Lexeme {" + "type=" + type + ", value=" + value + '\'' + '}';
        }
    }


    public static class LexemeBuffer {
        private int pos;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next(int step) {
            return (step > 1)? lexemes.get(pos += step): lexemes.get(pos++);
        }

        public Lexeme past(int step) { return (step > 1)? lexemes.get(pos -= step): lexemes.get(pos--); }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }


    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = Character.toLowerCase(expText.charAt(pos));
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIVISION, c));
                    pos++;
                    continue;
                case '%':
                    lexemes.add(new Lexeme(LexemeType.OP_MOD, c));
                    pos++;
                    continue;
                case '!':
                    lexemes.add(new Lexeme(LexemeType.OP_FACT, c));
                    pos++;
                    continue;
                case '^':
                    lexemes.add(new Lexeme(LexemeType.OP_POW, c));
                    pos++;
                    continue;
                default:
                    if ((c >= '0' && c <= '9') || c == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while ((c >= '0' && c <= '9') || c == '.');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else if (c >= 'a' && c <= 'z') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c >= 'a' && c <= 'z');

                        if (sb.toString().equals("pi") || sb.toString().equals("e"))
                            lexemes.add(new Lexeme(LexemeType.CONST, sb.toString()));
                        else if (sb.toString().equals("logx"))
                            lexemes.add(new Lexeme(LexemeType.LOGX, sb.toString()));
                        else
                            lexemes.add(new Lexeme(LexemeType.KEYWORD, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character");
                        }
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }


    public static double expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next(1);
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }


    public static double plusminus(LexemeBuffer lexemes) {
        double value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next(1);
            switch (lexeme.type) {
                case OP_PLUS:
                    value += multdiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multdiv(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }


    public static double multdiv(LexemeBuffer lexemes) {
        double value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next(1);
            switch (lexeme.type) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIVISION:
                    value /= factor(lexemes);
                    break;
                case OP_MOD:
                    value %= factor(lexemes);
                    break;
                case OP_POW:
                    value = pow(value, factor(lexemes));
                    break;
                case OP_FACT:
                    value = getFactorial(Integer.parseInt(lexemes.past(2).value));
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }


    // ToDo Убрать некоторые функции (точнее их дубликаты)
    public static double factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next(1);
        switch(lexeme.value) {
            case "sin": return sin(toRadians(Double.parseDouble(lexemes.next(1).value)));
            case "cos": return cos(toRadians(Double.parseDouble(lexemes.next(1).value)));
            case "tan": return tan(toRadians(Double.parseDouble(lexemes.next(1).value)));
            case "atan": return atan(toRadians(Double.parseDouble(lexemes.next(1).value)));
            case "rsin": return sin(Double.parseDouble(lexemes.next(1).value));
            case "rcos": return cos(Double.parseDouble(lexemes.next(1).value));
            case "rtan": return tan(Double.parseDouble(lexemes.next(1).value));
            case "ratan":
            case "arctan":
                return atan(Double.parseDouble(lexemes.next(1).value));
            case "sinh": return sinh(Double.parseDouble(lexemes.next(1).value));
            case "cosh": return cosh(Double.parseDouble(lexemes.next(1).value));
            case "tanh": return tanh(Double.parseDouble(lexemes.next(1).value));
            case "atanh": return 1/tanh(Double.parseDouble(lexemes.next(1).value));
            case "arcsin": return asin(Double.parseDouble(lexemes.next(1).value));
            case "arccos": return acos(Double.parseDouble(lexemes.next(1).value));
            case "ln": return log(Double.parseDouble(lexemes.next(1).value));
            case "sqrt": return sqrt(Double.parseDouble(lexemes.next(1).value));
            case "pi": return PI;
            case "e": return E;
            case "equation": return computeEquation();
            case "integral":
                new IntegralWindow().start();
                return 1.0;
                //MainClasses.ClassIntegral integral = new MainClasses.ClassIntegral();
                //return integral.computeIntegral();
            case "matrix":
                new MatrixWindow().start();
                //ClassMatrix matrix = new ClassMatrix();
                //return matrix.computeMatrix(lexemes.next(1).value);
            case "area":
                new AreaWindow().start();
                return 1.0;
                //integral = new MainClasses.ClassIntegral();
                //return integral.computeArea(lexemes.next(1).value);
        }

        switch(lexeme.type) {
            case NUMBER: return Double.parseDouble(lexeme.value);
            case LEFT_BRACKET:
                double value = expr(lexemes);
                lexeme = lexemes.next(1);
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.value + " at position: " + lexemes.getPos());
                }
                return value;
            case LOGX:
                value = log(Double.parseDouble(lexemes.next(2).value)) / log(Double.parseDouble(lexemes.past(1).value));
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.value + " at position: " + lexemes.getPos());
        }
    }


    //ToDo Добавить алгоритм парсинга уравнений из MainClasses.ClassIntegral
    public static double computeEquation() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter size of equation: ");
        int size = in.nextInt();
        List<Double> arguments = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            System.out.println("Enter x^%d: " + (size-i-1));
            arguments.add(in.nextDouble());
        }
        return 1.0;
    }


    public static int getFactorial(int n) {
        return (n <= 1)? 1: n * getFactorial(n - 1);
    }


    public double MainFunc(String expression) {
        //Scanner scanner = new Scanner(System.in);

        List<Lexeme> lexemes = lexAnalyze(expression);

        //Для консольной версии
        //List<Lexeme> lexemes = lexAnalyze(scanner.nextLine());
        System.out.println(lexemes);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        //System.out.println(expr(lexemeBuffer) + " ");
        System.out.println(lexemes);
        return expr(lexemeBuffer);
    }


    public static void main(String[] args) throws InterruptedException {
        /*Scanner scanner = new Scanner(System.in);

        CalculatorGUI gui = new CalculatorGUI();

        String expression = gui.inputExpression();

        System.out.println("Expression: "+expression);
        List<Lexeme> lexemes = lexAnalyze(expression);

        //Для консольной версии
        //List<Lexeme> lexemes = lexAnalyze(scanner.nextLine());
        System.out.println(lexemes);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        System.out.println(expr(lexemeBuffer) + " ");
        System.out.println(lexemes);*/
    }
}
