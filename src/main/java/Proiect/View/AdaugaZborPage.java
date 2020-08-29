package Proiect.View;

import Proiect.Controller.AuditController;
import Proiect.Controller.ZboruriController;
import Proiect.Model.Audit;
import Proiect.Model.Zboruri;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;

public class AdaugaZborPage extends JFrame {

    private String oraFinala;
    private String oraDePlecare;
    private String durataZbor;
    private String listaFinalaZile;
    private Double pretZbor;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

    private JPanel informationPanel;
    private JPanel buttonPanel;

    private JButton adaugaZborButton;
    private JButton cancelButton;

    private JLabel sursaLabel;
    private JLabel destinatieLabel;
    private JLabel oraPlecareLabel;
    private JLabel durataLabel;
    private JLabel separationLabel;
    private JLabel zileLabel;
    private JLabel pretLabel;

    private JTextField sursaTextField;
    private JTextField destinatieTextField;
    private JComboBox hourDepartureBox;
    private JComboBox minutesDepartureBox;
    private JComboBox hourDurationBox;
    private JComboBox minutesDurationBox;
    private JCheckBox luniCheckBox;
    private JCheckBox martiCheckBox;
    private JCheckBox miercuriCheckBox;
    private JCheckBox joiCheckBox;
    private JCheckBox vineriCheckBox;
    private JCheckBox sambataCheckBox;
    private JCheckBox duminicaCheckBox;
    private JTextField pretIntregTextField;
    private JTextField pretZecimaleTextField;

    ZboruriController zboruriController = new ZboruriController();
    AuditController auditController = new AuditController();

    AdaugaZborPage(){
        initDefaultProperties();
        initInformationPanel();
        initSursa();
        initDestinatie();
        initOraPlecare();
        initDurata();
        initZile();
        initPret();
        initButtonPanel();
        initMenuBar();
    }

