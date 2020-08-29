package Proiect.View;

import Proiect.Controller.AuditController;
import Proiect.Controller.UtilizatoriController;
import Proiect.Model.Audit;
import Proiect.Model.Utilizatori;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterPage extends JFrame
{
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel passwordConfirmationLabel;
    private JLabel emailAddressLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmationField;
    private JTextField emailAddressField;

    private JPanel registerPanel;
    private JPanel buttonPanel;

    private JButton registerDoneButton;

    UtilizatoriController utilizatoriController = UtilizatoriController.getInstance();
    AuditController auditController = new AuditController();

    RegisterPage(){
        initDefaultProperties();
        initRegisterPanel();
        initButtonPanel();
    }

    private void initRegisterPanel(){
        registerPanel = new JPanel();
        LayoutManager layout = new GridLayout(4,2);
        registerPanel.setLayout(layout);

        usernameLabel = new JLabel("username:");
        usernameLabel.setPreferredSize(new Dimension(50,40));
        passwordLabel = new JLabel("password:");
        passwordLabel.setPreferredSize(new Dimension(50,40));
        passwordConfirmationLabel = new JLabel("passwordConfirmation:");
        passwordConfirmationLabel.setPreferredSize(new Dimension(50,40));
        emailAddressLabel = new JLabel("emailAdress:");
        emailAddressLabel.setPreferredSize(new Dimension(50,40));

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(60,40));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(60,40));
        passwordConfirmationField = new JPasswordField();
        passwordConfirmationField.setPreferredSize(new Dimension(60,40));
        emailAddressField = new JTextField();
        emailAddressField.setPreferredSize(new Dimension(60,40));

        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(passwordConfirmationLabel);
        registerPanel.add(passwordConfirmationField);
        registerPanel.add(emailAddressLabel);
        registerPanel.add(emailAddressField);

        add(registerPanel, BorderLayout.CENTER);

    }

    private void initButtonPanel(){
        buttonPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        buttonPanel.setLayout(layout);

        registerDoneButton = new JButton("SignUp");

        registerDoneButton.addActionListener(event ->{
            if(validareUsername()&&validareEmail()&&validareSintaxaParola()&&confirmareParola())
            {
                Utilizatori currentUser = new Utilizatori(usernameField.getText(),String.valueOf(passwordField.getPassword()),emailAddressField.getText(),String.valueOf(passwordConfirmationField.getPassword()));
                if (utilizatoriController.createUtilizator(currentUser));
                {
                    createAudit();
                    JOptionPane.showMessageDialog(null,"Utilizatorul a fost creat cu succes!");
                    new LoginPage();
                    dispose();
                }
            }
        });

        buttonPanel.add(registerDoneButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initDefaultProperties(){
        setTitle("Pagina de Inregistrare a utilizatorului");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean validareUsername(){
        //boolean validareUsername = false;
        if (usernameField.getText() == "")
        {
            JOptionPane.showMessageDialog(null,"Introduceti username!");
            usernameField.requestFocus();
            //validareUsername = false;
            return false;
        }
        else
        {
            if (utilizatoriController.validateUsername(usernameField.getText())== false){
                JOptionPane.showMessageDialog(null,"Username-ul exista deja!");
                usernameField.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean validareEmail(){
        if (emailAddressField.getText() == ""){
            JOptionPane.showMessageDialog(null,"Introduceti email!");
            emailAddressField.requestFocus();
            return false;
        }
        else
        {
            if(utilizatoriController.validateEmail(emailAddressField.getText()) == false){
                JOptionPane.showMessageDialog(null,"Mail-ul nu respecta structura a@y.c");
                emailAddressField.requestFocus();
                //validareEmail = false;
                return false;
            }
        }
        return true;
    }


    private boolean validareSintaxaParola(){
        if (String.valueOf(passwordField.getPassword())==""){
            JOptionPane.showMessageDialog(null,"Introduceti parola!");
            passwordField.requestFocus();
            return false;
        }
        else
        {
            if(utilizatoriController.validatePassword(String.valueOf(passwordField.getPassword()))==false){
                JOptionPane.showMessageDialog(null,"Parola nu respecta sablonul!(min.6car,litera mare, litera mica si numar)");
                passwordField.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean confirmareParola(){
        if (String.valueOf(passwordConfirmationField.getPassword())==""){
            JOptionPane.showMessageDialog(null,"Nu ati introdus din nou parola!");
            passwordConfirmationField.requestFocus();
            return false;
        }
        else{
            if ((utilizatoriController.confirmationPassword(String.valueOf(passwordField.getPassword()),String.valueOf(passwordConfirmationField.getPassword())))==false){
                JOptionPane.showMessageDialog(null,"Parolele nu se potrivesc!");
                passwordConfirmationField.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void createAudit(){
        String auditEvent = "Utilizatorul "+usernameField.getText()+ " a fost creat";
        String auditMoment;
        LocalDateTime moment;

        moment = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        auditMoment = moment.format(formatter);

        Audit audit = new Audit(auditEvent,auditMoment);
    }
}
