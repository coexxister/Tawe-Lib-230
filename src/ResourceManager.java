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

    public boolean addResource(Book newBook) {

        /*
        Stage indicates how far the progression of the operation has gone. If an exception were to be thrown, the database
        must be reverted.
         */
        int stage = 0;

        String resourceID = "0";

        //The id of the language, a string is used as will only be used in sqlQueries.
        String langID = "0";

        try {

            //if the resource doesnt exist then add to the database. Otherwise return false.
            if (!dbManager.checkIfExist(RESOURCE_TABLE_NAME, new String[] {"Title", "RYear"}, new String[]
                    {encase(newBook.getTitle()), Integer.toString(newBook.getYear())}))
            {
                //The resource type is 1 corresponding to a book.
                int bookTypeID = 1;

                //Add the the resource to the resource table.
                dbManager.addTuple("Resource", new String[]{"null", encase(newBook.title), Integer.toString(newBook.year),
                        Integer.toString(newBook.thumbImageID), Integer.toString(bookTypeID), "null", "null", "null",
                        "null"});

                //get the resourceID of the resource by getting the largest primary key in Resource.
                resourceID = dbManager.getFirstTupleByQuery("SELECT max(RID) FROM Resource")[0];

                stage = 1;

                //add book to book table.
                dbManager.addTuple("Book", new String[]{"null", resourceID,
                        encase(newBook.getAuthor()), encase(newBook.getPublisher()), encase(newBook.getGenre()),
                        encase(newBook.getIsbn())});

                stage = 2;

                //Assign the language to the resource, returning its new id.
                langID = assignResourceLanguage(resourceID, newBook.getLang());

                stage = 3;

                System.out.println("Successfully added Book");
                return true;

            } else {
                System.out.println("Book already exists");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Adding resource failed.");

            //Revert the database to the previous state.
            /*
            break is not used in cases as for every stage increased, the previous stages must be reverted
            from the database.
            */
            switch (stage) {
                case 3: //del language entry in db.
                    dbManager.deleteTuple("ResourceLanguage", new String[]{"RID", "LangID"}, new String[]{resourceID, langID});
                case 2: //del book entry in db.
                    dbManager.deleteTuple("Book", new String[]{"RID"}, new String[]{resourceID});
                case 1: //del resource entry in db.
                    dbManager.deleteTuple("Resource", new String[]{"RID"}, new String[]{resourceID});
                default:
                    break;
            }

            System.out.println("Successfully reverted database");
            return false;
        }

    }

    public boolean addResource(Dvd newDvd) {

        /*
        Stage indicates how far the progression of the operation has gone. If an exception were to be thrown, the database
        must be reverted.
         */
        int stage = 0;

        String resourceID = "";

        //The id of the language, a string is used as will only be used in sqlQueries.
        String langID = "";
        String[] subLangID = null;

        try {

            //if the resource doesnt exist then add to the database. Otherwise return false.
            if (!dbManager.checkIfExist(RESOURCE_TABLE_NAME, new String[]{"Title", "RYear"}, new String[]
                    {encase(newDvd.getTitle()), Integer.toString(newDvd.getYear())})) {
                //The resource type is 1 corresponding to a book.
                int bookTypeID = 1;

                //Add the the resource to the resource table.
                dbManager.addTuple("Resource", new String[]{"null", encase(newDvd.title), Integer.toString(newDvd.year),
                        Integer.toString(newDvd.thumbImageID), Integer.toString(bookTypeID), "null", "null", "null",
                        "null"});

                //get the resourceID of the resource by getting the largest primary key in Resource.
                resourceID = dbManager.getFirstTupleByQuery("SELECT max(RID) FROM Resource")[0];

                stage = 1;

                //add book to book table.
                dbManager.addTuple("Dvd", new String[]{"null", resourceID,
                        encase(newDvd.getDirector()), Integer.toString(newDvd.getRunTime())});

                stage = 2;

                //Assign the language to the resource, returning its new lang ID.
                langID = assignResourceLanguage(resourceID, newDvd.getLanguage());

                stage = 3;

                //Assign the subtitle languages to the resource, returning its new subLangIDs.
                subLangID = assignSubtitleLanguages(resourceID, newDvd.getSubLang());

                System.out.println("Successfully added Dvd");
                return true;

            } else {
                System.out.println("Dvd already exists");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Adding resource failed.");

            //Revert the database to the previous state.
            /*
            break is not used in cases as for every stage increased, the previous stages must be reverted
            from the database.
            */
            switch (stage) {
                case 4: //del every sub languages entry in db.
                case 3: //del language entry in db.
                    dbManager.deleteTuple("ResourceLanguage", new String[]{"RID", "LangID"}, new String[]{resourceID, langID});
                case 2: //del book entry in db.
                    dbManager.deleteTuple("Dvd", new String[]{"RID"}, new String[]{resourceID});
                case 1: //del resource entry in db.
                    dbManager.deleteTuple("Resource", new String[]{"RID"}, new String[]{resourceID});
                default:
                    break;
            }

            System.out.println("Successfully reverted database");
            return false;
        }

    }

    public boolean addResource(Computer newComputer) {
        return false;
    }

    private String assignResourceLanguage(String rid, String language) throws SQLException {

        //Uniquely identifies the language.
        String langID;

        //check if language already exists in db.
        String[][] result = dbManager.searchTuples("Language", "Language", language);

        //if length is greater than 0, then the language must exist. Otherwise add the language.
        if (result.length > 0) {
            //set the langID where langID is the first column in the row. The first row is taken.
            langID = result[0][0];
        } else {
            System.out.println("Unknown language detected - adding to database.");

            dbManager.addTuple("Language", new String[]{"null", encase(language)});
            langID = dbManager.getFirstTupleByQuery("SELECT max(LangID) FROM Language")[0];
        }

        //Now make the association between the langID and RID
        dbManager.addTuple("ResourceLanguage", new String[]{rid,
                langID});

        return langID;

    }

    private String[] assignSubtitleLanguages(String rid, String[] subtitleLang) throws SQLException {

        //Uniquely identifies the subtitle languages.
        String[] langID = new String[subtitleLang.length];

        for (int iCount = 0; iCount < subtitleLang.length; iCount++) {

            //check if language already exists in db.
            String[][] result = dbManager.searchTuples("SubtitleLanguage", "Subtitle_Language",
                        subtitleLang[iCount]);

            //if length is greater than 0, then the language must exist. Otherwise add the language.
            if (result.length > 0) {
                //set the langID where langID is the first column in the row. The first row is taken.
                langID[iCount] = result[0][0];
            } else {
                System.out.println("Unknown subtitle language detected - " + subtitleLang[iCount] + " - adding to database.");

                dbManager.addTuple("SubtitleLanguage", new String[]{"null", encase(subtitleLang[iCount])});
                langID[iCount] = dbManager.getFirstTupleByQuery("SELECT max(SubID) FROM SubtitleLanguage")[0];
            }

            //Now make the association between the langID and RID
            dbManager.addTuple("DvdSubtitleLanguage", new String[]{rid,
                    langID[iCount]});

        }

        return langID;

    }

    private String encase(String str) {
        return "'" + str + "'";
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
