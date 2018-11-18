import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Responsible for fetching, adding and editing resources from the database.
 * @author Noah Lenagan
 * @version 1.0
 */
public class ResourceManager {

    /**
     * The table name that represents Resource.
     */
    private static final String RESOURCE_TABLE_NAME = "Resource";

    /**
     * The table name that represents Book.
     */
    private static final String BOOK_TABLE_NAME = "Book";

    /**
     * The table name that represents Dvd.
     */
    private static final String DVD_TABLE_NAME = "Dvd";

    /**
     * The table name that represents Computer.
     */
    private static final String COMPUTER_TABLE_NAME = "Computer";

    /**
     * The column position in the table Resource, which represents TID.
     * (The type ID of the resource).
     */
    private static final int TID_ATTRIBUTE_POSITION = 4;

    /**
     * An instance of DatabaseManager.
     */
    private DatabaseManager dbManager;

    /**
     * Creates ResourceManager.
     * @param dbManager An instance of DatabaseManager
     */
    public ResourceManager(DatabaseManager dbManager) {

        this.dbManager = dbManager;

    }

    /*
    + getCopies (resourceID: Integer) : Copy[]
    + getCopies () : Copy[]
    + editResource (resourceID: Integer, newResource : Book) : Boolean
    + editResource (resourceID: Integer, newResource : Dvd) : Boolean
    + editResource (resourceID: Integer, newResource : Computer) : Boolean
    + addResource (newResource : Book)
    + addResource (newResource : Dvd)
    + addResource (newResource : Computer)


     */

    /**
     * Gets all resources found in the database.
     * @return All resources found in the database as Resource object.
     */
    public Resource[] getResourceList() {

        String[][] table;

        try {

            table = dbManager.getTupleList(RESOURCE_TABLE_NAME);
            return constructResources(table);

        } catch (SQLException e) {
            System.out.println("SQLException upon calling getTupleList");
            return null;
        }

    }

    public Resource[] getResourceList(String SQLQuery) {

        String[][] table;

        try {

            table = dbManager.getTupleListByQuery(SQLQuery);
            return constructResources(table);

        } catch (SQLException e) {
            System.out.println("SQLException upon calling getTupleListByQuery");
            return null;
        }

    }

    public Resource[] searchResources(String selectColumn, String searchQuery) {

        String[][] table;

        try {

            table = dbManager.searchTuples("Resource", selectColumn, searchQuery);
            return constructResources(table);

        } catch (SQLException e) {
            System.out.println("SQLException upon calling getTupleListByQuery");
            return null;
        }

    }

    public void addResource(Book newBook) {

        /*
        Stage indicates how far the progression of the operation has gone. If an exception were to be thrown, the database
        must be reverted.
         */
        int stage = 0;
        int resourceID = 0;

        try {
            dbManager.addTuple("Resource", new String[]{"null", newBook.title, Integer.toString(newBook.year),
                    Integer.toString(newBook.thumbImageID), Integer.toString(newBook.thumbImageID), "null", "null", "null",
                    "null"});

            resourceID = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT last_insert_rowid()")[0]);

            stage = 1;

            dbManager.addTuple("Book", new String[] {"null", Integer.toString(resourceID),
                    newBook.getAuthor(), newBook.getPublisher(), newBook.getGenre(), newBook.getIsbn()});

            stage = 2;

            //check if language already exists in db.
            String[][] result = dbManager.searchTuples("Language", "Language", newBook.getLang());

            //The id of the language, a string is used as will only be used in sqlQueries.
            String langID;

            //if length is greater than 0, then the language must exist.
            if (result.length > 0) {
                //set the langID where langID is the first column in the row. The first row is taken.
                langID = result[0][0];

                dbManager.addTuple("ResourceLanguage", new String[] {Integer.toString(resourceID),
                        langID});
            } else {
                System.out.println("fuck");
            }

        } catch (SQLException e) {
            System.out.println("Adding resource failed.");
        }

    }

    private Resource[] constructResources(String[][] table) {
        ArrayList<Resource> resources = new ArrayList<>();

        for (String[] row : table) {

            Resource newResource = null;

            if (row[TID_ATTRIBUTE_POSITION].equals("1")) {
                newResource = constructBook(row);
            } else if (row[TID_ATTRIBUTE_POSITION].equals("2")) {
                newResource = constructDvd(row);
            } else if (row[TID_ATTRIBUTE_POSITION].equals("3")) {
                newResource = constructComputer(row);
            }

            if (newResource != null) {
                resources.add(newResource);
            }

        }

        return resources.toArray(new Resource[resources.size()]);

    }

