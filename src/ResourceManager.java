import java.sql.SQLException;
import java.util.ArrayList;

public class ResourceManager {

    private static final String RESOURCE_TABLE_NAME = "Resource";
    private static final String BOOK_TABLE_NAME = "Book";
    private static final String DVD_TABLE_NAME = "Dvd";
    private static final String COMPUTER_TABLE_NAME = "Computer";

    private static final int TID_ATTRIBUTE_POSITION = 4;

    private DatabaseManager dbManager;

    public ResourceManager(DatabaseManager dbManager) {

        this.dbManager = dbManager;

    }

    /*

    + getResourceList () : Resource[]
    + getResourceList (query : String) : Resource[]
    + getCopies (resourceID: Integer) : Copy[]
    + getCopies () : Copy[]
    + editResource (resourceID: Integer, newResource : Book) : Boolean
    + editResource (resourceID: Integer, newResource : Dvd) : Boolean
    + editResource (resourceID: Integer, newResource : Computer) : Boolean
    + addResource (newResource : Book)
    + addResource (newResource : Dvd)
    + addResource (newResource : Computer)


     */

    public Resource[] getResourceList() {

        ArrayList<Resource> resources = new ArrayList<>();
        String[][] table;

        try {
            table = dbManager.getTupleList(RESOURCE_TABLE_NAME);

            for (String[] rows: table) {
                for (String column : rows) {
                    System.out.print(column + " | ");
                }
                System.out.print("\n");
            }


            return null;
        } catch (SQLException e) {
            System.out.println("SQLException upon calling getTupleList");
            return null;
        }

    }

    private String[] getBookData() {
        return null;
    }

    private String[] getDvdData() {
        return null;
    }

    private String[] getComputerData() {
        return null;
    }

}
