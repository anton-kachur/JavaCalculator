package MethodsWindows;

import MainClasses.ClassIntegral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AreaWindow extends JFrame implements AreaWindowInterface {
    private final ClassIntegral integral = new ClassIntegral();

    public void start() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(500, 500, 400, 400);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel figureLabel = new JLabel("Figure: ");
        JTextField figureName = new JTextField(10);
        JLabel sideLabel = new JLabel("Enter number of sides ");
        JTextField side = new JTextField(10);
        JLabel label = new JLabel(" S = ");
        JLabel result = new JLabel("0");

        JButton buttonCalculate = new JButton("Calculate");

        buttonCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double res = 0.0;
                String[] str = side.getText().split(" ");
                double[] args = new double[str.length];

                for (int i = 0; i < str.length; i++) {
                    args[i] = Double.parseDouble(str[i]);
                }

                try {
                    res = integral.computeArea(figureName.getText(), args);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(panel, "Wrong input!");
                }
                result.setText(String.valueOf(res));
            }
        });

        panel.add(figureLabel);
        panel.add(figureName);
        panel.add(sideLabel);
        panel.add(side);
        panel.add(label);
        panel.add(result);
        panel.add(buttonCalculate);
        add(panel);

        setVisible(true);
    }
}