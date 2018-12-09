package Core;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class rfmManager {

    public static void main(String[] args) throws SQLException {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");
        ResourceManager rmManager = new ResourceManager(dbManager);
        AccountManager acManager = new AccountManager(dbManager);

        ResourceFlowManager rfm = new ResourceFlowManager(dbManager, acManager, rmManager, 1);

        Copy copy = new Copy(1, 34, 14, "", 0, 0);

        //System.out.println(rfm.calculateFine(rmManager.getCopies(1)[0]));

        //rmManager.addBulkCopies(copy);
        //rfm.borrowCopy(38,1);
        //rfm.requestResource(9, 3);
        //rfm.returnCopy(35, 1);
        //rfm.unreserveCopy(41,3);
        //rfm.returnCopy(21,1);

        //rmManager.addCopy(copy);

        //rfm.requestResource(2, 1);

        rmManager.addBulkCopies(copy);
        //System.out.print(acManager.isExist(2));

        /*String borrowDate = dbManager.getFirstTupleByQuery("SELECT Date_Out FROM BorrowHistory" +
                " WHERE CID = " + Integer.toString(55) + " AND Date_Returned IS NULL")[0];

        int loanDuration = 14;

        String estimateDue = "";

        try {
            //add loan duration to borrow date
            estimateDue = DateManager.returnDueDate(borrowDate, loanDuration);
        } catch (ParseException p) {
            estimateDue = "WEll shit";
        }

        //get the offset
        int offset = Math.toIntExact(ChronoUnit.DAYS.between(LocalDate.parse(DateManager.returnCurrentDate()), LocalDate.parse(estimateDue)));

        //get the due date
        String dueDate;

        //if user has borrowed for longer than loan duration, then set due date for next day
        //otherwise set due date for the loan duration + borrow date.
        if (offset <= 0) {
            dueDate = DateManager.returnDueDate(1);
        } else {
            dueDate = DateManager.returnDueDate(offset);
        }*/

    }

}
