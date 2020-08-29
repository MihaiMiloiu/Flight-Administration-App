package Proiect.Model;

import java.lang.reflect.Array;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Zboruri {
    //private int Id_zbor;
    private String Sursa;
    private String Destinatie;
    private String oraPlecare;
    private String oraSosire;
    private String listaZile;
    private Double pret;

    public Zboruri(String sursa, String destinatie, String oraPlecare, String oraSosire, String listaZile, Double pret) {
        //this.Id_zbor = Id_zbor;
        this.Sursa = sursa;
        this.Destinatie = destinatie;
        this.oraPlecare = oraPlecare;
        this.oraSosire = oraSosire;
        this.listaZile = listaZile;
        this.pret = pret;
    }

    /*public int getId_zbor() {
        return Id_zbor;
    }

    public void setId_zbor(int id_zbor) {
        Id_zbor = id_zbor;
    }
     */
    public String getSursa() {
        return Sursa;
    }

    public void setSursa(String sursa) {
        Sursa = sursa;
    }

    public String getDestinatie() {
        return Destinatie;
    }

    public void setDestinatie(String destinatie) {
        Destinatie = destinatie;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public String getOraSosire() {
        return oraSosire;
    }

    public void setOraSosire(String oraSosire) {
        this.oraSosire = oraSosire;
    }

    public String getListaZile() {
        return listaZile;
    }

    public void setListaZile(String listaZile) {
        this.listaZile = listaZile;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zboruri zboruri = (Zboruri) o;
        return Sursa == zboruri.Sursa &&
                Destinatie == zboruri.Destinatie &&
                Objects.equals(oraPlecare, zboruri.oraPlecare) &&
                Objects.equals(oraSosire, zboruri.oraSosire) &&
                Objects.equals(listaZile, zboruri.listaZile) &&
                Objects.equals(pret, zboruri.pret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Sursa, Destinatie, oraPlecare, oraSosire, listaZile, pret);
    }

    @Override
    public String toString() {
        return "Zboruri{" +
                "Sursa=" + Sursa +
                ", Destinatie=" + Destinatie +
                ", oraPlecare=" + oraPlecare +
                ", oraSosire=" + oraSosire +
                ", listaZile=" + listaZile +
                ", pret=" + pret +
                '}';
    }
}

