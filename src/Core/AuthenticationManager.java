package Core;

import java.sql.SQLException;

/**
 * Responsible for authenticating accounts.
 *
 * @author Daryl Tan
 * @version 1.0
 */
public class AuthenticationManager {

	/**
	 * The username.
	 */
	private final String username;

	/**
	 * The database manager instance.
	 */
	private final DatabaseManager dbManager;

	/**
	 * Constructor for the Authentication manager.
	 *
	 * @param username  Username of an account.
	 * @param dbManager The database manager.
	 */
	public AuthenticationManager(String username, DatabaseManager dbManager) {
		this.username = username;
		this.dbManager = dbManager;
	}

	/**
	 * Method that authenticates a user through their username.
	 *
	 * @return true if the username can be authenticated, else false.
	 * @throws IllegalArgumentException Thrown if user does not exist.
	 */
	public boolean authenticate() throws IllegalArgumentException {
		try {
			if (dbManager.checkIfExist("User", new String[]{"UID"},
					new String[]{username})) {
				return true;
			} else {
				throw new IllegalArgumentException("Account corresponding to specified username does not exist");
			}
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Method that checks whether a user is a staff member.
	 *
	 * @return true if specified user is a staff member, else false.
	 * @throws SQLException Thrown if failed to connect to database.
	 */
	public boolean isStaff() throws SQLException {
		return dbManager.checkIfExist("Staff", new String[]{"UID"},
				new String[]{username});
	}
}

