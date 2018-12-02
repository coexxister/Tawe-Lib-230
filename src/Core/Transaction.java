package Core;

/**
 * Represents a transaction.
 * @author Noah Lenagan
 * @version 1.0
 */
public class Transaction {

    /**
     * The user id of the transaction
     */
    private int userID;

    /**
     * The date of the transaction.
     */
    private String date;

    /**
     * The change in money.
     */
    private float change;

    /**
     * Creates a transaction.
     * @param userID The account id of the transaction.
     * @param date The data of the transaction.
     * @param change The money change in the transaction.
     */
    public Transaction(int userID, String date, float change) {
        this.userID = userID;
        this.date = date;
        this.change = change;
    }

    /**
     * Gets the user id of the transaction.
     * @return The user id.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the date of the transaction.
     * @return The date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the money change of the transaction.
     * @return The change in money.
     */
    public float getChange() {
        return change;
    }

    /**
     * Summarizes the transaction.
     * @return The summary of the transaction.
     */
    public String toString() {
        String out = "User: " + userID +
                "\nDate: " + getDate() +
                "\nChange: ";
        if (change >= 0) {
            out+= "+ £" + change;
        } else {
            out+= "- £" + change;
        }

        return out;
    }

}
