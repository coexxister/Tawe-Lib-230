package Core;

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
                    new String[]{"0", "'HMMMMMMMMM'"}, "CPID", copyID);

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }

    /**
     * Makes a copy unavailable. The copy must not already be available, on loan or reserved.
     * @param copy The copy to make unavailable.
     * @throws IllegalStateException Thrown if copy is available, on loan or reserved.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void removeAvailable(Copy copy) throws IllegalStateException, SQLException {

        //dequeue if only currently available, otherwise throw a illegal state exception.
        //state of -1 represents an undefined state of the copy.
        if (copy.getStateID() == 1) {

            String resourceID = Integer.toString(copy.getResourceID());
            String copyID = Integer.toString(copy.getCopyID());

            //get queue tail
            String tail = dbManager.getFirstTupleByQuery("SELECT TailOfAvailableQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //get queue head
            String head = dbManager.getFirstTupleByQuery("SELECT HeadOfAvailableQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //If head is null then throw illegal state exception, otherwise remove copy

            //Get previous id and next id.

            //If previous id null then make next head

            //If next is null then make previous tail

            //Otherwise make previous point to next.

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
                    new String[]{"-1", "''"}, "CPID", copyID);

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }

    /**
     * Sets the next copy of the copy and sets the previous copy of the next copy.
     * @param copyID The copy id of the Copy.
     * @param nextID The copy id of the next Copy.
     * @throws IllegalStateException Thrown if the copy or next copy doesn't exist.
     * @throws SQLException Thrown if table does not exist or connection to database failed.
     */
    private void setNextCopy(int copyID, int nextID) throws IllegalStateException, SQLException {
        //If the copy exists then check if the next copy exists. Otherwise thrown exception.
        if (rmManager.doesCopyExist(copyID)) {
            //If the next copy exists then set the next copy of the copy. Otherwise thrown exception.
            if (rmManager.doesCopyExist(nextID)) {

                //Set the next copy of the copy.
                dbManager.editTuple("Copy", new String[] {"NextCopyInQueue"},
                        new String[] {Integer.toString(nextID)},"CPID", Integer.toString(copyID));
                //Set the previous copy of the next copy.
                dbManager.editTuple("Copy", new String[] {"PreviousCopyInQueue"},
                        new String[] {Integer.toString(copyID)},"CPID", Integer.toString(nextID));

            } else {
                //Thrown if next copy doesn't exist.
                throw new IllegalStateException("The next ID speicified does not exist");
            }

        } else {
            //Thrown if copy doesn't exist.
            throw new IllegalStateException("The copy ID specified does not exist");
        }
    }

    /**
     * Sets the previous copy of the copy and sets the next copy of the previous copy.
     * @param copyID The copy id of the Copy.
     * @param prevID The copy id of the prev Copy.
     * @throws IllegalStateException Thrown if the copy or next copy doesn't exist.
     * @throws SQLException Thrown if table does not exist or connection to database failed.
     */
    private void setPrevCopy(int copyID, int prevID) throws IllegalStateException, SQLException {
        //If the copy exists then check if the next copy exists. Otherwise thrown exception.
        if (rmManager.doesCopyExist(copyID)) {
            //If the next copy exists then set the prev copy of the copy. Otherwise thrown exception.
            if (rmManager.doesCopyExist(prevID)) {

                //Set the next copy of the copy.
                dbManager.editTuple("Copy", new String[] {"PreviousCopyInQueue"},
                        new String[] {Integer.toString(prevID)},"CPID", Integer.toString(copyID));
                //Set the next copy of the prev copy.
                dbManager.editTuple("Copy", new String[] {"NextCopyInQueue"},
                        new String[] {Integer.toString(copyID)},"CPID", Integer.toString(prevID));

            } else {
                //Thrown if next copy doesn't exist.
                throw new IllegalStateException("The next ID speicified does not exist");
            }

        } else {
            //Thrown if copy doesn't exist.
            throw new IllegalStateException("The copy ID specified does not exist");
        }
    }

    /**
     * Gets the previous copy id of the copy.
     * @param copyID The copy id of the copy.
     * @return The copy id of the previous copy.
     * @throws SQLException Thrown if connection to database failed or table does not exist.
     * @throws IllegalStateException Thrown if the copy or previous copy does not exist.
     */
    private int getPrev(int copyID) throws SQLException, IllegalStateException {
        //If the copy exists, get the previous copy.
        if (rmManager.doesCopyExist(copyID)) {

            String prev = dbManager.getFirstTupleByQuery("SELECT PreviousCopyInQueue FROM Copy WHERE" +
                    " CPID = " + Integer.toString(copyID))[0];

            if (prev.equals("null")) {
                throw new IllegalStateException("Previous copy does not exist");
            } else {
                return Integer.parseInt(prev);
            }

        } else {
            throw new IllegalStateException("Copy does not exist");
        }
    }

    /**
     * Gets the next copy id of the copy.
     * @param copyID The copy id of the copy.
     * @return The copy id of the next copy.
     * @throws SQLException Thrown if connection to database failed or table does not exist.
     * @throws IllegalStateException Thrown if the copy or next copy does not exist.
     */
    private int getNext(int copyID) throws SQLException, IllegalStateException {
        //If the copy exists, get the next copy.
        if (rmManager.doesCopyExist(copyID)) {

            String next = dbManager.getFirstTupleByQuery("SELECT NextCopyInQueue FROM Copy WHERE" +
                    " CPID = " + Integer.toString(copyID))[0];

            if (next.equals("null")) {
                throw new IllegalStateException("Next copy does not exist");
            } else {
                return Integer.parseInt(next);
            }

        } else {
            throw new IllegalStateException("Copy does not exist");
        }
    }

    /**
     * Determines if there is a previous copy in the queue.
     * @param copyID The copy id of the copy.
     * @return Returns true, if previous copy exists. False is does not exist.
     * @throws SQLException Thrown if connection to database failed or table does not exist.
     * @throws IllegalStateException Thrown if the copy specified does not exist.
     */
    private boolean isPrev(int copyID) throws SQLException, IllegalStateException {
        //If the copy exists, get the previous copy.
        if (rmManager.doesCopyExist(copyID)) {

            String prev = dbManager.getFirstTupleByQuery("SELECT PreviousCopyInQueue FROM Copy WHERE" +
                    " CPID = " + Integer.toString(copyID))[0];

            //if previous is null then return false, otherwise true.
            if (prev.equals("null")) {
                return false;
            } else {
                return true;
            }

        } else {
            throw new IllegalStateException("Copy does not exist");
        }
    }

    /**
     * Determines if there is a next copy in the queue.
     * @param copyID The copy id of the copy.
     * @return Returns true, if next copy exists. False is does not exist.
     * @throws SQLException Thrown if connection to database failed or table does not exist.
     * @throws IllegalStateException Thrown if the copy specified does not exist.
     */
    private boolean isNext(int copyID) throws SQLException, IllegalStateException {
        //If the copy exists, get the next copy.
        if (rmManager.doesCopyExist(copyID)) {

            String next = dbManager.getFirstTupleByQuery("SELECT NextCopyInQueue FROM Copy WHERE" +
                    " CPID = " + Integer.toString(copyID))[0];

            //if next is null then return false, otherwise true.
            if (next.equals("null")) {
                return false;
            } else {
                return true;
            }

        } else {
            throw new IllegalStateException("Copy does not exist");
        }
    }

}
