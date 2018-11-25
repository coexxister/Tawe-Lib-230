import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Responsible for fetching, adding and editing resources from the database.
 * @author Noah Lenagan
 * @version 1.0
 */
public class ResourceManager {

    /**
     * The default amount of copies to add if no amount is specified.
     */
    private static final int DEFAULT_COPIES_PER_RESOURCE = 5;

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

    /**
     * Gets all resources found in the database.
     * @return All resources found in the database as Resource object.
     */
    public Resource[] getResourceList() {

        String[][] table;

        try {

            table = dbManager.getTupleList("Resource");
            return constructResources(table);

        } catch (SQLException e) {
            /*
            Catch any sql errors and return null.
            Any errors will most likely be as a result of a connection failure to the database or missing tables.
             */
            return null;
        }

    }

    /**
     * Gets all resource objects based upon a sql query.
     * @param SQLQuery The SQL query to execute upon the database.
     * @return An array of resources.
     * @throws SQLException Thrown when the sql query is invalid.
     */
    public Resource[] getResourceList(String SQLQuery) throws SQLException {

        String[][] table;

        table = dbManager.getTupleListByQuery(SQLQuery);
        return constructResources(table);

    }

    /**
     * Gets all resource objects based upon a search on a column.
     * @param selectColumn The column to search upon.
     * @param searchQuery The query to search on the column.
     * @return An array of resource objects.
     * @throws SQLException Thrown if column specified is incorrect or connection to database couldn't be established.
     */
    public Resource[] searchResources(String selectColumn, String searchQuery) throws SQLException {

        String[][] table;
        table = dbManager.searchTuples("Resource", selectColumn, searchQuery);
        return constructResources(table);

    }

    /**
     * Gets all copies of a resource.
     * @param resourceID The resource id of a copy.
     * @return All copies of a resource.
     */
    public Copy[] getCopies(int resourceID) {

        try {

            //Get the amount of copies
            int amountOfCopies = Integer.parseInt(dbManager.getFirstTupleByQuery("SELECT count(*) FROM Copy GROUP BY RID")[0]);

            Copy[] copies = new Copy[amountOfCopies];

            //Get all copy rows based upon a rid
            String[][] copyRows = dbManager.getTupleListByQuery("SELECT * FROM Copy WHERE RID = "
                    + Integer.toString(resourceID));

            //For every copy associated with the rid, will be constructed and added to the array of copies.
            for (int iCount = 0; iCount < amountOfCopies; iCount++) {
                copies[iCount] = new Copy(Integer.parseInt(copyRows[iCount][0]), Integer.parseInt(copyRows[iCount][1]),
                        Integer.parseInt(copyRows[iCount][2]), copyRows[iCount][3], Integer.parseInt(copyRows[iCount][4]),
                        Integer.parseInt(copyRows[iCount][5]));
            }

            return copies;

        } catch(SQLException e) {
            return null;
        }

    }

    /**
     * Adds multiple copies of a resource to the database.
     * @param newCopy The copy to add.
     */
    public boolean addBulkCopies(Copy newCopy) {

        return addBulkCopies(newCopy, DEFAULT_COPIES_PER_RESOURCE);

    }

    /**
     * Adds multiple copies of a resource to the database.
     * @param newCopy The copy to add.
     * @param amount The amount of copies to add.
     */
    public boolean addBulkCopies(Copy newCopy, int amount) {

        boolean isSuccess = true;

        //For every copy to add, add the copy to the database.
        for (int iCount = 0; iCount < amount; iCount++) {
            //if addCopy failed then isSuccess must equal false.
            if (!addCopy(newCopy)) {
                isSuccess = false;
            }
        }

        return isSuccess;
    }

