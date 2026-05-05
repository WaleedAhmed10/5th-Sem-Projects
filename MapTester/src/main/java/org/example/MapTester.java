package org.example;
import java.util.HashMap;

public class MapTester {
    private HashMap<String, String> phoneBook;

    public MapTester() {
        phoneBook = new HashMap<>();
    }

    public void enterNumber(String name, String number) {
        phoneBook.put(name, number);
    }

    public String lookupNumber(String name) {
        return phoneBook.get(name);
    }

    public static void main(String[] args) {
        MapTester phoneBook = new MapTester();
        phoneBook.enterNumber("Waleed", "123-456-7890");
        phoneBook.enterNumber("Hasan", "987-654-3210");
        phoneBook.enterNumber("Amir", "111-333-7777");
        phoneBook.enterNumber("Sheikh", "555-555-5555");

        System.out.println("Waleed's number: " + phoneBook.lookupNumber("Waleed"));
        System.out.println("Hasan's number: " + phoneBook.lookupNumber("Hasan"));
        System.out.println("Amir's number: " + phoneBook.lookupNumber("Amir"));
        System.out.println("Sheikh's number: " + phoneBook.lookupNumber("Sheikh"));
    }
}