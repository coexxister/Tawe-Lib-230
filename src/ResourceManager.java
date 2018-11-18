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

            for (String[] row : table) {

                if (row[TID_ATTRIBUTE_POSITION].equals("1")) {
                    resources.add(createBook(row));
                }

            }

            return resources.toArray(new Resource[resources.size()]);

        } catch (SQLException e) {
            System.out.println("SQLException upon calling getTupleList");
            return null;
        }

    }

    private Book createBook(String[] row) {

        String[] bookExtraData = getBookData(Integer.parseInt(row[0]));

        if (bookExtraData != null) {

            /*

            GET THE LANGUAGE AND REPLACE TURKEY

             */

            return new Book(Integer.parseInt(row[0]), row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                    bookExtraData[0], bookExtraData[1], bookExtraData[2], bookExtraData[3], "Turkey");

        } else {
            return null;
        }
    }

    private String[] getBookData(int rid) {

        String[] tuple = null;
        try {
            tuple = dbManager.getFirstTupleByQuery("SELECT Author, Publisher, Genre, ISBN " +
                    "FROM " + BOOK_TABLE_NAME + " WHERE RID = " + rid + ";");
        } catch (SQLException e) {
            System.out.println("SQLException upon calling getBookData");
        } finally {
            return tuple;
        }

    }

    private String[] getDvdData(int rid) {
        return null;
    }

    private String[] getComputerData(int rid) {
        return null;
    }

}