    /**
     * Creates a Book object from the database and passed data.
     * @param row The row data from resources.
     * @return The constructed Book object.
     */
    private Book constructBook(String[] row) {

        int rid = Integer.parseInt(row[0]);

        String[] bookExtraData = getBookData(rid);

        if (bookExtraData != null && row != null) {

            try {

                String language = getLanguage(rid);

                return new Book(rid, row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                        bookExtraData[0], bookExtraData[1], bookExtraData[2], bookExtraData[3], language);

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException: Required data does not exist.");
                return null;
            }

        } else {
            System.out.println("Passed parameter is null or could not retrieve further data.");
            return null;
        }

    }

    /**
     * Creates a Dvd object from the database and passed data.
     * @param row The row data from resources.
     * @return The constructed Dvd object.
     */
    private Dvd constructDvd(String[] row) {

        int rid = Integer.parseInt(row[0]);

        String[] dvdExtraData = getDvdData(rid);

        if (dvdExtraData != null && row != null) {

            try {

                String language = getLanguage(rid);
                String[] subtitleLanguages = getSubtitleLanguages(rid);

                if (subtitleLanguages != null) {

                    return new Dvd(rid, row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                            dvdExtraData[0], Integer.parseInt(dvdExtraData[1]), language, subtitleLanguages);

                } else {
                    System.out.println("Error retrieving subtitle languages");
                    return null;
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException: Required data does not exist.");
                return null;
            }

        } else {
            System.out.println("Passed parameter is null or could not retrieve further data.");
            return null;
        }

    }

    /**
     * Creates a Computer object from the database and passed data.
     * @param row The row data from resources.
     * @return The constructed Computer object.
     */
    private Computer constructComputer(String[] row) {

        int rid = Integer.parseInt(row[0]);

        String[] computerExtraData = getComputerData(rid);

        if (computerExtraData != null && row != null) {

            try {
                return new Computer(rid, row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                        computerExtraData[0], computerExtraData[1], computerExtraData[2]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ArrayIndexOutOfBoundsException: Required data does not exist.");
                return null;
            }

        } else {
            System.out.println("Passed parameter is null or could not retrieve further data.");
            return null;
        }

    }

    /**
     * Gets the language of the resource.
     * @param rid Uniquely identifies the resource.
     * @return The language of the resource.
     */
    private String getLanguage(int rid) {

        try {
            String[] row = dbManager.getFirstTupleByQuery("SELECT Language FROM ResourceLanguage, Language WHERE " +
                    rid + " = RID AND ResourceLanguage.LangID = Language.LangID");

            if (row != null) {
                return row[0];
            } else {
                return "";
            }
        } catch(SQLException e) {
            System.out.println("SQLException upon calling getLanguages");
            return "";
        }

    }

    /**
     * Gets all subtitle languages of a resource.
     * @param rid Uniquely identifies the resource.
     * @return All subtitle languages of the resource.
     */
    private String[] getSubtitleLanguages(int rid) {

        try {
            String[][] table = dbManager.getTupleListByQuery("SELECT Subtitle_Language FROM DvdSubtitleLanguage, SubtitleLanguage WHERE " +
                    rid + " = RID AND DvdSubtitleLanguage.SubID = SubtitleLanguage.SubID");

            String[] subLanguages = new String[table.length];

            for (int iCount = 0; iCount < table.length; iCount++) {
                subLanguages[iCount] = table[iCount][0];
            }

            return subLanguages;
        } catch(SQLException e) {
            System.out.println("SQLException upon calling getLanguages");
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

        String[] tuple = null;
        try {
            tuple = dbManager.getFirstTupleByQuery("SELECT Director, Runtime " +
                    "FROM " + DVD_TABLE_NAME + " WHERE RID = " + rid + ";");
        } catch (SQLException e) {
            System.out.println("SQLException upon calling getDvdData");
        } finally {
            return tuple;
        }

    }

    private String[] getComputerData(int rid) {

        String[] tuple = null;
        try {
            tuple = dbManager.getFirstTupleByQuery("SELECT Manufacturer, Model, Installed_OS " +
                    "FROM " + COMPUTER_TABLE_NAME + " WHERE RID = " + rid + ";");
        } catch (SQLException e) {
            System.out.println("SQLException upon calling getComputerData");
        } finally {
            return tuple;
        }

    }

}
