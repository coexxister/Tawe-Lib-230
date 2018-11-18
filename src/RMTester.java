public class RMTester {

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");

        ResourceManager rm = new ResourceManager(dbManager);

        //Get all resources
        //Resource[] resources = rm.getResourceList();

        //Filter by DVDs
        //Resource[] resources = rm.getResourceList("SELECT * FROM Resource WHERE TID = 2");

        //Search resources
        Resource[] resources = rm.searchResources("Title", "Ring");

        for (Resource r : resources) {
            System.out.println("____________________\n\n" + r.toString());
        }

    }

}
