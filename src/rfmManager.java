import java.sql.SQLException;

public class rfmManager {

    public static void main(String[] args) throws SQLException {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");
        ResourceManager rmManager = new ResourceManager(dbManager);
        AccountManager acManager = new AccountManager(dbManager);

        ResourceFlowManager rfm = new ResourceFlowManager(dbManager, acManager, rmManager, 1);

        Copy copy = new Copy(1, 1, 14, "2018-12-6", 0, 0);

//        System.out.println(rfm.calculateFine(rmManager.getCopies(1)[0]));
        //System.out.println(DateManager.returnCurrentDate());

    }

}
