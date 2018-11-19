
public class Tester {
    public static void main(String[] args) {
        //Test for User
        User testUser = new User(92372, "parisskopelitis", "Paris", "Skopelitis", "07403576267", "27", "Ernald Place", "Uplands", "Swansea", "SA2 OHN");
        System.out.println(testUser.toString());
        System.out.println("\n");

        //Test for Staff
        Staff testStaff = new Staff(92372, "parisskopelitis", "Paris", "Skopelitis", "07403576267", "27", "Ernald Place", "Uplands", "Swansea", "SA2 OHN", "12-13-2016", 95568);
        System.out.println(testStaff.toString());
        System.out.println("\n");

        //Test for Book
        Book testBook = new Book(12322, "The Greatest Book", 2001, 2322, "Paris Skopelitis", "Penguin Books", "Horror", "2uffee", "English");
        System.out.println(testBook.toString());
        System.out.println("\n");

        //Test for Dvd
        String[] testSubLang = {"English", "French"};
        Dvd testDvd = new Dvd(42322, "The Greatest Movie", 2001, 2322, "Paris Skopelitis", 124, "English", testSubLang);
        System.out.println(testDvd.toString());
        System.out.println("\n");

        //Test for Computer
        Computer testComputer = new Computer(2342,"Laptop", 2017, 82272, "Dell", "XPS 9560", "Windows 10");
        System.out.println(testComputer.toString());
        System.out.println("\n");

    }


}
