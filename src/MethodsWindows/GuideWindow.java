package MethodsWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuideWindow extends JFrame {

    public void start() {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setBounds(400, 100, 600, 100);

        JPanel panel1 = new JPanel(new FlowLayout());
        JTextField textField1 = new JTextField(40);
        textField1.setText("Coming soon...");

        panel1.add(textField1);
        add(panel1);
        setVisible(true);
    }
}
