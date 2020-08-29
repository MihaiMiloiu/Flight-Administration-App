package Proiect.View;

import Proiect.Controller.AuditController;
import Proiect.Controller.UtilizatoriController;
import Proiect.Model.Audit;
import Proiect.Model.Utilizatori;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginPage extends JFrame {
    private JButton loginButton;

    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JTextField usernameTextField;
    private JPasswordField passwordTextField;

    private JPanel informationPanel;
    private JPanel buttonPanel;

    //private int minutes = 0;

    UtilizatoriController utilizatoriController = UtilizatoriController.getInstance();
    AuditController auditController = new AuditController();
    Audit audit;
    //ExecutorService executor = Executors.newSingleThreadExecutor();
    LoginPage(){
        initDefaultProperties();
        initInformationPanel();
        initButtonPanel();
    }

    private void initInformationPanel(){
        informationPanel = new JPanel();
        LayoutManager layout = new GridLayout(2,2);
        informationPanel.setLayout(layout);

        usernameLabel = new JLabel("username/email:");
        EmptyBorder labelBorder = new EmptyBorder(0,50, 0,50);
        usernameLabel.setBorder(labelBorder);

        passwordLabel = new JLabel("password:");
        passwordLabel.setBorder(labelBorder);

        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(70,40));

        passwordTextField = new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(70,40));

        informationPanel.add(usernameLabel);
        informationPanel.add(usernameTextField);
        informationPanel.add(passwordLabel);
        informationPanel.add(passwordTextField);

        add(informationPanel, BorderLayout.CENTER);
    }

    private void initButtonPanel(){
        buttonPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        buttonPanel.setLayout(layout);

        loginButton = new JButton("LogIn");

        loginButton.addActionListener(event ->{
            if(verificareCredentiale()){
                JOptionPane.showMessageDialog(null,"Logarea s-a efectuat cu succes!");
                Utilizatori currentUser = utilizatoriController.utilizatorCurent(usernameTextField.getText(),String.valueOf(passwordTextField.getPassword()));
                Main.setCurrentUser(currentUser);
                createAudit();
                if(auditController.insertAudit(audit)) {
                    //executor.execute(logOutThread);
                    //autoLogOut();
                    new TablePage();
                    //dispose();
                }
                if(!(auditController.insertAudit(audit)))
                {
                    JOptionPane.showMessageDialog(null,"Eroare la inregistrarea auditului");
                }
            }
            //JOptionPane.showMessageDialog(null,"Eroare la conexiunea cu baza de date!");
            //dispose();
        });
        buttonPanel.add(loginButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initDefaultProperties(){
        setTitle("Pagina de Inregistrare a utilizatorului");
        setSize(500,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean verificareCredentiale(){
        boolean existantUserOrEmail = false;
        boolean existantPassword = false;

        if (!(utilizatoriController.verificareExistentaUsername(usernameTextField.getText())) || !(utilizatoriController.verificarePotrivireEmail(usernameTextField.getText())))
        {
            existantUserOrEmail = true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Username-ul/Email-ul introdus este incorect!");
            usernameTextField.requestFocus();
            existantUserOrEmail = false;
        }

        if(utilizatoriController.verificarePotrivireParola(String.valueOf(passwordTextField.getPassword()))){
            existantPassword = true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Parola introdusa este incorecta!");
            passwordTextField.requestFocus();
            existantPassword = false;
        }

        if (existantUserOrEmail&&existantPassword){
            return true;
        }
        return false;
    }

    private void createAudit(){
        String auditEvent = "Utilizatorul "+usernameTextField.getText()+" a efectuat log-in";
        String auditMoment;
        LocalDateTime moment;

        moment = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        auditMoment = moment.format(formatter);
        audit = new Audit(auditEvent,auditMoment);
    }

    /*private Runnable logOutThread = ()->{
        //ExecutorService executor = Executors.newSingleThreadExecutor();
        try{
            while(minutes <= 15)
            {
                minutes = minutes + 1;
                Thread.sleep(60000);
            }
            executor.shutdown();
            TablePage.logOut();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    };


    private void autoLogOut(){
        Thread logOutThread = new Thread(){
            @Override
            public void run(){
                try{
                    if (minutes <= 15) {
                        minutes = minutes + 1;
                        sleep(10000);
                    }
                    else{
                        TablePage.logOut();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        logOutThread.start();
    }

     */
}