    /**
     * Adds a copy of a resource to the database.
     * @param newCopy The copy to add.
     * @return True if operation was a success. False if failure.
     */
    public boolean addCopy(Copy newCopy) {
        try {

            //Add the copy to the database.
            dbManager.addTuple("Copy", new String[] {"null", Integer.toString(newCopy.getResourceID()),
                    Integer.toString(newCopy.getLoanDuration()), encase(newCopy.getDueDate()),
                    Integer.toString(newCopy.getStateID()), Integer.toString(newCopy.getCurrentBorrowerID()),
                    "null", "null"});

            //ENQUEUE THE COPY

            //Get copy id.
            String copyID = dbManager.getFirstTupleByQuery("SELECT max(CPID) FROM Copy")[0];
            String resourceID = Integer.toString(newCopy.getResourceID());

            //check if there is an a item in the available queue.
            String[] queryResult = dbManager.getFirstTupleByQuery("SELECT HeadOfAvailableQueue, TailOfAvailableQueue" +
                    " FROM Resource WHERE RID = " + Integer.toString(newCopy.getResourceID()));
            String head = queryResult[0];
            String tail = queryResult[1];

            if (head == null) {
                //add copy to head and tail, the row is edited as the auto increment primary key must be found before
                //adding the id to the queue.
                dbManager.editTuple("Resource", new String[] {"HeadOfAvailableQueue", "TailOfAvailableQueue"},
                        new String[] {copyID, copyID}, "RID", resourceID);
            } else {
                //insert at the tail.

                //assign the next queue item for the tail item.
                dbManager.editTuple("Copy", new String[] {"NextCopyInQueue"}, new String[] {copyID},
                        "CPID", tail);

                //assign the previous queue item for the new item.
                dbManager.editTuple("Copy", new String[] {"PreviousCopyInQueue"}, new String[] {tail},
                        "CPID", copyID);

                //assign the new tail.
                dbManager.editTuple("Resource", new String[] {"TailOfAvailableQueue"},
                        new String[] {copyID}, "RID", resourceID);
            }

            return true;

        } catch(SQLException e) {

            //If an exception, return false to indicate failure to add copy.
            return false;

        }
    }

