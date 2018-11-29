package Core;

import java.sql.SQLException;

/**
 * Responsible for authenticating accounts
 * @author Daryl Tan
 * @version 1.0
 */

public class AuthenticationManager{
    private String username;
    private DatabaseManager dbManager;

    /**
     * constructor for the Authentication manager
     * @param username
     */
    public AuthenticationManager(String username, DatabaseManager dbManager) {
        this.username = username;
        this.dbManager = dbManager;
    }

    /**
     * method that authenticates a user through their username
     * @return true if the username can be authenticated, else false
     * @throws IllegalArgumentException
     */
    public boolean authenticate()throws IllegalArgumentException {
        try {
            if(dbManager.checkIfExist("User", new String[]{"UID"},
                    new String[]{username})){
                return true;
        }
        else {
            throw new IllegalArgumentException("Account corresponding to specified username does not exist");
        }
    } catch (SQLException e) {
            return false;
    }
    }

    /**
     * method that checks whether a user is a staff member
     * @return true if specified user is a staff member, else false
     * @throws IllegalArgumentException, SQLException
     */
    public boolean isStaff()throws IllegalArgumentException, SQLException{
            if (dbManager.checkIfExist("Staff", new String[]{"UID"},
                    new String[]{username})) {
                return true;
            } else {
                return false;
            }
        }
        }

