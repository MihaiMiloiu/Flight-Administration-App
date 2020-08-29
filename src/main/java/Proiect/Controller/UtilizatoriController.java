package Proiect.Controller;

import Proiect.DAO.UtilizatoriDAO;
import Proiect.Model.Utilizatori;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilizatoriController {

    static final class SingletonHolder{
        public static final UtilizatoriController INSTANCE = new UtilizatoriController();
    }

    private static UtilizatoriDAO userDAO;

    UtilizatoriController(){
        userDAO = new UtilizatoriDAO(DatabaseConnection.getConnection());
    }

    public static UtilizatoriController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /*public static boolean validatePassword(String password)
    {
        boolean hasNumber = false;
        boolean hasCapital = false;
        boolean hasLowerCase = false;
        char c;

        if (password.length() >= 6)
        {
            for (int i = 0; i < password.length(); i++)
            {
                c = password.charAt(i);

                if (Character.isDigit(c))
                {
                    hasNumber = true;
                }
                else
                {
                    if (Character.isUpperCase(c))
                    {
                        hasCapital = true;
                    }
                    else
                    {
                        if (Character.isLowerCase(c))
                        {
                            hasLowerCase = true;
                        }
                    }
                }
                if (hasNumber && hasCapital && hasLowerCase)
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            return false;
        }
    }*/

    public static boolean validatePassword(String password){
        String regex ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
        return password.matches(regex);
    }

    public static boolean validateUsername(String username){
        //returneaza true daca lista cu utilizatorii gasiti cu acel username este goala
        //deci practic returneaza true daca usernameul nu exista
        if (UtilizatoriDAO.findByUsername(username) == true){
            return true;
        }
        return false;
    }

    public static boolean confirmationPassword(String password, String confirmationPassword) {
        //if (password == confirmationPassword)
        if(password.equals(confirmationPassword))
        {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String adresaEmail)
    {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(adresaEmail);

        if (matcher.matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean createUtilizator(Utilizatori utilizator){
        if(userDAO.createUtilizator(utilizator)){
            return true;
        }
        return false;
    }

    public static boolean verificareExistentaUsername(String username){
        if(userDAO.findByUsername(username)){
            return true;
        }
        return false;
    }

    public static boolean verificarePotrivireEmail(String email){
        if(userDAO.findByEmail(email)){
            return true;
        }
        return false;
    }

    public static boolean verificarePotrivireParola(String parola){
        if(!(userDAO.findByPassword(parola))){
            return true;
        }
        return false;
    }

    public static Utilizatori utilizatorCurent(String username, String password){
        return userDAO.findCurrentUser(username,password);
    }

    public boolean actualizareUsername(String newUsername, String oldUsername){
        return userDAO.updateUsername(newUsername, oldUsername);
    }

    public boolean actualizareEmail(String newEmail, String oldEmail){
        return userDAO.updateEmail(newEmail, oldEmail);
    }

    public boolean actualizareParola(String newPassword, String oldPassword){
        return userDAO.updatePassword(newPassword, oldPassword);
    }

    public boolean actualizareConfirmareParola(String newConfirmPass, String oldConfirmPass){
        return userDAO.updateConfirmationPassword(newConfirmPass, oldConfirmPass);
    }
}
