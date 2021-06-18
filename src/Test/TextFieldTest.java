package Test;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.*;
import java.awt.FlowLayout;

public class TextFieldTest extends JFrame
{
    // Текстовые поля
    JTextField calcField;

    public TextFieldTest()
    {
        super("MainClasses.Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание текстовых полей
        calcField = new JTextField(25);
        calcField.setToolTipText("Enter arithmetic expression or read guide first");

        // Настройка шрифта
        calcField.setFont(new Font("Dialog", Font.PLAIN, 14));

        // Слушатель окончания ввода
        calcField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Отображение введенного текста
                JOptionPane.showMessageDialog(TextFieldTest.this,
                        "Ваше слово: " + calcField.getText());
            }
        });


        // Создание панели с текстовыми полями
        JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEADING));
        contents.add(calcField);
        setContentPane(contents);
        // Определяем размер окна и выводим его на экран
        setSize(400, 130);
        setVisible(true);
    }
    public static void main(String[] args) {
        new TextFieldTest();
    }
}