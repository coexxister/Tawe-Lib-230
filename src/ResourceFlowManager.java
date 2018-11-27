
public class ResourceFlowManager {

    /*


    - dbManager : DatabaseManager
    - accountManager : AccountManager
    - resourceManager : ResourceManager
    + ResourceFlowManager (dbManager : DataBaseManager, accountManager: AccountManager)
    + returnCopy (copy : Copy, user : User) : Bool
    + returnCopy (copyID : Integer, userID : Integer) : Bool
    + borrowCopy (copy : Copy, user : User) : Bool
    + borrowCopy (copyID : Integer, userID : Integer) : Bool
    + requestCopy (copy : Copy, user : User) : Bool
    + requestCopy (copyID : Integer, userID : Integer) : Bool
    - calculateFine (copy : Copy) : Float
    + showOverdueCopies() : Copy[]
    + getBorrowedCopies (userID: Integer) : Copy[]
    + getBorrowHistory (userID: Integer) : String[][]
    - setCopyAvailability (isAvailable : Boolean, userID, Integer)


     */

    /**
     * An instance of database manager.
     */
    private DatabaseManager dbManager;

    /**
     * An instance of account manager.
     */
    private AccountManager acManager;

    /**
     * Creates the ResourceFlowManager
     * @param dbManager The database manager instance.
     * @param acManager The account manager instance.
     */
    public ResourceFlowManager(DatabaseManager dbManager, AccountManager acManager) {

        this.dbManager = dbManager;
        this.acManager = acManager;

    }

    

}
