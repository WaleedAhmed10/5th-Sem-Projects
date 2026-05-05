package org.example;
import java.util.Scanner;

public class ClockApp {
    private static final int MAX_HOUR = 23;
    private static final int MAX_MINUTE = 59;
    private static final int MIN_VALUE = 0;

    private int hour = 0;
    private int minute = 0;
    private boolean is24Hour = true;
    private final Scanner scanner;

    public ClockApp() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        ClockApp clock = new ClockApp();
        clock.run();
    }

    private void run() {
        System.out.println("=== DIGITAL CLOCK ===");

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            if (!processChoice(choice)) {
                break;
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("1. Show time");
        System.out.println("2. Tick (advance 1 minute)");
        System.out.println("3. Set time");
        System.out.println("4. Reset to midnight");
        System.out.println("5. Toggle 12h/24h format");
        System.out.println("6. Exit");
        System.out.print("Your choice: ");
    }

    private boolean processChoice(String choice) {
        switch (choice) {
            case "1":
                showTime();
                return true;
            case "2":
                tick();
                System.out.println("✓ Time advanced by 1 minute");
                showTime();
                return true;
            case "3":
                setTime();
                return true;
            case "4":
                reset();
                System.out.println("✓ Clock reset to midnight");
                showTime();
                return true;
            case "5":
                toggleFormat();
                return true;
            case "6":
                return false;
            default:
                System.out.println("❌ Invalid choice! Please enter 1-6.");
                return true;
        }
    }

    private void showTime() {
        String timeString = formatTime();
        System.out.println("\n🕐 Current time: " + timeString);
    }

    private String formatTime() {
        if (is24Hour) {
            return String.format("%02d:%02d", hour, minute);
        } else {
            int displayHour = convertTo12HourFormat();
            String period = getPeriod();
            return String.format("%02d:%02d %s", displayHour, minute, period);
        }
    }

    private int convertTo12HourFormat() {
        int displayHour = hour % 12;
        return displayHour == 0 ? 12 : displayHour;
    }

    private String getPeriod() {
        return (hour < 12) ? "AM" : "PM";
    }

    private void tick() {
        minute++;
        if (minute > MAX_MINUTE) {
            minute = MIN_VALUE;
            hour = (hour + 1) % 24;
        }
    }

    private void setTime() {
        System.out.println("\n--- Set New Time ---");

        Integer newHour = getValidatedInput("Hour (0-23): ", MIN_VALUE, MAX_HOUR);
        if (newHour == null) return;

        Integer newMinute = getValidatedInput("Minute (0-59): ", MIN_VALUE, MAX_MINUTE);
        if (newMinute == null) return;

        hour = newHour;
        minute = newMinute;
        System.out.println("✓ Time set successfully!");
        showTime();
    }

    private Integer getValidatedInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please try again.");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("❌ Value must be between %d and %d. Please try again.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }

    private void reset() {
        hour = MIN_VALUE;
        minute = MIN_VALUE;
    }

    private void toggleFormat() {
        is24Hour = !is24Hour;
        String newFormat = is24Hour ? "24-hour" : "12-hour";
        System.out.printf("✓ Switched to %s format\n", newFormat);
        showTime();
    }
}