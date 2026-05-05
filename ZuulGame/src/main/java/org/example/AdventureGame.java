package org.example;
import java.util.*;

class CommandWords {
    private static final String[] validCommands = { "go", "quit", "help", "grab", "drop" };
    public boolean isCommand(String aString) {
        for (String command : validCommands) {
            if (command.equals(aString)) return true;
        }
        return false;
    }

    public void showAll() {
        for (String command : validCommands) {
            System.out.print(command + " ");
        }
        System.out.println();
    }
}

class Command {
    private String commandWord;
    private String secondWord;

    public Command(String first, String second) {
        commandWord = first; secondWord = second;
    }

    public String getCommandWord() { return commandWord; }
    public String getSecondWord() { return secondWord; }
    public boolean isUnknown() { return (commandWord == null); }
    public boolean hasSecondWord() { return (secondWord != null); }
}

class Parser {
    private CommandWords commands;
    private Scanner reader;

    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");
        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) word2 = tokenizer.next();
        }

        if (commands.isCommand(word1)) {
            return new Command(word1, word2);
        } else {
            return new Command(null, word2);
        }
    }

    public void showCommands() { commands.showAll(); }
}

class Room {
    private String description;
    private HashMap<String, Room> exits = new HashMap<>();
    private ArrayList<String> items = new ArrayList<>();

    public Room(String description) { this.description = description; }

    public void setExit(String direction, Room neighbor) { exits.put(direction, neighbor); }

    public String getLongDescription() {
        return "Location: " + description + ".\n" + getExitString() + "\nItems: " + getItemsString();
    }

    private String getExitString() {
        return "Exits: " + String.join(" ", exits.keySet());
    }

    private String getItemsString() {
        return items.isEmpty() ? "None" : String.join(", ", items);
    }

    public Room getExit(String direction) { return exits.get(direction); }
    public void addItem(String item) { items.add(item); }
    public String removeItem() { return items.isEmpty() ? null : items.remove(0); }
}

public class AdventureGame {
    private Parser parser;
    private Room currentRoom;
    private ArrayList<String> inventory = new ArrayList<>();

    public AdventureGame() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room outside = new Room("outside near a cliff");
        Room cave = new Room("inside a dark, damp cave");

        outside.setExit("down", cave);
        cave.setExit("up", outside);
        cave.addItem("Treasure");

        currentRoom = outside;
    }

    public void play() {
        System.out.println("Welcome to the World of Zuul!");
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing!");
    }

    private boolean processCommand(Command command) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String word = command.getCommandWord();
        if (word.equals("help")) printHelp();
        else if (word.equals("go")) goRoom(command);
        else if (word.equals("grab")) grabItem();
        else if (word.equals("quit")) return true;

        return false;
    }

    private void printHelp() {
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private void grabItem() {
        String item = currentRoom.removeItem();
        if (item != null) {
            inventory.add(item);
            System.out.println("You picked up: " + item);
        } else System.out.println("Nothing here to grab.");
    }

    public static void main(String[] args) {
        new AdventureGame().play();
    }
}