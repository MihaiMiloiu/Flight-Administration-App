package Proiect.View;

import Proiect.Model.Utilizatori;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.util.concurrent.Executors;

public class Main {
    public static Utilizatori currentUser;

    public static Utilizatori getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Utilizatori currentUser) {
        Main.currentUser = currentUser;
    }

    public static void main(String[] args) {
        StartPage startPage = new StartPage();
        //TablePage tablePage = new TablePage();
        //RegisterPage registerPage = new RegisterPage();
        //LoginPage loginPage = new LoginPage();
        //AdaugaZborPage adaugaZborPage = new AdaugaZborPage();
    }

    public static void initAppFromLogin(){
        LoginPage loginPage = new LoginPage();
    }

    /*public static void trackSystemEvents()
    {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
                                                        {
                                                            public void eventDispatched(AWTEvent event)
                                                            {
                                                                String eventText = event.toString();
                                                                if(eventText.indexOf(“PRESSED”) != -1 || eventText.indexOf(“RELEASED”) != -1)
                                                                {
                                                                    SessionMonitor.getInstance().setLastActionTime(System.currentTimeMillis());
                                                                }
                                                            }
                                                        }
                AWTEvent.MOUSE_EVENT_MASK + AWTEvent.KEY_EVENT_MASK);
    }

     */
}
