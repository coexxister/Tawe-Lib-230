import java.sql.SQLException;

public class amTester {

    public static void main(String[] args) throws SQLException {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");

        AccountManager rm = new AccountManager(dbManager);
/*
        User testUser = new User(92372, "parisskopelitis", "Paris", "Skopelitis", "07403576267", "27", "Ernald Place", "Uplands", "Swansea", "SA2 OHN", 1);
        Staff testStaff = new Staff(92372, "parisskopelitis", "Paris", "Skopelitis", "07403576267", "27", "Ernald Place", "Uplands", "Swansea", "SA2 OHN", "1999-07-26", 95568, 1);
*/

        /*rm.addAccount(testUser);
        rm.addAccount(testStaff);*/

        System.out.println(rm.getAccount(2).toString());



    }

}
