package Proiect.View;

import Proiect.Controller.AuditController;
import Proiect.Controller.UtilizatoriController;
import Proiect.Model.Audit;
import Proiect.Model.Utilizatori;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyAccountPage extends JFrame {
    private JLabel userNameLabel;
    private JLabel currentUsernameLabel;
    private JLabel emailLabel;
    private JLabel currentUserEmailLabel;
    private JLabel changeUsernameLabel;
    private JLabel changeEmailLabel;

    private JTextField changeUsernameTextField;
    private JTextField changeEmailTextField;

    private JButton changeUsernameButton;
    private JButton changeEmailButton;
    private JButton changePasswordButton;

    private JPanel mainPanel;
    private JPanel changePasswordPanel;

    UtilizatoriController utilizatoriController = UtilizatoriController.getInstance();
    AuditController auditController = new AuditController();

    MyAccountPage(){
        initDefaultProperties();
        initMenuBar();
        initMainPanel();
        initCurrentUsername();
        initCurrentUserEmail();
        initChangeUsername();
        initChangeEmail();
        initChangePassword();
        changeUsernameAction();
        changeEmailAction();
    }

    private void initDefaultProperties(){
        setTitle("MyAccount");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initMainPanel(){
        mainPanel = new JPanel();
        LayoutManager layout = new GridLayout(7,2);
        mainPanel.setLayout(layout);
        add(mainPanel,BorderLayout.CENTER);
    }

    private void initCurrentUsername(){
        userNameLabel = new JLabel("Username curent:");

        currentUsernameLabel = new JLabel(Main.currentUser.getUsername());

        mainPanel.add(userNameLabel);
        mainPanel.add(currentUsernameLabel);
    }

    private void initCurrentUserEmail(){
        emailLabel = new JLabel("Email-ul user-ului curent");

        currentUserEmailLabel = new JLabel(Main.currentUser.getEmail());

        mainPanel.add(emailLabel);
        mainPanel.add(currentUserEmailLabel);
    }

    private void initChangeUsername(){
        changeUsernameLabel = new JLabel("Schimbare username:");
        changeUsernameTextField = new JTextField();
        changeUsernameButton = new JButton("Schimba username!");

        JPanel emptyPanel = new JPanel();

        mainPanel.add(changeUsernameLabel);
        mainPanel.add(changeUsernameTextField);
        mainPanel.add(emptyPanel);
        mainPanel.add(changeUsernameButton);
    }

    private void initChangeEmail(){
        changeEmailLabel = new JLabel("Schimbare email:");
        changeEmailTextField = new JTextField();
        changeEmailButton = new JButton("Schimba email!");

        JPanel emptyPanel = new JPanel();

        mainPanel.add(changeEmailLabel);
        mainPanel.add(changeEmailTextField);
        mainPanel.add(emptyPanel);
        mainPanel.add(changeEmailButton);
    }

    private void initChangePassword(){
        changePasswordPanel = new JPanel();
        changePasswordButton = new JButton("Schimba parola");
        changePasswordPanel.add(changePasswordButton);

        mainPanel.add(changePasswordPanel);

        changePasswordButton.addActionListener(event->{
            String textAudit ="Utilizatorul a accesat pagina de schimbare a parolei";
            createAudit(textAudit);
            new ChangePasswordPage();
        });
    }

    private void changeUsernameAction(){
        changeUsernameButton.addActionListener(event->{
            if (utilizatoriController.validateUsername(changeUsernameTextField.getText())){
                utilizatoriController.actualizareUsername(changeUsernameTextField.getText(),Main.currentUser.getUsername());
                String textAudit = "Username-ul "+currentUsernameLabel.getText()+" a fost schimbat in "+changeUsernameTextField.getText();
                createAudit(textAudit);
                JOptionPane.showMessageDialog(null,"Username-ul a fost modificat!");
            }
            else{
                JOptionPane.showMessageDialog(null,"Username-ul introdus nu poate fi validat!");
                changeUsernameTextField.requestFocus();
            }
        });
    }

    private void changeEmailAction(){
        changeEmailButton.addActionListener(event->{
            if(utilizatoriController.validateEmail(changeEmailTextField.getText())){
                utilizatoriController.actualizareEmail(changeEmailTextField.getText(),Main.currentUser.getEmail());
                String textAudit = "Email-ul "+currentUserEmailLabel.getText()+" a fost schimbat in "+changeEmailTextField.getText();
                createAudit(textAudit);
                JOptionPane.showMessageDialog(null,"Email-ul a fost modificat!");
            }
            else{
                JOptionPane.showMessageDialog(null,"Email-ul introdus nu poate fi validat!");
                changeEmailTextField.requestFocus();
            }
        });
    }

    private void initMenuBar(){
        JPanel menuBarPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        menuBarPanel.setLayout(layout);

        JMenuBar menuBar = new JMenuBar();

        JButton homeMenu = new JButton("Home");
        JButton accountMenu = new JButton("MyAccount");
        JButton logoutMenu = new JButton("LogOut");
        JButton backButton = new JButton("Back");

        menuBar.add(backButton);
        menuBar.add(homeMenu);
        menuBar.add(accountMenu);
        menuBar.add(logoutMenu);

        backButton.addActionListener(event->{
            String textAudit = "Utilizatorul a accesat pagina principala";
            createAudit(textAudit);
            dispose();
        });

        homeMenu.addActionListener(event->{
            String textAudit = "Utilizatorul a accesat pagina principala";
            createAudit(textAudit);
            new TablePage();

        });

        logoutMenu.addActionListener(event->{
            String textAudit = "Utilizatorul s-a delogat!";
            createAudit(textAudit);
            //dispose();
            TablePage.logOut();
        });

        menuBarPanel.add(menuBar);
        add(menuBarPanel,BorderLayout.NORTH);
    }

    private void createAudit(String mesaj){
        String auditEvent = mesaj;
        String auditMoment;
        LocalDateTime moment;

        moment = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        auditMoment = moment.format(formatter);

        Audit audit = new Audit(auditEvent,auditMoment);
        auditController.insertAudit(audit);
    }
}
