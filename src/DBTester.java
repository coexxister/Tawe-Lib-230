import java.sql.SQLException;

public class DBTester {

	public static void main(String[] args) {

		/*DatabaseManager dbManager = new DatabaseManager("C:/Users/Noah/Desktop/TaweLib Database/TaweLibDB.db");
		
		try {
			String[][] tuples = dbManager.searchTuples("Resources", "Title", "mac");
			
			for (String[] row : tuples) {
				for (String col: row) {
					System.out.print(col + " | ");
				}
				System.out.print("\n");
			}
		} catch (SQLException e) {
			System.out.println("Table not found");
		}*/
		
		/*try {
			dbManager.editTuple("Resources", new String[] {"Title"}, new String[] {"'iMac Pro'"}, "Title = 'iMac Pro'");
		} catch (IllegalArgumentException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//dbManager.sqlQuery("UPDATE Resources SET MacbookPro");

		System.out.println("hello");
		
		
	}

}
