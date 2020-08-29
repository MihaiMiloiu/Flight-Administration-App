package Proiect.DAO;

import Proiect.Model.Audit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditDAO {
    private Connection connection;
    private static PreparedStatement insertAuditStatement;

    public AuditDAO(Connection connection){
        this.connection = connection;
        try{
            insertAuditStatement = connection.prepareStatement("INSERT INTO audit VALUES (?,?)");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean insertAudit(Audit audit){
        try{
            insertAuditStatement.setString(1,audit.getEvent());
            insertAuditStatement.setString(2,audit.getDateAndTime());

            return insertAuditStatement.executeUpdate() != 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
