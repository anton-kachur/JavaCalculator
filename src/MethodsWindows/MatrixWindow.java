package MethodsWindows;

import MainClasses.ClassIntegral;
import MainClasses.ClassMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixWindow extends JFrame implements AreaWindowInterface {
    private final ClassMatrix matrix = new ClassMatrix();

    public void start() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(500, 500, 400, 400);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel matrixLabel = new JLabel("Enter matrix: ");
        JTextField matrixValues1 = new JTextField(10);
        JTextField matrixValues2 = new JTextField(10);
        JTextField matrixValues3 = new JTextField(10);
        JTextField matrixResValues = new JTextField(10);

        JLabel matrixOpLabel = new JLabel("Operation: ");
        JTextField matrixOp = new JTextField(10);

        JButton buttonCalculate = new JButton("Calculate");
        JLabel result = new JLabel("0");

        buttonCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double res = 0.0;
                String[][] str = { matrixValues1.getText().split(" "),
                                   matrixValues2.getText().split(" "),
                                   matrixValues3.getText().split(" "),
                                   matrixResValues.getText().split(" ")};

                double[][] args = new double[str.length][3];
                double[] argsB = new double[3];

                for (int i = 0; i < str.length-1; i++) {
                    for (int j = 0; j < str[i].length; j++) {
                        args[i][j] = Double.parseDouble(str[i][j]);
                    }
                }

                for (int j = 0; j < str[0].length; j++) {
                    argsB[j] = Double.parseDouble(str[3][j]);
                }


                try {
                    res = matrix.computeMatrix(matrixOp.getText(), new int[]{3, 3}, args, argsB);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(panel, "Wrong input!");
                }
                result.setText(String.valueOf(res));
            }
        });

        panel.add(matrixLabel);
        panel.add(matrixValues1);
        panel.add(matrixValues2);
        panel.add(matrixValues3);
        panel.add(matrixResValues);
        panel.add(matrixOpLabel);
        panel.add(matrixOp);
        panel.add(result);
        panel.add(buttonCalculate);
        add(panel);

        setVisible(true);
    }
}