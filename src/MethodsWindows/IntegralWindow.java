package MethodsWindows;

import MainClasses.ClassIntegral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntegralWindow extends JFrame {
    private final ClassIntegral integral = new ClassIntegral();

    public void start() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(500, 500, 400, 400);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel labelBorderA = new JLabel("a = ");
        JTextField borderA = new JTextField(5);
        JLabel labelBorderB = new JLabel("b = ");
        JTextField borderB = new JTextField(5);
        JLabel labelIntegral = new JLabel("∫ ");
        JTextField function = new JTextField(10);
        JLabel labelDx = new JLabel("dx = ");
        JLabel result = new JLabel("0");

        JButton buttonCalculate = new JButton("Calculate");

        buttonCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double res = 0.0;
                try {
                    res = integral.computeIntegral(borderA.getText(), borderB.getText(), function.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(panel, "Wrong input!");
                }
                result.setText(String.valueOf(res));
            }
        });

        panel.add(labelBorderA);
        panel.add(borderA);
        panel.add(labelBorderB);
        panel.add(borderB);
        panel.add(labelIntegral);
        panel.add(function);
        panel.add(labelDx);
        panel.add(result);
        panel.add(buttonCalculate);
        add(panel);

        setVisible(true);
    }

}