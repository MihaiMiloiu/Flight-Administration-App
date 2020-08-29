package Proiect.Controller;

import Proiect.DAO.AuditDAO;
import Proiect.Model.Audit;

public class AuditController {
    private static AuditDAO auditDAO;
    public AuditController(){
        auditDAO = new AuditDAO(DatabaseConnection.getConnection());
    }

    public static boolean insertAudit(Audit audit){
        return auditDAO.insertAudit(audit);
    }
}
