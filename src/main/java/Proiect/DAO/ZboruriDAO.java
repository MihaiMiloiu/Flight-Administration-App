package Proiect.DAO;

import Proiect.Model.Zboruri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ZboruriDAO {
    private Connection connection;
    private static PreparedStatement selectStatement;
    private static PreparedStatement validareDestinatieStatement;
    private static PreparedStatement insertStatement;

    public ZboruriDAO(Connection connection){
        this.connection = connection;
        try {
            selectStatement = connection.prepareStatement("SELECT * FROM zboruri");
            //findBySursaStatement = connection.prepareStatement("SELECT Sursa FROM zboruri");
            validareDestinatieStatement = connection.prepareStatement("SELECT Sursa,Destinatie FROM zboruri WHERE Sursa = ? AND Destinatie = ?");
            insertStatement = connection.prepareStatement("INSERT INTO zboruri VALUES (?,?,?,?,?,?)");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Zboruri> accessFlights(){
        try {
            ResultSet result = selectStatement.executeQuery();
            List<Zboruri> listZboruri = new ArrayList<>();

            while(result.next()){
                Zboruri zbor = new Zboruri(
                        result.getString("Sursa"),
                        result.getString("Destinatie"),
                        result.getString("OraDePlecare"),
                        result.getString("OraDeSosire"),
                        result.getString("Zile"),
                        result.getDouble("Pret")
                );
                listZboruri.add(zbor);
            }
            return listZboruri;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static boolean validareDestinatie(String sursaZbor, String destinatieZbor){
        try {
            validareDestinatieStatement.setString(1,sursaZbor);
            validareDestinatieStatement.setString(2,destinatieZbor);

            ResultSet result = validareDestinatieStatement.executeQuery();

            if (!result.next()){
                return true;
            }
            result.beforeFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean adaugareZbor(Zboruri zbor){
        try {
            insertStatement.setString(1, zbor.getSursa());
            insertStatement.setString(2,zbor.getDestinatie());
            insertStatement.setString(3,zbor.getOraPlecare());
            insertStatement.setString(4,zbor.getOraSosire());
            insertStatement.setString(5,zbor.getListaZile());
            insertStatement.setDouble(6,zbor.getPret());

            return insertStatement.executeUpdate()!= 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

