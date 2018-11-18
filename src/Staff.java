import java.util.Date;

/**
 * This class represents a Staff user.
 * @author Noah Lenagan, Paris Kelly Skopelitis
 * @version 1.0
 */
public class Staff extends User {

    /**
     * The employment date of the member of staff.
     */
    private Date employmentDate;

    /**
     * The member of staff's staff number.
     */
    private int staffNum;

    /**
     * Creates a staff user with specified account id, username, first name, last name, telephone number,
     * street number, street name, county, city and postcode.
     * @param accountID A unique identifier for the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param telNum The telephone number of the user.
     * @param streetNum The street number of the user's address.
     * @param streetName The street name of the user's address..
     * @param county The county of the user's address.
     * @param city The city of the user's address.
     * @param postCode The post code of the user's address.
     * @param employmentDate The employment date of the member of staff.
     * @param staffNum The member of staff's staff number.
     */
    public Staff(int accountID, String username, String firstName, String lastName, String telNum, String streetNum, String streetName, String county, String city, String postCode, Date employmentDate, int staffNum){

        super(accountID, username, firstName, lastName, telNum, streetNum, streetName, county, city, postCode);

        this.employmentDate = employmentDate;
        this.staffNum = staffNum;

    }

    /**
     * Get the Employment Date
     * @return The Employment Date.
     */
    public Date getEmploymentDate() {
        return employmentDate;
    }

    /**
     * Get the staff number.
     * @return The staff number.
     */
    public int getStaffNum() {
        return staffNum;
    }

    /**
     * Set the Employment Date
     * @param employmentDate  The new Employment Date.
     */
    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    /**
     * Get the staff number.
     * @param staffNum  The new staff number.
     */
    public void setStaffNum(int staffNum) {
        this.staffNum = staffNum;
    }

    /**
     * Creates a summary of information for the member of Staff.
     * @return Returns a summary of the member of Staff.
     */
    public String toString() {

        //create summary
        String out  = super.toString() +
                "\nEmployment Date - " + employmentDate +
                "\nStaff Number - " + staffNum;

        return out;
    }
}
