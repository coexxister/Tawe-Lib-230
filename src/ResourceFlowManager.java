import java.sql.SQLException;

/**
 * Manages operations that relate resources and users.
 * @author Noah Lenagan, Paris Kelly Skopelitis
 * @version 1.0
 */
public class ResourceFlowManager {

    /*
       TODO

    + returnCopy (copy : Copy, user : User) : Bool
    + returnCopy (copyID : Integer, userID : Integer) : Bool
    + borrowCopy (copy : Copy, user : User) : Bool
    + borrowCopy (copyID : Integer, userID : Integer) : Bool
    + requestCopy (copy : Copy, user : User) : Bool
    + requestCopy (copyID : Integer, userID : Integer) : Bool
    + showOverdueCopies() : Copy[]
    + getBorrowedCopies (userID: Integer) : Copy[]
    + getBorrowHistory (userID: Integer) : String[][]

    vvv note if de-queued, copy is left in state -1 vvv
    vvv if enqueued the copy must be in state -1    vvv

    - dequeue available
    - enqueue borrowed
    - dequeue borrowed
    - reserve
    - un reserve



     */

    /**
     * The user id of the account.
     */
    private int userID;

    /**
     * An instance of database manager.
     */
    private DatabaseManager dbManager;

    /**
     * An instance of account manager.
     */
    private AccountManager acManager;

    /**
     * An instance of resource manager.
     */
    private ResourceManager rmManager;

    /**
     * Creates the ResourceFlowManager
     * @param dbManager The database manager instance.
     * @param acManager The account manager instance.
     * @param rmManager The resource manager instance.
     */
    public ResourceFlowManager(DatabaseManager dbManager, AccountManager acManager,
                               ResourceManager rmManager, int userID) {

        this.dbManager = dbManager;
        this.acManager = acManager;
        this.rmManager = rmManager;
        this.userID = userID;

    }

    /**
     * Calculates the fine of a copy.
     * @param copy The copy to calculate the fine of.
     * @return The calculated fine amount.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     * @throws IllegalStateException Thrown if the copy is not on loan.
     */
    private float calculateFine(Copy copy) throws SQLException, IllegalStateException {

        //calculate the days before or after the due date.
        int daysOffset = copy.calculateDaysOffset(DateManager.returnCurrentDate());
        float fine = 0;

        //if the copy is days after the due date then calculate fine otherwise return 0
        if (daysOffset < 0) {

            //get resource type fine data
            String[] resourceFineStat = dbManager.getFirstTupleByQuery("SELECT * FROM Fine, (SELECT TID FROM Resource" +
                    " WHERE RID = " + Integer.toString(copy.getResourceID()) + ") as T2 WHERE Fine.TID = T2.TID");

            //convert data to floats
            float dailyFine = Float.parseFloat(resourceFineStat[1]);
            float maxFine = Float.parseFloat(resourceFineStat[2]);

            //calculate fine
            fine = dailyFine * daysOffset;

            //If the (-)fine is larger than the (-)maxFine then cap the fine.
            if (fine < -maxFine) {
                fine = -maxFine;
            }
        }

        return fine;

    }

    /**
     * Makes a copy available. The copy must not already be available, on loan or reserved.
     * @param copy The copy to make available.
     * @throws IllegalStateException Thrown if copy is available, on loan or reserved.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void enqueueAvailable(Copy copy) throws IllegalStateException, SQLException {

        //enqueue if not borrowed, reserved or available, otherwise throw a illegal state exception.
        //state of -1 represents an undefined state of the copy.
        if (copy.getStateID() == -1) {

            String resourceID = Integer.toString(copy.getResourceID());
            String copyID = Integer.toString(copy.getCopyID());

            //get queue tail
            String tail = dbManager.getFirstTupleByQuery("SELECT TailOfAvailableQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //If the tail is null then set the head and tail, otherwise make the tail point to the copy.
            if (tail == null) {
                //set head and tail
                dbManager.editTuple("Resource",
                        new String[]{"HeadOfAvailableQueue", "HeadOfAvailableQueue"},
                        new String[]{copyID, copyID}, "RID", resourceID);
            } else {
                //Make the tail point to the first copy.
                dbManager.editTuple("Copy", new String[]{"NextCopyInQueue"},
                        new String[]{copyID}, "CPID", tail);
            }

            //make copy the new tail.
            dbManager.editTuple("Resource", new String[]{"TailOfAvailableQueue"},
                    new String[]{copyID}, "RID", resourceID);

            //set copy state id and due date.
            dbManager.editTuple("Copy", new String[]{"StateID", "Due_Date"},
                    new String[]{"0", "''"}, "CPID", copyID);

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }



}
