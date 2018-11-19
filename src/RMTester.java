public class RMTester {

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");

        ResourceManager rm = new ResourceManager(dbManager);

        //Get all resources
        //Resource[] resources = rm.getResourceList();

        //Search resources
        //Resource[] resources = rm.searchResources("Title", "Kirk");

        //Add resource
        /*rm.addResource(new Book(0, "Grimms Fairy Tales", 1812, 1, "Jacob Grimm, Wilhelm Grimm",
                "Unknown", "Fairy Tale", "9788847227910", "German"));*/

        rm.addResource(new Dvd(0, "First Man", 2018, 1, "Damien Chazelle",
                142, "English", new String[] {"English", "German",
                "French", "Chinese", "Italian", "Japanese", "Korean", "Dutch"}));

        //Delete row (multiple calls are required to delete a resource!)
        /*dbManager.deleteTuple("Resource", new String[] {"Title"},
                new String[] {"'Grimms Fairy Tales2'"});*/

        /*try {
            System.out.println(dbManager.checkIfExist("Resource", new String[]{"Title"},
                    new String[]{"'Grimms Fairy Tales'"}));
        } catch(Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }*/

        //Filter by DVDs
        /*Resource[] resources = rm.getResourceList("SELECT * FROM Resource WHERE TID = 1");

        for (Resource r : resources) {
            System.out.println("____________________\n\n" + r.toString());
        }*/
    }

}