    private void initDefaultProperties(){
        setTitle("Pagina de Adaugare a unui zbor");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initInformationPanel(){
        informationPanel = new JPanel();
        LayoutManager layout = new GridLayout(7,2);
        informationPanel.setLayout(layout);
        add(informationPanel, BorderLayout.CENTER);
    }

    private void initButtonPanel(){
        buttonPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        buttonPanel.setLayout(layout);
        adaugaZborButton = new JButton("Adauga_Zbor");
        cancelButton = new JButton("Anuleaza");
        buttonPanel.add(cancelButton);
        buttonPanel.add(adaugaZborButton);

        adaugaZborButton.addActionListener(event-> {
            if (validare()==true)
            {
                Zboruri newZbor = new Zboruri(sursaTextField.getText(),destinatieTextField.getText(),oraDePlecare,oraFinala,listaFinalaZile,pretZbor);
                if(zboruriController.addZbor(newZbor)==true) {
                    JOptionPane.showMessageDialog(null, "Zborul a fost inregistrat cu succes!");
                    String textAaudit = "Utilizatorul a inregistrat un zbor si a revenit la pagina principala";
                    createAudit(textAaudit);
                    new TablePage();
                    dispose();
                }
                //JOptionPane.showMessageDialog(null,"Eroare la adaugarea zborului in baza de date");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Zborul nu poate fi inregistrat!");
            }

        });

        cancelButton.addActionListener(event->{
            String textAudit = "Utilizatorul a anulat introducerea unui zbor si a revenit la pagina principala";
            createAudit(textAudit);
            JOptionPane.showMessageDialog(null,"Ati ales sa anulati introducerea unui zbor nou!");
            dispose();
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initSursa(){
        sursaLabel = new JLabel("Sursa:");
        EmptyBorder labelBorder = new EmptyBorder(0,50, 0,50);
        sursaLabel.setBorder(labelBorder);

        sursaTextField = new JTextField();
        informationPanel.add(sursaLabel);
        informationPanel.add(sursaTextField);

    }

    private boolean validateSursa(){
        if (sursaTextField.getText().length()>=3){
            return true;
        }
        else{
            return false;
        }
    }

    private void initDestinatie(){
        destinatieLabel = new JLabel("Destinatie:");
        EmptyBorder labelBorder = new EmptyBorder(0,50,0,50);
        destinatieLabel.setBorder(labelBorder);

        destinatieTextField = new JTextField();
        informationPanel.add(destinatieLabel);
        informationPanel.add(destinatieTextField);

    }

    private boolean validateDestination(){
        if ((destinatieTextField.getText().equals(sursaTextField.getText())==false) && (destinatieTextField.getText().length()>=3) && (zboruriController.validateDestination(sursaTextField.getText(),destinatieTextField.getText())==true)){
            return true;
        }
        else
        {
            return false;
        }
    }

    private void initOraPlecare(){
        oraPlecareLabel = new JLabel("Ora plecare:");
        EmptyBorder labelBorder = new EmptyBorder(0,50,0,50);
        oraPlecareLabel.setBorder(labelBorder);
        informationPanel.add(oraPlecareLabel);

        JPanel orePanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        orePanel.setLayout(layout);

        Integer[] ore = new Integer[24];
        for (int i = 0; i<24; i++){
            ore[i] = i;
        }
        hourDepartureBox = new JComboBox<Integer>(ore);
        orePanel.add(hourDepartureBox);

        JLabel dotsLabel = new JLabel(":");
        orePanel.add(dotsLabel);

        Integer[] minutes = new Integer[60];
        for (int i = 0; i< 60; i++)
        {
            minutes[i] = i;
        }
        minutesDepartureBox = new JComboBox<Integer>(minutes);
        orePanel.add(minutesDepartureBox);
        informationPanel.add(orePanel);
    }

    private void initDurata(){
        durataLabel = new JLabel("Durata zborului:");
        EmptyBorder labelBorder = new EmptyBorder(0,50,0,50);
        durataLabel.setBorder(labelBorder);
        informationPanel.add(durataLabel);

        JPanel durataPanel = new JPanel();
        LayoutManager layout2 = new FlowLayout();
        durataPanel.setLayout(layout2);

        Integer[] ore = new Integer[24];
        for (int i = 0; i<24; i++){
            ore[i] = i;
        }
        hourDurationBox = new JComboBox<Integer>(ore);
        durataPanel.add(hourDurationBox);

        JLabel dotsLabel = new JLabel(":");
        durataPanel.add(dotsLabel);

        Integer[] minutes = new Integer[60];
        for (int i = 0; i<60; i++)
        {
            minutes[i] = i;
        }
        minutesDurationBox = new JComboBox<Integer>(minutes);
        durataPanel.add(minutesDurationBox);
        informationPanel.add(durataPanel);
    }

    private void constructieOraSosire(){
        oraDePlecare = hourDepartureBox.getSelectedItem().toString() + ":" + minutesDepartureBox.getSelectedItem().toString();
        durataZbor = hourDurationBox.getSelectedItem().toString() + ":" + minutesDurationBox.getSelectedItem().toString();
        String[] momentPlecare = oraDePlecare.split(":");
        Integer oraMomentuluiDePlecare = Integer.parseInt(momentPlecare[0]);
        Integer minutulMomentuluiDePlecare = Integer.parseInt(momentPlecare[1]);
        String[] durata = durataZbor.split(":");
        Integer durataZboruluiInOre = parseInt(durata[0]);
        Integer durataZboruluiInMinute = parseInt(durata[1]);

        Integer oraDeSosire = oraMomentuluiDePlecare + durataZboruluiInOre;
        Integer minutulDeSosire = minutulMomentuluiDePlecare + durataZboruluiInMinute;

        /*if (durataZboruluiInMinute >= 60){
            oraDeSosire = oraDeSosire + 1;
            if (oraDeSosire > 23)
            {
                Integer diferentaOre = oraDeSosire - 24;
                oraDeSosire = 0 + diferentaOre;
            }
            else {
                Integer diferentaMinute = minutulDeSosire - 60;
                minutulDeSosire = diferentaMinute;
            }
        }
        else{
            if (oraDeSosire > 23)
            {
                Integer diferentaOre = oraDeSosire - 24;
                oraDeSosire = 0 + diferentaOre;
            }
        }*/

        if(minutulDeSosire >=60){
            oraDeSosire =  oraDeSosire + minutulDeSosire/60;
            minutulDeSosire = minutulDeSosire % 60;
        }

        if (oraDeSosire > 23){
            oraDeSosire = oraDeSosire - 24;
        }
        oraFinala = oraDeSosire.toString() + ":" + minutulDeSosire.toString();
    }

    public void initZile(){
        zileLabel = new JLabel("Zilele in care se efectueaza zborul:");
        EmptyBorder labelBorder = new EmptyBorder(0,20,0,20);
        zileLabel.setBorder(labelBorder);
        informationPanel.add(zileLabel);

        JPanel lmmPanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        lmmPanel.setLayout(layout);

        luniCheckBox = new JCheckBox("Luni");
        luniCheckBox.setSelected(false);
        lmmPanel.add(luniCheckBox);
        checkBoxes.add(luniCheckBox);

        martiCheckBox = new JCheckBox("Marti");
        martiCheckBox.setSelected(false);
        lmmPanel.add(martiCheckBox);
        checkBoxes.add(martiCheckBox);

        miercuriCheckBox = new JCheckBox("Miercuri");
        miercuriCheckBox.setSelected(false);
        lmmPanel.add(miercuriCheckBox);
        checkBoxes.add(miercuriCheckBox);

        joiCheckBox = new JCheckBox("Joi");
        joiCheckBox.setSelected(false);
        lmmPanel.add(joiCheckBox);
        checkBoxes.add(joiCheckBox);

        informationPanel.add(lmmPanel);

        JPanel emptyPanel = new JPanel();
        informationPanel.add(emptyPanel);

        JPanel jvsdPanel = new JPanel();
        jvsdPanel.setLayout(layout);

        vineriCheckBox = new JCheckBox("Vineri");
        vineriCheckBox.setSelected(false);
        jvsdPanel.add(vineriCheckBox);
        checkBoxes.add(vineriCheckBox);

        sambataCheckBox = new JCheckBox("Sambata");
        sambataCheckBox.setSelected(false);
        jvsdPanel.add(sambataCheckBox);
        checkBoxes.add(sambataCheckBox);

        duminicaCheckBox = new JCheckBox("Duminica");
        joiCheckBox.setSelected(false);
        jvsdPanel.add(duminicaCheckBox);
        checkBoxes.add(duminicaCheckBox);

        informationPanel.add(jvsdPanel);

    }

    private void constructiaListeiDeZile(){
        ArrayList<String> listaZile = new ArrayList<String>();
        listaFinalaZile = " ";
        for (JCheckBox checkBox:checkBoxes ){
            if (checkBox.isSelected()){
                listaZile.add(checkBox.getText());
            }
        }

        for (String numeZi:listaZile){
            listaFinalaZile = listaFinalaZile + numeZi + ",";
        }
    }

    private void initPret(){
        pretLabel = new JLabel("Pret:");
        EmptyBorder labelBorder = new EmptyBorder(0,50,0,50);
        pretLabel.setBorder(labelBorder);
        informationPanel.add(pretLabel);

        JPanel pricePanel = new JPanel();
        LayoutManager layout = new FlowLayout();
        pricePanel.setLayout(layout);

        pretIntregTextField = new JTextField();
        pretIntregTextField.setPreferredSize(new Dimension(60,40));
        pricePanel.add(pretIntregTextField);

        JLabel dotLabel = new JLabel(".");
        dotLabel.setPreferredSize(new Dimension(10,10));
        pricePanel.add(dotLabel);

        pretZecimaleTextField = new JTextField();
        pretZecimaleTextField.setPreferredSize(new Dimension(60,40));
        pricePanel.add(pretZecimaleTextField);

        informationPanel.add(pricePanel);
    }

    private boolean validatePrice(){
        int intPartOfPrice = Integer.parseInt(pretIntregTextField.getText());
        double decimalPartOfPrice = (Double.parseDouble(pretZecimaleTextField.getText()))/1000;
        Double pret = intPartOfPrice + decimalPartOfPrice;

        if (pret >= 0 && (pretZecimaleTextField.getText().length()==3)){
            pretZbor = pret;
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validare(){
        if ((validateDestination()==true)&&(validateSursa()==true)&&(validatePrice()==true)) {
            constructieOraSosire();
            constructiaListeiDeZile();
            return true;
        }else{
            return false;
        }
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
            String textAudit = "Utilizatorul a accesat pagina principala";
            createAudit(textAudit);
            dispose();
        });

        homeMenu.addActionListener(event->{
            String textAudit = "Utilizatorul a accesat pagina principala";
            createAudit(textAudit);
            new TablePage();
        });

        accountMenu.addActionListener(event->{
            String textAudit = "Utilizatorul a accesat pagina myAccount";
            createAudit(textAudit);
            new MyAccountPage();
        });

        logoutMenu.addActionListener(event->{
            String textAudit = "Utilizatorul s-a delogat!";
            createAudit(textAudit);
            //
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
