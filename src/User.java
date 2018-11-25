/**
 * This class represents a user and its information.
 * @author Noah Lenagan, Paris Kelly Skopelitis
 * @version 1.1
 */

public class User {

    /**
     * Uniquely identifies the account.
     */
    protected int userID;

    /**
     * The first name of the user.
     */
    protected String firstName;

    /**
     * The last name of the user.
     */
    protected String lastName;

    /**
     * The telephone number of the user.
     */
    protected String telNum;

    /**
     * The street number of the user's address.
     */
    protected String streetNum;

    /**
     * The street name of the user's address.
     */
    protected String streetName;

    /**
     * The county of the user's address.
     */
    protected String county;

    /**
     * The city of the user's address.
     */
    protected String city;

    /**
     * The post code of the user's address.
     */
    protected String postCode;

    /**
     * Uniquely identifies the image id of the avatar.
     */
    protected int avatarID;

    /**
     * Creates a user with specified account id, username, first name, last name, telephone number,
     * street number, street name, county, city and postcode.
     * @param userID A unique identifier for the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param telNum The telephone number of the user.
     * @param streetNum The street number of the user's address.
     * @param streetName The street name of the user's address..
     * @param county The county of the user's address.
     * @param city The city of the user's address.
     * @param postCode The post code of the user's address.
     * @param avatarID The unique identifier for the avatar image.
     */
    public User(int userID, String firstName, String lastName, String telNum, String streetNum,
                String streetName, String county, String city, String postCode, int avatarID){

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNum = telNum;
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.county = county;
        this.city = city;
        this.postCode = postCode;
        this.avatarID = avatarID;

    }


    /**
     * Get the user id of the user..
     * @return The user id.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Get the user's first name.
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the user's last name.
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the user's telephone number.
     * @return The user's telephone number.
     */
    public String getTelNum() {
        return telNum;
    }

    /**
     * Get the user's street number.
     * @return The user's street number.
     */
    public String getStreetNum() {
        return streetNum;
    }

    /**
     * Get the user's street name.
     * @return The user's street name.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Get the user's county.
     * @return The user's county.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Get the user's city.
     * @return The user's city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Get the user's post code.
     * @return The user's post code.
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Get the avatar image id.
     * @return The unique identifier for the avatar image.
     */
    public int getAvatarID() {
        return avatarID;
    }

    /**
     * Set the user's first name.
     * @param firstName  The new user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set the user's last name.
     * @param lastName  The new user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set the user's telephone number.
     * @param telNum The new user's telephone number.
     */
    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    /**
     * Set the user's street number.
     * @param streetNum  The new user's street number.
     */
    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    /**
     * Set the user's street name.
     * @param streetName  The new user's street name.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Set the user's county.
     * @param county  The new user's county.
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * Set the user's city.
     * @param city  The new user's city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Set the user's post code.
     * @param postCode  The new user's post code.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Sets the avatar id.
     * @param avatarID The new unique identifier for the avatar image.
     */
    public void setAvatarID(int avatarID) {
        this.avatarID = avatarID;
    }

    /**
     * Creates a summary of information for the User.
     * @return Returns a summary of the User.
     */
    public String toString() {
        String out  = "User ID - " + userID +
                "\nFirst Name - " + firstName +
                "\nLast Name - " + lastName +
                "\nTelephone Number - " + telNum +
                "\nStreet Number - " + streetNum +
                "\nStreet Name - " + streetName +
                "\nCounty - " + county +
                "\nPost Code - " + postCode;
        return  out;
    }
}