    /**
     * Edits/replaces an existing resource with a new resource.
     * @param newResource The new resource with information to overwrite.
     * @param type The type of resource.
     * @return Returns true if the operation was a success. False if failed.
     */
    private boolean editResource(Resource newResource, int type) {

        try {
            //get old resource data
            String[] resourceRow = dbManager.searchTuples("Resource", "RID",
                    Integer.toString(newResource.getResourceID()))[0];

            //edit resource in resource table
            dbManager.editTuple("Resource", new String[]{"RID", "Title", "RYear",
                            "ImageID", "TID", "HeadOfAvailableQueue", "TailOfAvailableQueue",
                            "HeadOfBorrowedQueue", "TailOfBorrowedQueue"},
                    new String[]{Integer.toString(newResource.resourceID), encase(newResource.getTitle()),
                            Integer.toString(newResource.getYear()), Integer.toString(newResource.getThumbImage()),
                            Integer.toString(type), resourceRow[5], resourceRow[6], resourceRow[7], resourceRow[8]},
                    "RID", Integer.toString(newResource.resourceID));
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    /**
     * Edits/replaces an existing book resource with the passed new book.
     * @param newResource The new book with information to overwrite.
     * @return Returns true if the operation was a success. False if failed.
     */
    public boolean editResource(Book newResource) {

        try {

            //Attempt to edit the resource table.
            boolean isEditSuccess = editResource((Resource)newResource, 1);

            //If the edit of the resource table was a success then continue editing.
            if (isEditSuccess) {
                //get old book data
                String[] bookRow = dbManager.searchTuples("Book", "RID",
                        Integer.toString(newResource.getResourceID()))[0];

                //edit book in book table
                dbManager.editTuple("Book", new String[]{"BID", "RID", "Author", "Publisher",
                        "Genre", "ISBN"}, new String[]{bookRow[0], Integer.toString(newResource.resourceID),
                        encase(newResource.getAuthor()), encase(newResource.getPublisher()), encase(newResource.getGenre()),
                        encase(newResource.getIsbn())}, "RID", Integer.toString(newResource.resourceID));

                dbManager.deleteTuple("ResourceLanguage", new String[]{"RID"},
                        new String[]{Integer.toString(newResource.resourceID)});

                assignResourceLanguage(Integer.toString(newResource.resourceID), newResource.getLang());

                return true;
            } else {
                return false;
            }

        } catch(SQLException e) {
            return false;
        }
    }

    /**
     * Edits/replaces an existing dvd resource with the passed new dvd.
     * @param newResource The new dvd with information to overwrite.
     * @return Returns true if the operation was a success. False if failed.
     */
    public boolean editResource(Dvd newResource) {

        try {

            //Attempt to edit the resource table.
            boolean isEditSuccess = editResource((Resource) newResource, 2);

            //If the edit of the resource table was a success then continue editing.
            if (isEditSuccess) {

                //get old dvd data
                String[] dvdRow = dbManager.searchTuples("Dvd", "RID",
                        Integer.toString(newResource.getResourceID()))[0];

                //edit book in dvd table
                dbManager.editTuple("Dvd", new String[]{"DID", "RID", "Director", "Runtime"},
                        new String[]{dvdRow[0], Integer.toString(newResource.resourceID),
                        encase(newResource.getDirector()), Integer.toString(newResource.getRunTime())},
                        "RID", Integer.toString(newResource.resourceID));

                //remove language
                dbManager.deleteTuple("ResourceLanguage", new String[] {"RID"},
                        new String[] {Integer.toString(newResource.resourceID)});

                //add language
                assignResourceLanguage(Integer.toString(newResource.resourceID), newResource.getLanguage());

                //remove subtitles
                dbManager.deleteTuple("DvdSubtitleLanguage", new String[] {"RID"},
                        new String[] {Integer.toString(newResource.resourceID)});

                //add subtitles
                assignSubtitleLanguages(Integer.toString(newResource.resourceID), newResource.getSubLang());

                return true;

            } else {
                return false;
            }

        } catch(SQLException e) {
            return false;
        }
    }

    /**
     * Edits/replaces an existing computer resource with the passed new computer.
     * @param newResource The new computer with information to overwrite.
     * @return Returns true if the operation was a success. False if failed.
     */
    public boolean editResource(Computer newResource) {

        try {

            //Attempt to edit the resource table.
            boolean isEditSuccess = editResource((Resource)newResource, 3);

            //If the edit of the resource table was a success then continue editing.
            if (isEditSuccess) {

                //get old computer data
                String[] computerRow = dbManager.searchTuples("Computer", "RID",
                        Integer.toString(newResource.getResourceID()))[0];

                //edit book in computer table
                dbManager.editTuple("Computer", new String[]{"CID", "RID", "Manufacturer", "Model", "Installed_OS"},
                        new String[]{computerRow[0], Integer.toString(newResource.resourceID),
                                encase(newResource.getManufacturer()), encase(newResource.getModel()),
                                encase(newResource.getOs())},"RID", Integer.toString(newResource.resourceID));

                return true;

            } else {
                return false;
            }

        } catch(SQLException e) {
            return false;
        }

    }

    /**
     * Adds a new book to the database.
     * @param newBook The new book to add.
     * @return True if the operation was a success. False if failed.
     * @throws ResourceDuplicateException Thrown if a duplicate resource is trying to be added.
     */
    public boolean addResource(Book newBook) throws ResourceDuplicateException {

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
            if (!dbManager.checkIfExist("Resource", new String[] {"Title", "RYear"}, new String[]
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

                return true;

            } else {
                //Book already exists.
                throw new ResourceDuplicateException("Resource already exists in database.");
            }
        } catch (SQLException e) {

            //Revert the database to the previous state.
            /*
            break is not used in cases as for every stage increased, the previous stages must be reverted
            from the database.
            */

            try {
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

                return false;
            } catch (IllegalArgumentException f) {
                //Arguments are incorrect which suggests table doesn't exist or exception should have
                // been thrown after stage increase!
                return false;
            } catch (SQLException f) {
                //Clearly the database tables don't exist or database file has moved!
                return false;
            }
        }

    }

    /**
     * Adds a new dvd to the database.
     * @param newDvd The new dvd to add.
     * @return True if the operation was a success. False if failed.
     * @throws ResourceDuplicateException Thrown if a duplicate resource is trying to be added.
     */
    public boolean addResource(Dvd newDvd) throws ResourceDuplicateException {

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
            if (!dbManager.checkIfExist("Resource", new String[]{"Title", "RYear"}, new String[]
                    {encase(newDvd.getTitle()), Integer.toString(newDvd.getYear())})) {
                //The resource type is 1 corresponding to a Dvd.
                int dvdTypeID = 2;

                //Add the the resource to the resource table.
                dbManager.addTuple("Resource", new String[]{"null", encase(newDvd.title), Integer.toString(newDvd.year),
                        Integer.toString(newDvd.thumbImageID), Integer.toString(dvdTypeID), "null", "null", "null",
                        "null"});

                //get the resourceID of the resource by getting the largest primary key in Resource.
                resourceID = dbManager.getFirstTupleByQuery("SELECT max(RID) FROM Resource")[0];

                stage = 1;

                //add Dvd to Dvd table.
                dbManager.addTuple("Dvd", new String[]{"null", resourceID,
                        encase(newDvd.getDirector()), Integer.toString(newDvd.getRunTime())});

                stage = 2;

                //Assign the language to the resource, returning its new lang ID.
                langID = assignResourceLanguage(resourceID, newDvd.getLanguage());

                stage = 3;

                //Assign the subtitle languages to the resource, returning its new subLangIDs.
                subLangID = assignSubtitleLanguages(resourceID, newDvd.getSubLang());

                stage = 4;

                return true;

            } else {
                //Dvd already exists.
                throw new ResourceDuplicateException("Resource already exists in database.");
            }
        } catch (SQLException e) {

            //Revert the database to the previous state.
            /*
            break is not used in cases as for every stage increased, the previous stages must be reverted
            from the database.
            */
            try {
                switch (stage) {
                    case 4: //del every sub languages entry in db.
                        for (int iCount = 0; iCount < subLangID.length; iCount++) {
                            dbManager.deleteTuple("DvdSubtitleLanguage", new String[]{"RID", "SubID"},
                                    new String[]{resourceID, subLangID[iCount]});
                        }
                    case 3: //del language entry in db.
                        dbManager.deleteTuple("ResourceLanguage", new String[]{"RID", "LangID"}, new String[]{resourceID, langID});
                    case 2: //del book entry in db.
                        dbManager.deleteTuple("Dvd", new String[]{"RID"}, new String[]{resourceID});
                    case 1: //del resource entry in db.
                        dbManager.deleteTuple("Resource", new String[]{"RID"}, new String[]{resourceID});
                    default:
                        break;
                }

                return false;
            } catch (IllegalArgumentException f) {
                //Arguments are incorrect which suggests table doesn't exist or exception should have
                // been thrown after stage increase!
                return false;
            } catch (SQLException f) {
                //Clearly the database tables don't exist or database file has moved!
                return false;
            }
        }

    }

    /**
     * Adds a new computer to the database.
     * @param newComputer The new computer to add.
     * @return True if the operation was a success. False if failed.
     * @throws ResourceDuplicateException Thrown if a duplicate resource is trying to be added.
     */
    public boolean addResource(Computer newComputer) throws ResourceDuplicateException {

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
            if (!dbManager.checkIfExist("Resource", new String[]{"Title", "RYear"}, new String[]
                    {encase(newComputer.getTitle()), Integer.toString(newComputer.getYear())})) {
                //The resource type is 1 corresponding to a Computer.
                int computerTypeID = 3;

                //Add the the resource to the resource table.
                dbManager.addTuple("Resource", new String[]{"null", encase(newComputer.title), Integer.toString(newComputer.year),
                        Integer.toString(newComputer.thumbImageID), Integer.toString(computerTypeID), "null", "null", "null",
                        "null"});

                //get the resourceID of the resource by getting the largest primary key in Resource.
                resourceID = dbManager.getFirstTupleByQuery("SELECT max(RID) FROM Resource")[0];

                stage = 1;

                //add Computer to Computer table.
                dbManager.addTuple("Computer", new String[]{"null", resourceID,
                        encase(newComputer.getManufacturer()), encase(newComputer.getModel()), encase(newComputer.getOs())});

                stage = 2;

                return true;

            } else {
                //Computer already exists.
                throw new ResourceDuplicateException("Resource already exists in database.");
            }
        } catch (SQLException e) {

            //Revert the database to the previous state.
            /*
            break is not used in cases as for every stage increased, the previous stages must be reverted
            from the database.
            */

            try {
                switch (stage) {
                    case 2: //del book entry in db.
                        dbManager.deleteTuple("Computer", new String[]{"RID"}, new String[]{resourceID});
                    case 1: //del resource entry in db.
                        dbManager.deleteTuple("Resource", new String[]{"RID"}, new String[]{resourceID});
                    default:
                        break;
                }

                return false;
            } catch (IllegalArgumentException f) {
                //Arguments are incorrect which suggests table doesn't exist or exception should have
                // been thrown after stage increase!
                return false;
            } catch (SQLException f) {
                //Clearly the database tables don't exist or database file has moved!
                return false;
            }
        }

    }

