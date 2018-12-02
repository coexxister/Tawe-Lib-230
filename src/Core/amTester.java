package Core;

import java.sql.SQLException;

public class amTester {

    public static void main(String[] args) throws SQLException {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");

        AccountManager rm = new AccountManager(dbManager);

        User testUser = new User(1, "Paris", "Skopelitis", "07403576267", "27", "Ernald Place", "Uplands", "Swansea", "SA2 OHN", 1);
        Staff testStaff = new Staff(2, "Noah", "Lenagan", "07403576267", "27", "Ernald Place", "Uplands", "Swansea", "SA2 OHN", "1999-07-26", 1, 5);


        /*rm.addAccount(testUser);*/
        rm.addAccount(testStaff);

        /*rm.editAccount(testStaff);
        System.out.println(rm.getAccount(2).toString());*/

        /*rm.changeBalance(1, 3.679F);

        System.out.println(rm.getAccountBalance(2));

        Transaction[] transactions = rm.getTransactionHistory(1);

        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }*/

        //rm.changeBalance(2,10000);
        //rm.deleteAccount(2);

    }

}
