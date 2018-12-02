package Core;

import java.sql.SQLException;

public class rfmManager {

    public static void main(String[] args) throws SQLException {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");
        ResourceManager rmManager = new ResourceManager(dbManager);
        AccountManager acManager = new AccountManager(dbManager);

        ResourceFlowManager rfm = new ResourceFlowManager(dbManager, acManager, rmManager, 1);

        Copy copy = new Copy(1, 9, 14, "", 0, 0);

        //System.out.println(rfm.calculateFine(rmManager.getCopies(1)[0]));

        //rmManager.addBulkCopies(copy);
        //rfm.borrowCopy(38,1);
        //rfm.requestResource(9,3);
        rfm.returnCopy(38, 1);

        //rfm.returnCopy(21,1);

    }

}
