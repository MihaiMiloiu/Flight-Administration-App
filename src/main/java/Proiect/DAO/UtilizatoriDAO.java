package Proiect.DAO;

import Proiect.Model.Utilizatori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilizatoriDAO {
    private Connection connection;
    private static PreparedStatement registerStatement;
    private static PreparedStatement findByUsername;
    private static PreparedStatement findByPassword;
    private static PreparedStatement findByEmail;
    private static PreparedStatement findStatement;
    private static PreparedStatement updateUsernameStatement;
    private static PreparedStatement updateEmailStatement;
    private static PreparedStatement updatePasswordStatement;
    private static PreparedStatement updateConfirmationPasswordStatement;

    public UtilizatoriDAO(Connection connection) {
        this.connection = connection;

        try {
            //Inregistrare utilizator nou
            registerStatement = connection.prepareStatement("INSERT INTO utilizatori VALUES (?,?,?,?)");
            //Query pentru validarea username-ului utilizatorului nou, dar si pt login
            findByUsername = connection.prepareStatement("SELECT * FROM utilizatori WHERE username = ?");
            //Folosit la login
            findByPassword = connection.prepareStatement("SELECT password FROM utilizatori WHERE  password = ?");
            //Folosit la login
            findByEmail = connection.prepareStatement("SELECT email FROM utilizatori WHERE email = ?");
            findStatement = connection.prepareStatement("SELECT * FROM utilizatori WHERE username = ? AND password = ?");
            //Update-uri
            updateUsernameStatement = connection.prepareStatement("UPDATE utilizatori SET username = ? WHERE username = ?");
            updateEmailStatement = connection.prepareStatement("UPDATE utilizatori SET email = ? WHERE email = ?");
            updatePasswordStatement = connection.prepareStatement("UPDATE utilizatori SET password = ? WHERE password = ?");
            updateConfirmationPasswordStatement = connection.prepareStatement("UPDATE utilizatori SET confirmPassword = ? WHERE confirmPassword = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean createUtilizator(Utilizatori user) {
        try {
            registerStatement.setString(1, user.getUsername());
            registerStatement.setString(2, user.getPassword());
            registerStatement.setString(3, user.getEmail());
            registerStatement.setString(4, user.getConfirmPassword());
            return registerStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean findByUsername(String username){
        try {
            findByUsername.setString(1, username);
            ResultSet result = findByUsername.executeQuery();

            /*if (!result.next()){
                return true;
            }

            result.beforeFirst();*/
            List<Utilizatori> list = new ArrayList<>();
            while(result.next()){
                Utilizatori user = new Utilizatori(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getString("confirmPassword")
                );
                list.add(user);
            }

            if (list.size()==0){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean findByEmail(String email){
        try{
            findByEmail.setString(1, email);
            ResultSet result = findByEmail.executeQuery();

            /*if(!result.next()){
                return true;
            }

            result.beforeFirst();*/

            List<Utilizatori> list = new ArrayList<>();
            while(result.next()){
                Utilizatori user = new Utilizatori(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getString("confirmPassword")
                );
                list.add(user);
            }

            if (list.size()==0){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean findByPassword(String password){
        try{
            findByPassword.setString(1,password);
            ResultSet result = findByPassword.executeQuery();

            /*if(!result.next()){
                return true;
            }
            result.beforeFirst();*/
            List<Utilizatori> list = new ArrayList<>();
            while(result.next()){
                Utilizatori user = new Utilizatori(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getString("confirmPassword")
                );
                list.add(user);
            }

            if (list.size()==0){
                return true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Utilizatori findCurrentUser(String username, String password){
        try{
           findStatement.setString(1,username);
           findStatement.setString(2,password);

           ResultSet result = findStatement.executeQuery();

           while(result.next()){
               Utilizatori utilizatorCurent = new Utilizatori(
                       result.getString("username"),
                       result.getString("password"),
                       result.getString("email"),
                       result.getString("confirmPassword")
               );
               return utilizatorCurent;
           }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return new Utilizatori();
    }

    public boolean updateUsername(String newUsername, String oldUsername){
        try{
            updateUsernameStatement.setString(1,newUsername);
            updateUsernameStatement.setString(2,oldUsername);

            return updateUsernameStatement.executeUpdate() != 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmail(String newEmail, String oldEmail){
        try{
            updateEmailStatement.setString(1,newEmail);
            updateEmailStatement.setString(2,oldEmail);

            return updateEmailStatement.executeUpdate() != 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String newPassword, String oldPassword){
        try{
            updatePasswordStatement.setString(1,newPassword);
            updatePasswordStatement.setString(2,oldPassword);

            return updatePasswordStatement.executeUpdate() != 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateConfirmationPassword(String newConfirmPass, String oldConfirmPass){
        try{
            updateConfirmationPasswordStatement.setString(1,newConfirmPass);
            updateConfirmationPasswordStatement.setString(2,oldConfirmPass);

            return updateConfirmationPasswordStatement.executeUpdate() != 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
