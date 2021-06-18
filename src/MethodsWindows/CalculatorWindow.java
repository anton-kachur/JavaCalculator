package MethodsWindows;

import MainClasses.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorWindow extends JFrame {
    private final Calculator calculator = new Calculator();

    public void start() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 100);

        JPanel panel = new JPanel(new FlowLayout());
        JTextField textField = new JTextField(10);
        JLabel label2 = new JLabel(" = ");
        JLabel result = new JLabel("0");

        JButton buttonCalculate = new JButton("Calculate");
        JButton buttonGuide = new JButton("Guide");

        buttonCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double res = 0.0;
                try {
                    res = calculator.MainFunc(textField.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(panel, "Wrong input!");
                }
                result.setText(String.valueOf(res));
            }
        });


        buttonGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GuideWindow().start();
            }
        });

        panel.add(textField);
        panel.add(label2);
        panel.add(result);
        panel.add(buttonCalculate);
        panel.add(buttonGuide);
        add(panel);

        setVisible(true);
    }


    /*public void startGuide() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(400, 100, 600, 100);

        JPanel panel1 = new JPanel(new FlowLayout());
        JTextField textField1 = new JTextField(10);
        textField1.setText("Coming soon...");

        panel1.add(textField1);
        add(panel1);
        setVisible(true);
    }*/
}
