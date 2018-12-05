package Core;

/**
 * Contains the data regarding the borrow and return of a specific copy by a user.
 * @author Noah Lenagam.
 * @version 1.0
 */
public class LoanEvent {

    /**
     * The user id of a user.
     */
    private int userID;

    /**
     * The copy id of a copy.
     */
    private int copyID;

    /**
     * The date of the borrow of the copy.
     */
    private String dateOut;

    /**
     * The date of the return of the copy.
     */
    private String dateIn;

    /**
     * Constructs the BorrowEvent.
     * @param userID The user id of a user.
     * @param copyID The copy id of a copy.
     * @param dateOut The date of the borrow of the copy.
     * @param dateIn The date of the return of the copy.
     */
    public LoanEvent(int userID, int copyID, String dateOut, String dateIn) {

        this.userID = userID;
        this.copyID = copyID;
        this.dateOut = dateOut;
        this.dateIn = dateIn;

    }

    /**
     * Gets the user id of a user.
     * @return The user id of a user.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the copy id of a copy.
     * @return The copy id of a copy.
     */
    public int getCopyID() {
        return copyID;
    }

    /**
     * Gets the date of borrowing the copy.
     * @return The date of the borrowing.
     */
    public String getDateOut() {
        return dateOut;
    }

    /**
     * Gets the date of returning the copy.
     * @return The date of return.
     */
    public String getDateIn() {
        return dateIn;
    }

}
