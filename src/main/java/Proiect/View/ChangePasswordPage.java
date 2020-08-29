package Proiect.View;

import Proiect.Controller.AuditController;
import Proiect.Controller.UtilizatoriController;
import Proiect.Model.Audit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChangePasswordPage extends JFrame {
        private JLabel newPasswordLabel;
        private JLabel newConfirmationPasswordLabel;

        private JPasswordField newPasswordField;
        private JPasswordField newConfirmPassField;

        private JPanel mainPanel;

        private JButton changePasswordButton;
        private JButton cancelButton;

        UtilizatoriController utilizatoriController = UtilizatoriController.getInstance();
        AuditController auditController = new AuditController();

        ChangePasswordPage(){
            initDefaultProperties();
            initMainPanel();
            initMenuBar();
            initNewPassword();
            initNewPasswordConfirmation();
            initChangePasswordButton();
            changePassButtonAction();
            cancelButtonAction();
        }

        private void initDefaultProperties(){
            setTitle("Pagina de schimbare a parolei");
            setSize(500,200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        private void initMainPanel(){
            mainPanel = new JPanel();
            LayoutManager layout = new GridLayout(3,2);
            mainPanel.setLayout(layout);

            add(mainPanel,BorderLayout.CENTER);
        }

        private void initNewPassword(){
            newPasswordLabel = new JLabel("Noua parola:");
            EmptyBorder border = new EmptyBorder(0,10,0,0);
            newPasswordLabel.setBorder(border);

            newPasswordField = new JPasswordField();
            mainPanel.add(newPasswordLabel);
            mainPanel.add(newPasswordField);

        }

        private void initNewPasswordConfirmation(){
            newConfirmationPasswordLabel = new JLabel("Confirmati noua parola:");
            EmptyBorder border = new EmptyBorder(0,10,0,0);
            newConfirmationPasswordLabel.setBorder(border);

            newConfirmPassField = new JPasswordField();
            mainPanel.add(newConfirmationPasswordLabel);
            mainPanel.add(newConfirmPassField);
        }

        private void initChangePasswordButton(){
            cancelButton = new JButton("Anulare");
            changePasswordButton = new JButton("Schimbati parola");
            mainPanel.add(cancelButton);
            mainPanel.add(changePasswordButton);
        }

        private void changePassButtonAction(){
            changePasswordButton.addActionListener(event->{
                if(utilizatoriController.validatePassword(String.valueOf(newPasswordField.getPassword())))
                {
                    if (utilizatoriController.confirmationPassword(String.valueOf(newPasswordField.getPassword()),String.valueOf(newConfirmPassField.getPassword())))
                    {
                        utilizatoriController.actualizareParola(String.valueOf(newPasswordField.getPassword()),String.valueOf(Main.currentUser.getPassword()));
                        utilizatoriController.actualizareConfirmareParola(String.valueOf(newConfirmPassField.getPassword()),String.valueOf(Main.currentUser.getConfirmPassword()));
                        JOptionPane.showMessageDialog(null,"Actualizarea parolei a fost facuta cu succes!");
                        String textAudit = "Utilizatorul a schimbat parola si a accesat pagina myAccount";
                        createAudit(textAudit);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Parolele nou introduse nu se potrivesc!");
                        newConfirmPassField.requestFocus();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Parola noua nu respecta regula de validare!");
                    newPasswordField.requestFocus();
                }
            });
        }

        private void cancelButtonAction(){
            cancelButton.addActionListener(event->{
                String textAudit = "Utilizatorul a accesat pagina myAccount si a anulat schimbarea de parola";
                createAudit(textAudit);
                JOptionPane.showMessageDialog(null,"Ati anulat operatia de schimbare a parolei!");
                //new MyAccountPage();
                dispose();
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
            String textAudit = "Utilizatorul a accesat pagina myAccount si a anulat schimbarea de parola";
            createAudit(textAudit);
            dispose();
        });

        homeMenu.addActionListener(event->{
            String textAudit = "Utilizatorul a accesat pagina principala";
            createAudit(textAudit);
            new TablePage();
        });

        accountMenu.addActionListener(event->{
            String textAudit = "Utilizatorul a accesat pagina myAccount si a anulat schimbarea de parola";
            createAudit(textAudit);
            dispose();
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
