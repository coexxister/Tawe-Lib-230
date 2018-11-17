public class RMTester {

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");

        ResourceManager rm = new ResourceManager(dbManager);

        rm.getResourceList();

    }

}
