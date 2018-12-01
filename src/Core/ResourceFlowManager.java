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
    + showOverdueCopies() : Copy[]
    + getBorrowedCopies (userID: Integer) : Copy[]
    + getBorrowHistory (userID: Integer) : String[][]

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
     * Borrows a specified copy for a user.
     * @param copyID The copy id of a copy.
     * @param userID The user id of a copy.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     * @throws IllegalStateException Thrown if specified copy or user does not exist.
     */
    public void borrowCopy(int copyID, int userID) throws SQLException, IllegalStateException {

        //check if copy is available, if so then borrow copy. Otherwise throw exception.
        if (getCopyState(copyID) == 0) {
            //Get the resource id.
            int resourceID = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT RID FROM Copy WHERE CPID = " +
                    Integer.toString(copyID))[0]);

            //If the user exists then borrow the copy.
            if (dbManager.checkIfExist("User", new String[] {"UID"},
                    new String[] {Integer.toString(userID)})) {

                //remove copy from availability queue.
                removeAvailable(copyID, resourceID);
                //enqueue in borrowed queue.
                enqueueBorrowed(copyID, resourceID, userID);

                //adds to borrow history
                dbManager.addTuple("BorrowHistory",
                        new String[] {Integer.toString(userID), Integer.toString(copyID),
                        encase(DateManager.returnCurrentDate()), "''"});

            } else {
                throw new IllegalStateException("User does not exist");
            }

        } else {
            throw new IllegalStateException("Copy is not available");
        }

    }

    /**
     * Reserves a copy for a user.
     * @param copyID The copy id of a copy.
     * @param userID The user id of a user.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void reserveCopy(int copyID, int userID) throws SQLException, IllegalStateException {
        //If the copy state is undefined then reserve the copy.
        if (getCopyState(copyID) == -1) {
            dbManager.addTuple("ReservedResource",
                    new String[]{Integer.toString(copyID), Integer.toString(userID)});
            setCopyState(copyID, 2);
        } else {
            throw new IllegalStateException("Copy state must be undefined");
        }
    }

    /**
     * Un-reserves a copy for a user.
     * @param copyID The copy id of a copy.
     * @param userID The user id of a user.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    public void unreserveCopy(int copyID, int userID) throws SQLException, IllegalStateException {
        if (getCopyState(copyID) == 2) {
            dbManager.deleteTuple("ReservedResource",
                    new String[]{"CPID", "UID"},
                    new String[]{Integer.toString(copyID), Integer.toString(userID)});
            setCopyState(copyID,-1);

            //get resource id of copy.
            int resourceID = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT RID FROM Copy WHERE CPID = " +
                    Integer.toString(copyID))[0]);

            enqueueAvailable(copyID, resourceID);
        } else {
            throw new IllegalStateException("Copy must be reserved");
        }
    }

    /**
     * Requests a select resource for a user.
     * @param resourceID The resource id of the resource.
     * @param userID The user id of the user.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    public void requestResource(int resourceID, int userID) throws SQLException {

        dbManager.addTuple("ResourceRequestQueue",
                new String[] {"null", Integer.toString(resourceID), Integer.toString(userID)});

    }

    /**
     * Deletes a request for a resource.
     * @param resourceID The resource id of the resource.
     * @param userID The user id of the resource.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    public void deleteRequest(int resourceID, int userID) throws SQLException {

        dbManager.deleteTuple("ResourceRequestQueue", new String[] {"RID", "UID"},
                new String[] {Integer.toString(resourceID), Integer.toString(userID)});

    }

    /**
     * Deletes all requests from a user.
     * @param userID The user id of a user.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    public  void deleteAllRequests(int userID) throws SQLException {

        dbManager.deleteTuple("ResourceRequestQueue", new String[] {"UID"},
                new String[] {Integer.toString(userID)});

    }

    /**
     * Makes the copy borrowed. The copy must not already be available, on loan or reserved.
     * @param copyID The copy id of the copy.
     * @param resourceID The resource id of the resource.
     * @param userID The user id of the user.
     * @throws IllegalStateException Thrown if copy is available, on loan or reserved.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void enqueueBorrowed(int copyID, int resourceID, int userID) throws IllegalStateException, SQLException {

        //enqueue if not borrowed, reserved or available, otherwise throw a illegal state exception.
        //state of -1 represents an undefined state of the copy.
        if (getCopyState(copyID) == -1) {

            String stResourceID = Integer.toString(resourceID);
            String stCopyID = Integer.toString(copyID);

            //get queue tail
            String tail = dbManager.getFirstTupleByQuery("SELECT TailOfBorrowedQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //If the tail is null then set the head and tail, otherwise make the tail point to the copy.
            if (tail == null) {
                //set head and tail
                dbManager.editTuple("Resource",
                        new String[]{"HeadOfBorrowedQueue"},
                        new String[]{stCopyID}, "RID", stResourceID);
            } else {
                //Make the tail point to the first copy.
                setNextCopy(Integer.parseInt(tail), copyID);

                //Make the copy point to the tail.
                setPrevCopy(copyID, tail);
            }

            //make copy the new tail.
            dbManager.editTuple("Resource", new String[]{"TailOfBorrowedQueue"},
                    new String[]{stCopyID}, "RID", stResourceID);

            //set copy state id and due date.
            dbManager.editTuple("Copy", new String[]{"StateID", "Due_Date", "UID"},
                    new String[]{"1", "null", Integer.toString(userID)}, "CPID", stCopyID);

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }

    /**
     * Makes a copy unavailable. The copy must not already be available, on loan or reserved.
     * @param copyID The copy id of the copy.
     * @param resourceID The resource id of the resource.
     * @throws IllegalStateException Thrown if copy is available, on loan or reserved.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void removeBorrowedCopy(int copyID, int resourceID) throws IllegalStateException, SQLException {

        //dequeue if only currently available, otherwise throw a illegal state exception.
        //state of -1 represents an undefined state of the copy.
        if (getCopyState(copyID) == 1) {

            String stResourceID = Integer.toString(resourceID);

            //get queue head
            String head = dbManager.getFirstTupleByQuery("SELECT HeadOfBorrowedQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //If head is null then throw illegal state exception, otherwise remove copy
            if (head == null) {
                throw new IllegalStateException("Queue is empty");
            } else {

                //Get previous id and next id.
                int prev = -1;
                int next = -1;

                //Get previous copy id if exists.
                if (isPrev(copyID)) {
                    prev = getPrev(copyID);
                }

                //Get next copy id if exists.
                if (isNext(copyID)) {
                    next = getNext(copyID);
                }

                if (prev != -1 && next != -1) {
                    //If prev and next does equal null.
                    setNextCopy(prev, next);
                    setPrevCopy(next, prev);
                } else if (prev == -1 && next != -1) {
                    //If the copy is the head but isn't the tail.
                    //set the head
                    dbManager.editTuple("Resource",
                            new String[]{"HeadOfBorrowedQueue"},
                            new String[]{Integer.toString(next)},
                            "RID", stResourceID);
                    setPrevCopy(next, "null");
                } else if (prev != -1 && next == -1) {
                    //If the copy is the tail but isn't the head.
                    //set the tail
                    dbManager.editTuple("Resource",
                            new String[]{"TailOfBorrowedQueue"},
                            new String[]{Integer.toString(prev)},
                            "RID", stResourceID);
                    setNextCopy(prev, "null");
                } else {
                    //If the copy is both the head and tail.
                    //set both the head and tail to null as queue is empty
                    dbManager.editTuple("Resource",
                            new String[]{"HeadOfBorrowedQueue"},
                            new String[]{"null"},
                            "RID", stResourceID);
                    dbManager.editTuple("Resource",
                            new String[]{"TailOfBorrowedQueue"},
                            new String[]{"null"},
                            "RID", stResourceID);
                }

                //Set the copys next and prev to null.
                setNextCopy(copyID, "null");
                setPrevCopy(copyID, "null");

                //Set the state
                setCopyState(copyID, -1);

            }

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }

    /**
     * Makes a copy available. The copy must not already be available, on loan or reserved.
     * @param copyID The copy id of the copy.
     * @param resourceID The resource id of the resource.
     * @throws IllegalStateException Thrown if copy is available, on loan or reserved.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void enqueueAvailable(int copyID, int resourceID) throws IllegalStateException, SQLException {

        //enqueue if not borrowed, reserved or available, otherwise throw a illegal state exception.
        //state of -1 represents an undefined state of the copy.
        if (getCopyState(copyID) == -1) {

            String stResourceID = Integer.toString(resourceID);
            String stCopyID = Integer.toString(copyID);

            //get queue tail
            String tail = dbManager.getFirstTupleByQuery("SELECT TailOfAvailableQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //If the tail is null then set the head and tail, otherwise make the tail point to the copy.
            if (tail == null) {
                //set head and tail
                dbManager.editTuple("Resource",
                        new String[]{"HeadOfAvailableQueue"},
                        new String[]{stCopyID}, "RID", stResourceID);
            } else {
                //Make the tail point to the copy.
                setNextCopy(Integer.parseInt(tail), copyID);

                //Make the copy point to the tail.
                setPrevCopy(copyID, tail);
            }

            //make copy the new tail.
            dbManager.editTuple("Resource", new String[]{"TailOfAvailableQueue"},
                    new String[]{stCopyID}, "RID", stResourceID);

            //set copy state id and due date.
            dbManager.editTuple("Copy", new String[]{"StateID", "Due_Date"},
                    new String[]{"0", "null"}, "CPID", stCopyID);

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }

    /**
     * Makes a copy unavailable. The copy must not already be available, on loan or reserved.
     * @param copyID The copy id of the copy.
     * @param resourceID The resource id of the resource.
     * @throws IllegalStateException Thrown if copy is available, on loan or reserved.
     * @throws SQLException Thrown if connection to database failed or tables do not exist.
     */
    private void removeAvailable(int copyID, int resourceID) throws IllegalStateException, SQLException {

        //dequeue if only currently available, otherwise throw a illegal state exception.
        //state of -1 represents an undefined state of the copy.
        if (getCopyState(copyID) == 0) {

            String stResourceID = Integer.toString(resourceID);

            //get queue head
            String head = dbManager.getFirstTupleByQuery("SELECT HeadOfAvailableQueue FROM " +
                    "Resource WHERE RID = " + resourceID)[0];

            //If head is null then throw illegal state exception, otherwise remove copy
            if (head == null) {
                throw new IllegalStateException("Queue is empty");
            } else {

                //Get previous id and next id.
                int prev = -1;
                int next = -1;

                //Get previous copy id if exists.
                if (isPrev(copyID)) {
                    prev = getPrev(copyID);
                }

                //Get next copy id if exists.
                if (isNext(copyID)) {
                    next = getNext(copyID);
                }

                if (prev != -1 && next != -1) {
                    //If prev and next does equal null.
                    setNextCopy(prev, next);
                    setPrevCopy(next, prev);
                } else if (prev == -1 && next != -1) {
                    //If the copy is the head but isn't the tail.
                    //set the head
                    dbManager.editTuple("Resource",
                            new String[]{"HeadOfAvailableQueue"},
                            new String[]{Integer.toString(next)},
                            "RID", stResourceID);
                    setPrevCopy(next, "null");
                } else if (prev != -1 && next == -1) {
                    //If the copy is the tail but isn't the head.
                    //set the tail
                    dbManager.editTuple("Resource",
                            new String[]{"TailOfAvailableQueue"},
                            new String[]{Integer.toString(prev)},
                            "RID", stResourceID);
                    setNextCopy(prev, "null");
                } else {
                    //If the copy is both the head and tail.
                    //set both the head and tail to null as queue is empty
                    dbManager.editTuple("Resource",
                            new String[]{"HeadOfAvailableQueue"},
                            new String[]{"null"},
                            "RID", stResourceID);
                    dbManager.editTuple("Resource",
                            new String[]{"TailOfAvailableQueue"},
                            new String[]{"null"},
                            "RID", stResourceID);
                }

                //Set the copys next and prev to null.
                setNextCopy(copyID, "null");
                setPrevCopy(copyID, "null");

                //Set the state
                setCopyState(copyID, -1);

            }

        } else {
            throw new IllegalStateException("Copy must not be reserved, on loan or available.");
        }

    }

    /**
     * Sets the next copy of the copy.
     * @param copyID The copy id of the Copy.
     * @param nextID The copy id of the next Copy.
     * @throws IllegalStateException Thrown if the copy does not exist.
     * @throws SQLException Thrown if table does not exist or connection to database failed.
     */
    private void setNextCopy(int copyID, String nextID) throws IllegalStateException, SQLException {
        //If the copy exists then check if the next copy exists. Otherwise thrown exception.
        if (rmManager.doesCopyExist(copyID)) {
            //Set the next copy of the copy.
            dbManager.editTuple("Copy", new String[]{"NextCopyInQueue"},
                    new String[]{nextID}, "CPID", Integer.toString(copyID));

        } else {
            //Thrown if copy doesn't exist.
            throw new IllegalStateException("The copy ID specified does not exist");
        }
    }

    /**
     * Sets the next copy of the copy.
     * @param copyID The copy id of the Copy.
     * @param nextID The copy id of the next Copy.
     * @throws IllegalStateException Thrown if the copy does not exist.
     * @throws SQLException Thrown if table does not exist or connection to database failed.
     */
    private void setNextCopy(int copyID, int nextID) throws IllegalStateException, SQLException {
        setNextCopy(copyID, Integer.toString(nextID));
    }

    /**
     * Sets the previous copy of the copy.
     * @param copyID The copy id of the Copy.
     * @param prevID The copy id of the prev Copy.
     * @throws IllegalStateException Thrown if the copy does not exist.
     * @throws SQLException Thrown if table does not exist or connection to database failed.
     */
    private void setPrevCopy(int copyID, String prevID) throws IllegalStateException, SQLException {
        //If the copy exists then check if the next copy exists. Otherwise thrown exception.
        if (rmManager.doesCopyExist(copyID)) {

            //Set the previous copy of the copy.
            dbManager.editTuple("Copy", new String[]{"PreviousCopyInQueue"},
                    new String[]{prevID}, "CPID", Integer.toString(copyID));

        } else {
            //Thrown if copy doesn't exist.
            throw new IllegalStateException("The copy ID specified does not exist");
        }
    }

    /**
     * Sets the previous copy of the copy.
     * @param copyID The copy id of the Copy.
     * @param prevID The copy id of the prev Copy.
     * @throws IllegalStateException Thrown if the copy does not exist.
     * @throws SQLException Thrown if table does not exist or connection to database failed.
     */
    private void setPrevCopy(int copyID, int prevID) throws IllegalStateException, SQLException {
        setPrevCopy(copyID, Integer.toString(prevID));
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

            if (prev == null) {
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
            if (prev == null) {
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
            if (next == null) {
                return false;
            } else {
                return true;
            }

        } else {
            throw new IllegalStateException("Copy does not exist");
        }
    }

    /**
     * Sets the state of the Copy.
     * @param copyID The copy id of the copy.
     * @param state The state of the copy where -1 is undefined, 0 is available, 1 is on loan and 2 is reserved.
     * @throws SQLException Thrown if connection to database fails or table does not exist.
     * @throws IllegalArgumentException Thrown if state argument is invalid.
     */
    private void setCopyState(int copyID, int state) throws SQLException, IllegalArgumentException {
        //If the state specified is valid then change the state.
        if (state >= -1 && state <= 2) {
            dbManager.editTuple("Copy", new String[] {"StateID"},
                    new String[] {Integer.toString(state)}, "CPID",
                    Integer.toString(copyID));
        } else {
            throw new IllegalArgumentException("State specified is invalid");
        }
    }

    /**
     * Gets the state of the Copy.
     * @param copyID The copy id of the copy.
     * @return The state of the Copy.
     * @throws SQLException Thrown if connection to database fails or table does not exist.
     * @throws IllegalArgumentException Thrown if specified copy does not exist.
     */
    private int getCopyState(int copyID) throws SQLException, IllegalArgumentException {
        //If copy exists, return it's state. Otherwise return
        if (rmManager.doesCopyExist(copyID)) {
            return Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT StateID FROM Copy WHERE CPID = "
                    + Integer.toString(copyID))[0]);
        } else {
            throw new IllegalArgumentException("Specified copy does not exist");
        }
    }

    /**
     * Encases a string in apostrophe marks.
     * @param str The string to encase.
     * @return The encased string.
     */
    private String encase(String str) {
        return "'" + str + "'";
    }

}
