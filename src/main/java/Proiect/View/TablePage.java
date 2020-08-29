package Proiect.View;

import Proiect.Controller.AuditController;
import Proiect.Controller.ZboruriController;
import Proiect.DAO.ZboruriDAO;
import Proiect.Model.Audit;
import Proiect.Model.Zboruri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

public class TablePage extends JFrame {

    private JLabel lblClock;
    private JPanel dateTimePanel;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton adaugaZborButton;
    private JPanel menuPanel;

    private ZboruriController zboruriController = new ZboruriController();

    AuditController auditController = new AuditController();

    TablePage(){
        initDefaultProperties();
        clock();
        initTable();
        initMenuBar();
    }

    public void clock(){
        lblClock = new JLabel();
        Thread clockThread = new Thread(){
           public void run(){
               try {
                   while(true) {
                       Calendar cal = new GregorianCalendar();
                       int day = cal.get(Calendar.DAY_OF_MONTH);
                       int month = cal.get(Calendar.MONTH);
                       int year = cal.get(Calendar.YEAR);

                       int second = cal.get(Calendar.SECOND);
                       int minute = cal.get(Calendar.MINUTE);
                       int hour = cal.get(Calendar.HOUR);


                       lblClock.setText("Timp " + hour + ":" + minute + ":" + second + "  Data" + day + "/" + month + "/" + year);

                       sleep(1000);
                       /*
                       sau cu LocalDateTime.now() iar apoi aplicare de format DateTimeFormatter format = DateTimeFormatter.ofPattern(..);
                        */
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        };

        clockThread.start();
        dateTimePanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        dateTimePanel.setLayout(layout);

        dateTimePanel.add(lblClock);
        add(dateTimePanel, BorderLayout.SOUTH);
    }

    private void initTable(){
        mainPanel = new JPanel();
        LayoutManager layout = new GridLayout(2,1);
        mainPanel.setLayout(layout);

        scrollPane = new JScrollPane();
        JPanel buttonPanel = new JPanel(new FlowLayout());

        String[] campuri = {"Sursa","Destinatie","oraPlecare","oraSosire","listaZile","Pret"};
        List<Zboruri> listaZboruri = zboruriController.getZboruri();
        Object[][] dateTabel = new Object[15][6];

        for (int i = 0; i < listaZboruri.size(); i++){
            Zboruri zbor = listaZboruri.get(i);
            dateTabel[i][0] = zbor.getSursa();
            dateTabel[i][1] = zbor.getDestinatie();
            dateTabel[i][2] = zbor.getOraPlecare();
            dateTabel[i][3] = zbor.getOraSosire();
            dateTabel[i][4] = zbor.getListaZile();
            dateTabel[i][5] = zbor.getPret();
        }

        table = new JTable(dateTabel, campuri);
        scrollPane.add(table);
        mainPanel.add(scrollPane);

        JButton stergereButton = new JButton("Stergere Inregistrari");
        buttonPanel.add(stergereButton);

        adaugaZborButton = new JButton("Adauga zbor");
        buttonPanel.add(adaugaZborButton);
        adaugaZborButton.addActionListener(event ->{
            String textAudit = "Utilizatorul a accesat pagina de adaugare a unui zbor nou";
            createAudit(textAudit);
            //JOptionPane.showMessageDialog(null,"Audit creat cu succes");
            new AdaugaZborPage();
            //dispose();
        });
        mainPanel.add(buttonPanel);
        add(mainPanel,BorderLayout.CENTER);
    }

    private void initDefaultProperties(){
        setTitle("Pagina principala");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected void initMenuBar(){
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
            String textAudit = "Utilizatorul a revenit la pagina de LogIn";
            createAudit(textAudit);
            dispose();
        });

        accountMenu.addActionListener(event->{
            //createAuditMyAccount();
            String textAudit = "Utilizatorul a accesat pagina myAccount";
            createAudit(textAudit);
            new MyAccountPage();

        });

        logoutMenu.addActionListener(event->{
            //createAuditLogOut();
            String textAudit = "Utilizatorul s-a delogat!";
            createAudit(textAudit);
            //dispose();
            logOut();
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

    public static void logOut(){
        JOptionPane.showMessageDialog(null,"V-ati delogat!");
        //service.
        System.exit(0);
        //Main.initAppFromLogin();
    }
}
