import java.sql.SQLException;

/**
 * Responsible for performing operations on accounts.
 * @author Noah Lenagan, Paris, Paris Kelly Skopelitis
 * @version 1.0
 */
public class AccountManager {

    /**
     * An instance of DatabaseManager.
     */
    DatabaseManager dbManager;

    AccountManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /*
    TODO

    + getCurrentAccount() : User
    + getAccount (userID : Integer) : User
    + editAccount (userID : Integer, newAccount : User) : Boolean
    + changeBalance (userID : Integer, money: Float) : Boolean
    + deleteAccount (userID : Integer)
    + checkBalance() : Boolean
    + getTransactionHistory(userID : User) : String[][]


     */


    /**
     * Adds a user account to the database and return its user id.
     * @param newAccount The account to add.
     * @return The user id of the newly added account.
     * @throws SQLException Thrown if connection to the database failed or the arguments were invalid.
     */
    public int addAccount(User newAccount) throws SQLException {

        //add account to the database.
        dbManager.addTuple("User", new String[]{"null", encase(newAccount.getFirstName()), encase(newAccount.getLastName()),
                encase(newAccount.getTelNum()), encase(newAccount.getStreetNum()), encase(newAccount.getStreetName()),
                encase(newAccount.getCounty()), encase(newAccount.getPostCode()), encase(newAccount.getCity()),
                Integer.toString(newAccount.getAvatarID()), "0"});

        //get the user id, as the user id is an auto incrementing primary key. userID will be the max value.
        int userID = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT max(UID) FROM User")[0]);

        return userID;

    }

    /**
     * Adds a staff account to the database and returns its user id and staff id.
     * @param newAccount The staff account to add.
     * @return An array containing the user and staff id.
     * @throws SQLException Thrown if connection to the database failed or the arguments were invalid.
     */
    public int[] addAccount(Staff newAccount) throws SQLException {

        //get the user id, and add basic user details
        int userID = addAccount((User)newAccount);

        //add staff details
        dbManager.addTuple("Staff", new String[]{"null", Integer.toString(userID),
                encase(newAccount.getEmploymentDate())});

        //int get staff id;
        int staffID = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT max(SID) FROM Staff")[0]);

        return new int[] {userID, staffID};

    }

    /**
     * Constructs and returns a user from the database specified by the user id.
     * @param userID The user id of the user to return.
     * @return The constructed user.
     * @throws IllegalArgumentException Thrown if the specified user does not exist.
     */
    public User getAccount(int userID) throws IllegalArgumentException {

        try {
            //check if user exists
            if (dbManager.checkIfExist("User", new String[] {"UID"},
                    new String[] {Integer.toString(userID)})) {

                //get user details
                String[] userRow = dbManager.getFirstTupleByQuery("SELECT * FROM User WHERE UID = " +
                        Integer.toString(userID));

                //check if user is a staff member
                boolean isStaff = dbManager.checkIfExist("Staff", new String[] {"UID"},
                        new String[] {Integer.toString(userID)});

                User user;

                //get staff details and construct staff object if staff member, otherwise construct user object
                if (isStaff) {

                    String[] staffRow = dbManager.getFirstTupleByQuery("SELECT * FROM Staff WHERE UID = " +
                            Integer.toString(userID));

                    user = new Staff(Integer.parseInt(userRow[0]), userRow[1], userRow[2], userRow[3], userRow[4],
                            userRow[5], userRow[6], userRow[8], userRow[7], staffRow[2],
                            Integer.parseInt(staffRow[0]), Integer.parseInt(userRow[9]));

                } else {
                    user = new User(Integer.parseInt(userRow[0]), userRow[1], userRow[2], userRow[3], userRow[4],
                            userRow[5], userRow[6], userRow[8], userRow[7], Integer.parseInt(userRow[9]));
                }

                return user;

            } else {
                throw new IllegalArgumentException("Specified user does not exist");
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Encases a string in apostrophe marks.
     * @param str The string to encase.
     * @return The encased string.
     */
    public String encase(String str) {
        return "'" + str + "'";
    }

}
