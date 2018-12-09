package Core;

public class FineTransaction extends Transaction {

    /**
     * The copy id of the copy that caused the fine.
     */
    private int copyID;

    /**
     * The days that the copy was overdue.
     */
    private int days;

    /**
     * Creates a transaction.
     *
     * @param userID The account id of the transaction.
     * @param date   The data of the transaction.
     * @param change The money change in the transaction.
     */
    public FineTransaction(int userID, String date, String time, float change, int tranID, int copyID, int days) {
        super(userID, date, time, change, tranID);

        this.copyID = copyID;
        this.days = days;
    }

    /**
     * Gets the copy id of the copy that caused the fine.
     *
     * @return The copy id of the copy that caused the fine.
     */
    public int getCopyID() {
        return copyID;
    }

    /**
     * Gets the days that the copy was overdue.
     *
     * @return The days that the copy was overdue.
     */
    public int getDays() {
        return days;
    }

    /**
     * Summarizes the fine transaction.
     *
     * @return The summary of the transaction.
     */
    public String toString() {
        String out = super.toString();
        out += "\nCopy ID: " + copyID +
                "\nDays overdue: " + days;

        return out;
    }

}
