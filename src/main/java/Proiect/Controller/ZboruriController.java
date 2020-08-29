package Proiect.Controller;

import Proiect.DAO.ZboruriDAO;
import Proiect.Model.Zboruri;
import Proiect.View.AdaugaZborPage;

import java.util.ArrayList;
import java.util.List;

public class ZboruriController
{
    private static ZboruriDAO zborDAO;

    public ZboruriController(){

        zborDAO = new ZboruriDAO(DatabaseConnection.getConnection());
    }

    public boolean addZbor(Zboruri zbor){
        if(zborDAO.adaugareZbor(zbor)){
            return true;
        }
        return false;
    }

    public List<Zboruri> getZboruri(){
        return ZboruriDAO.accessFlights();
    }

    public boolean validateDestination(String sursa, String destinatie){
        return zborDAO.validareDestinatie(sursa,destinatie);
    }
}
