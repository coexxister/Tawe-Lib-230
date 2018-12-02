package Core;

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

    public AccountManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /*
    TODO
    + deleteAccount (userID : Integer)
     */

    /**
     * Deletes an account.
     * @param userID The user id of the account to delete.
     * @throws IllegalArgumentException Thrown if the specified user does not exist.
     * @throws SQLException Thrown if connection to the database failed or the table doesn't exist.
     * @throws IllegalStateException Thrown if the user has fines to pay.
     */
    public void deleteAccount(int userID) throws IllegalArgumentException, SQLException, IllegalStateException {

        //check if account exists
        if (dbManager.checkIfExist("User", new String[]{"UID"}, new String[]{Integer.toString(userID)})) {

            //check if account balance is greater or equal to 0
            if (getAccountBalance(userID) >= 0.0F) {

                //check if user has borrowed any borrowed books
                if (dbManager.getFirstTupleByQuery("SELECT count(*) FROM Copy WHERE UID = " +
                        Integer.toString(userID))[0].equals("0")) {

                    //remove from request queue
                    dbManager.sqlQuery("DELETE FROM ResourceRequestQueue WHERE UID = " + Integer.toString(userID));

                    //remove any reserved resources
                    String[][] copies = dbManager.getTupleListByQuery("SELECT CPID, RID FROM " +
                            "Copy WHERE UID = " + Integer.toString(userID));

                    //if there are reserved copies, then add them to the availability queue.
                    if (copies.length > 0) {
                        //remove reserved copies from ReservedResource table.
                        dbManager.deleteTuple("ReservedResource", new String[]{"UID"},
                                new String[]{Integer.toString(userID)});

                        //get queue tail
                        String tail = dbManager.getFirstTupleByQuery("SELECT TailOfAvailableQueue FROM " +
                                "Resource WHERE RID = " + copies[0][1])[0];

                        //If the tail is null then set the head and tail, otherwise make the tail point to the first copy.
                        if (tail == null) {
                            //set head and tail
                            dbManager.editTuple("Resource",
                                    new String[]{"HeadOfAvailableQueue", "HeadOfAvailableQueue"},
                                    new String[]{copies[0][0], copies[0][0]}, "RID", copies[0][1]);
                        } else {
                            //Make the tail point to the first copy.
                            dbManager.editTuple("Copy", new String[]{"NextCopyInQueue"},
                                    new String[]{copies[0][0]}, "CPID", tail);
                        }

                        //for every reserved copy make it available
                        for (int iCount = 1; iCount < copies.length; iCount++) {
                            //set the previous copy to point to this copy.
                            dbManager.editTuple("Copy", new String[]{"NextCopyInQueue"},
                                    new String[]{copies[iCount][0]}, "CPID", tail);

                            //if last copy to make available, then must be the new tail
                            if (iCount == copies.length - 1) {
                                dbManager.editTuple("Resource", new String[]{"TailOfAvailableQueue"},
                                        new String[]{copies[iCount][0]}, "RID", copies[iCount][1]);
                            }

                        }

                    }

                    //delete transaction history
                    dbManager.deleteTuple("TransactionHistory",
                            new String[]{"UID"}, new String[]{Integer.toString(userID)});

                    //delete resource request history
                    dbManager.deleteTuple("ResourceRequestHistory",
                            new String[]{"UID"}, new String[]{Integer.toString(userID)});

                    //delete borrow history
                    dbManager.deleteTuple("BorrowHistory",
                            new String[]{"UID"}, new String[]{Integer.toString(userID)});

                    //check if staff
                    boolean isStaff = dbManager.checkIfExist("User", new String[]{"UID"},
                            new String[]{Integer.toString(userID)});

                    //if staff, then delete staff details
                    if (isStaff) {
                        //delete staff details
                        dbManager.deleteTuple("Staff",
                                new String[]{"UID"}, new String[]{Integer.toString(userID)});
                    }

                    //delete account
                    dbManager.deleteTuple("User",
                            new String[]{"UID"}, new String[]{Integer.toString(userID)});

                }

            } else {
                throw new IllegalStateException("User must pay fines to delete account.");
            }

        } else {
            throw new IllegalArgumentException("Specified user does not exist");
        }

    }

    /**
     * Changes the balance of an account.
     * @param userID The user id of the account.
     * @param money The change in balance.
     * @throws IllegalArgumentException Thrown when the specified user does not exist.
     * @throws SQLException Thrown if connection to database fails or table does not exist.
     */
    public void changeBalance(int userID, float money) throws IllegalArgumentException, SQLException{

        //get the account balance, IllegalArgumentException is thrown if user does not exist.
        float accountBalance = getAccountBalance(userID);

        //transaction
        accountBalance += money;

        //change balance in user table.
        dbManager.editTuple("User", new String[] {"Current_Balance"},
                new String[] {Float.toString(accountBalance)}, "UID", Integer.toString(userID));

        //create transaction/
        Transaction transaction = new Transaction(userID, "WAITING FOR ALBERTO", money);

        //add the transaction to the history.
        addTransaction(transaction);

    }

    /**
     * Adds a transaction to history.
     * @param transaction The transaction object.
     * @throws SQLException Thrown if connection to database fails or table does not exist.
     */
    public void addTransaction(Transaction transaction) throws SQLException {

        dbManager.addTuple("TransactionHistory", new String[] {Integer.toString(transaction.getUserID()),
            encase(transaction.getDate()), Float.toString(transaction.getChange())});

    }

    /**
     * Gets the account balance of an account.
     * @param userID The user id of the account.
     * @return The account balance.
     * @throws IllegalArgumentException Thrown if the specified user does not exist.
     * @throws SQLException Thrown if connection to the database failed or tables do not exist.
     */
    public float getAccountBalance(int userID) throws IllegalArgumentException, SQLException {

        //check if user exists
        if (dbManager.checkIfExist("User", new String[]{"UID"}, new String[]{Integer.toString(userID)})) {

            //get the balance
            return Float.parseFloat(dbManager.getFirstTupleByQuery("SELECT Current_Balance FROM User WHERE UID = " +
                    Integer.toString(userID))[0]);

        } else {
            throw new IllegalArgumentException("User specified does not exist.");
        }

    }

    /**
     * Get all transactions from a specific account.
     * @param userID The user id of the account.
     * @return An array of transactions.
     * @throws IllegalArgumentException Thrown if the specified user does not exist.
     */
    public Transaction[] getTransactionHistory(int userID) throws IllegalArgumentException {

        try {
            //if user exists then get transactions
            if (dbManager.checkIfExist("User", new String[]{"UID"}, new String[]{Integer.toString(userID)})) {

                //get amount of transactions
                int amountOfTransactions = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT count(*) FROM " +
                        "TransactionHistory WHERE UID = " + Integer.toString(userID))[0]);

                //create array
                Transaction[] transactions = new Transaction[amountOfTransactions];

                //get transactions
                String[][] transRows = dbManager.getTupleListByQuery("SELECT * FROM TransactionHistory WHERE UID = " +
                        Integer.toString(userID));

                //for every transaction construct a transaction
                for (int iCount = 0; iCount < amountOfTransactions; iCount++) {

                    //construct transaction
                    transactions[iCount] = new Transaction(userID, transRows[iCount][1],
                            Float.parseFloat(transRows[iCount][2]));

                }

                return transactions;

            } else {
                throw new IllegalArgumentException("User specified does not exist");
            }
        } catch(SQLException e) {
            return null;
        }

    }

    /**
     * Edits/replaces an existing user account with the new data.
     * @param user The new user data to add to the account.
     * @throws SQLException Thrown if connection to the database fails or tables do not exist.
     */
    public void editAccount(User user) throws SQLException {

        //Edit user details
        dbManager.editTuple("User", new String[] {"First_Name", "Last_Name", "Phone_Number",
                "Address1", "Address2", "County", "Postcode", "City", "ImageID"},
                new String[] {encase(user.getFirstName()),
                        encase(user.getLastName()), encase(user.getTelNum()), encase(user.getStreetNum()),
                        encase(user.getStreetName()), encase(user.getCounty()), encase(user.getPostCode()),
                        encase(user.getCity()), Integer.toString(user.getAvatarID())},"UID",
                        Integer.toString(user.getUserID()));

    }

    /**
     * Edits/replaces an existing staff account with the new data.
     * @param staff The new staff data to add to the account.
     * @throws SQLException Thrown if connection to the database fails or tables do not exist.
     */
    public void editAccount(Staff staff) throws SQLException {

        //Edit basic user details first
        editAccount((User)staff);

        //Edit staff details
        dbManager.editTuple("Staff", new String[] {"Employment_Date"},
                new String[] {encase(staff.getEmploymentDate())}, "SID", Integer.toString(staff.getStaffNum()));

    }

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
