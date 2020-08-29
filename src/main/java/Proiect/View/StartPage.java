package Proiect.View;

import javax.swing.*;
import java.awt.*;

public class StartPage extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JPanel buttonPanel;

    StartPage(){
        initDefaultProperties();
        initButtonPanel();
    }

    private void initButtonPanel(){
        buttonPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        buttonPanel.setLayout(layout);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(event -> new LoginPage());
        buttonPanel.add(loginButton);
        registerButton.addActionListener(event -> new RegisterPage());
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void initDefaultProperties(){
        setTitle("Pagina de Start");
        setSize(300,100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
