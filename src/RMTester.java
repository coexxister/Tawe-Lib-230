public class RMTester {

    public static void main(String[] args) {

        DatabaseManager dbManager = new DatabaseManager("TaweLibDB.db");

        ResourceManager rm = new ResourceManager(dbManager);

        Resource[] resources = rm.getResourceList();

        for (Resource r : resources) {
            System.out.println("____________________\n\n" + r.toString());
        }

    }

}
