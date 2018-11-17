import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class allows the fetching and writing of data to the database.
 * @author Noah Lenagan
 * @version 1.0
 */
public class DatabaseManager {

	/**
	 * The directory to the database file.
	 */
	private String dbLocation;

	/**
	 * Creates a DatabaseManager.
	 * @param dbLocation The directory to the database file.
	 */
	public DatabaseManager(String dbLocation) {
		this.dbLocation = dbLocation;
	}

	/**
	 * Creates a connection (session) with the database.
	 * @return The connection to the database.
	 */
	private Connection createConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection dbConn = null;
			try {
				dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);
			} catch (SQLException e) {
				System.out.println("Connection could not be established");
			} finally {
				return dbConn;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("sqliteJDBC not found");
			return null;
		}

	}
	
	//THINGS TO ADD: get a primary key returned when adding a row
	
	public void sqlQuery(String query) throws SQLException {
		// create connection
		Connection conn = createConnection();

		Statement statement = conn.createStatement();
		
		statement.execute(query);

		statement.close();
		conn.close();
	}

	public void addTuple(String tableName, String[] data) throws SQLException, IllegalArgumentException {

		String sqlQuery = "INSERT INTO " + tableName + " VALUES (";

		// for every column value in data except the last value, add to the sqlQuery
		for (int iColumn = 0; iColumn < data.length - 1; iColumn++) {
			sqlQuery += data[iColumn] + ", ";
		}
		// add the last column value to the sql query without a comma.
		sqlQuery += data[data.length - 1] + ")";
		
		sqlQuery(sqlQuery);
	}

	public void editTuple(String tableName, String[] columnNames, String[] values, String condition) throws SQLException {
		
		String query = "UPDATE " + tableName + " SET ";
		
		for (int iColumn = 0; iColumn < columnNames.length - 1; iColumn++) {
			query += columnNames[iColumn] + " = " + values[iColumn] + ", ";
		}
		
		query += columnNames[columnNames.length - 1] + " = " + values[columnNames.length - 1] + " WHERE " + condition;
		
		sqlQuery(query);
		
	}

	public String[][] getTupleList(String tableName) throws SQLException {
		// selects all rows from table
		return getTupleListByQuery("SELECT * FROM " + tableName + ";");
	}

	public String[][] searchTuples(String tableName, String selectColumn, String searchQuery) throws SQLException {
		// selects all rows from table tableName where the selectColumn contains
		// searchQuery inside it
		return getTupleListByQuery(
				"SELECT * FROM " + tableName + " WHERE " + selectColumn + " LIKE " + "'%" + searchQuery + "%'");
	}

	public String[][] getTupleListByQuery(String query) throws SQLException {
		// create connection
		Connection conn = createConnection();

		Statement statement = conn.createStatement();

		// execute query which will list all rows
		ResultSet resultSet = statement.executeQuery(query);

		// an array list is initially used as the amount of rows are unknown
		ArrayList<String[]> tupleList = new ArrayList<>();

		// the amount of columns in a row
		int rowLength = resultSet.getMetaData().getColumnCount();

		// for every row, add the columns to an array of strings and then add to the
		// arraylist
		while (resultSet.next()) {

			// add an empty string array to the array list
			tupleList.add(new String[rowLength]);

			// for every column add the column data to the string array
			for (int iColumn = 1; iColumn <= rowLength; iColumn++) {
				tupleList.get(tupleList.size() - 1)[iColumn - 1] = resultSet.getString(iColumn);
			}

		}

		resultSet.close();
		statement.close();
		conn.close();

		// convert array list to an array and return
		return tupleList.toArray(new String[tupleList.size()][]);
	}

	public String getDBLocation() {
		return dbLocation;
	}

	public void setDBlocation(String dbLocation) {
		this.dbLocation = dbLocation;
	}

}