    /**
     * Assigns a language to the resource in the database.
     * @param rid Identifies which resource to assign the language to.
     * @param language The language to assign. If it does not already exist it will be added to the database.
     * @return Returns the unique identifier for language.
     * @throws SQLException Thrown if the resource specified was not found or connection to database could not be established.
     */
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
            //Adding new language
            dbManager.addTuple("Language", new String[]{"null", encase(language)});
            //Set langID
            langID = dbManager.getFirstTupleByQuery("SELECT max(LangID) FROM Language")[0];
        }

        //Now make the association between the langID and RID
        dbManager.addTuple("ResourceLanguage", new String[]{rid,
                langID});

        return langID;

    }

    /**
     * Assigns multiple subtitle languages to the resource in the database.
     * @param rid Identifies which resource to assign the subtitle languages to.
     * @param subtitleLang The subtitle languages to assign. If they do not exist, they will be added to the database.
     * @return Returns an array of unique identifiers for the subtitle languages.
     * @throws SQLException Thrown if the resource specified was not found or connection to database could not be established.
     */
    private String[] assignSubtitleLanguages(String rid, String[] subtitleLang) throws SQLException {

        //Uniquely identifies the subtitle languages.
        String[] langID = new String[subtitleLang.length];

        //for every subtitle language, assign the language to the resource.
        for (int iCount = 0; iCount < subtitleLang.length; iCount++) {

            //check if language already exists in db.
            String[][] result = dbManager.searchTuples("SubtitleLanguage", "Subtitle_Language",
                        subtitleLang[iCount]);

            //if length is greater than 0, then the language must exist. Otherwise add the language.
            if (result.length > 0) {
                //set the langID where langID is the first column in the row. The first row is taken.
                langID[iCount] = result[0][0];
            } else {
                //Adding new subtitle language.
                dbManager.addTuple("SubtitleLanguage", new String[]{"null", encase(subtitleLang[iCount])});
                //Set the langID.
                langID[iCount] = dbManager.getFirstTupleByQuery("SELECT max(SubID) FROM SubtitleLanguage")[0];
            }

            //Now make the association between the langID and RID
            dbManager.addTuple("DvdSubtitleLanguage", new String[]{rid,
                    langID[iCount]});

        }

        return langID;

    }

    /**
     * Encases a string in apostrophe marks.
     * @param str The string to encase.
     * @return The encased string.
     */
    private String encase(String str) {
        return "'" + str + "'";
    }

    /**
     * Constructs multiple resources to their respective type from a table of resource data and then returns them.
     * @param table The data in which to construct the resource objects from.
     * @return The array of resources.
     */
    private Resource[] constructResources(String[][] table) {

        //an ArrayList is used as some rows may not be constructed in to resources. Therefore the size is unknown.
        ArrayList<Resource> resources = new ArrayList<>();

        //The column position which identifies the type id of a resource.
        final int TID_ATTRIBUTE_POSITION = 4;

        //for every row in the table construct a resource.
        for (String[] row : table) {

            Resource newResource = null;

            //determine whether the resource is a book, dvd or computer.
            if (row[TID_ATTRIBUTE_POSITION].equals("1")) {
                newResource = constructBook(row);
            } else if (row[TID_ATTRIBUTE_POSITION].equals("2")) {
                newResource = constructDvd(row);
            } else if (row[TID_ATTRIBUTE_POSITION].equals("3")) {
                newResource = constructComputer(row);
            }

            //if the newly constructed resource isn't null, add the ArrayList.
            if (newResource != null) {
                resources.add(newResource);
            }

        }

        //convert the ArrayList to an array.
        return resources.toArray(new Resource[resources.size()]);

    }

    /**
     * Creates a Book object from the database and passed data.
     * @param row The row data from resources.
     * @return The constructed Book object.
     */
    private Book constructBook(String[] row) {

        //The resource id.
        int rid = Integer.parseInt(row[0]);

        //Data from the book table.
        String[] bookExtraData = getBookData(rid);

        //If the passed row and the fetched book data not null then construct the book, otherwise return null.
        if (bookExtraData != null && row != null) {

            try {

                //Get the language corresponding to the resource.
                String language = getLanguage(rid);

                //Return the constructed book.
                return new Book(rid, row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                        bookExtraData[0], bookExtraData[1], bookExtraData[2], bookExtraData[3], language);

            } catch (ArrayIndexOutOfBoundsException e) {
                //returns null if data is invalid.
                return null;
            }

        } else {
            //returns null if the passed data is null or book data could not be retrieved.
            return null;
        }

    }

    /**
     * Creates a Dvd object from the database and passed data.
     * @param row The row data from resources.
     * @return The constructed Dvd object.
     */
    private Dvd constructDvd(String[] row) {

        //The resource id.
        int rid = Integer.parseInt(row[0]);

        //Data from the dvd table.
        String[] dvdExtraData = getDvdData(rid);

        //If the passed row and the fetched dvd data not null then construct the dvd, otherwise return null.
        if (dvdExtraData != null && row != null) {

            try {

                //Get the language corresponding to the resource.
                String language = getLanguage(rid);

                //Get the subtitle languages corresponding to the resource.
                String[] subtitleLanguages = getSubtitleLanguages(rid);

                //If the subtitle languages does not equal null
                if (subtitleLanguages != null) {

                    //Return the constructed dvd.
                    return new Dvd(rid, row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                            dvdExtraData[0], Integer.parseInt(dvdExtraData[1]), language, subtitleLanguages);

                } else {
                    //returns null if subtitle languages could not be found.
                    return null;
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                //returns null if data is invalid.
                return null;
            }

        } else {
            //returns null if the passed data is null or dvd data could not be retrieved.
            return null;
        }

    }

    /**
     * Creates a Computer object from the database and passed data.
     * @param row The row data from resources.
     * @return The constructed Computer object.
     */
    private Computer constructComputer(String[] row) {

        //The resource id.
        int rid = Integer.parseInt(row[0]);

        //Data from the computer table.
        String[] computerExtraData = getComputerData(rid);

        //If the passed row and the fetched computer data not null then construct the computer, otherwise return null.
        if (computerExtraData != null && row != null) {

            try {
                return new Computer(rid, row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]),
                        computerExtraData[0], computerExtraData[1], computerExtraData[2]);
            } catch (ArrayIndexOutOfBoundsException e) {
                //returns null if data is invalid.
                return null;
            }

        } else {
            //returns null if the passed data is null or computer data could not be retrieved.
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

            //Get the language of the resource.
            String[] row = dbManager.getFirstTupleByQuery("SELECT Language FROM ResourceLanguage, Language WHERE " +
                    rid + " = RID AND ResourceLanguage.LangID = Language.LangID");

            //If the row found is not null then return the language, otherwise return an empty string.
            if (row != null) {
                return row[0];
            } else {
                return "";
            }
        } catch(SQLException e) {
            //Return an empty string if exception. Most likely will be caused by connection failure or missing table.
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

            //Get the table data for the subtitle languages.
            String[][] table = dbManager.getTupleListByQuery("SELECT Subtitle_Language FROM DvdSubtitleLanguage, SubtitleLanguage WHERE " +
                    rid + " = RID AND DvdSubtitleLanguage.SubID = SubtitleLanguage.SubID");

            String[] subLanguages = new String[table.length];

            //For every row, add the subtitle language from the first column to the array of strings.
            for (int iCount = 0; iCount < table.length; iCount++) {
                subLanguages[iCount] = table[iCount][0];
            }

            return subLanguages;

        } catch(SQLException e) {

            //Return null if an exception has occurred.
            return null;

        }

    }


    /**
     * Gets the book data using a resource id.
     * @param rid Uniquely identifies the resource.
     * @return The row of book data.
     */
    private String[] getBookData(int rid) {

        try {
            //Create the row
            String[] tuple = dbManager.getFirstTupleByQuery("SELECT Author, Publisher, Genre, ISBN " +
                    "FROM Book WHERE RID = " + rid + ";");
            return tuple;
        } catch (SQLException e) {
            return null;
        }

    }

    /**
     * Gets the dvd data using a resource id.
     * @param rid Uniquely identifies the resource.
     * @return The row of dvd data.
     */
    private String[] getDvdData(int rid) {

        try {
            //Create the row
            String[] tuple = dbManager.getFirstTupleByQuery("SELECT Director, Runtime " +
                    "FROM Dvd WHERE RID = " + rid + ";");
            return tuple;
        } catch (SQLException e) {
            return null;
        }

    }

    /**
     * Gets the computer data using a resource id.
     * @param rid Uniquely identifies the resource.
     * @return The row of computer data.
     */
    private String[] getComputerData(int rid) {

        try {
            //Create the row
            String[] tuple = dbManager.getFirstTupleByQuery("SELECT Manufacturer, Model, Installed_OS " +
                    "FROM Computer WHERE RID = " + rid + ";");
            return tuple;
        } catch (SQLException e) {
            return null;
        }

    }

}
