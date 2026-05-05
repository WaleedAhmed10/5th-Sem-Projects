package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TechSupport {

    record Tip(String summary, String detail) {}

    record ConversationTurn(LocalDateTime timestamp, String command, String response) {
        private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

        @Override
        public String toString() {
            return String.format("[%s] > %s%n[%s]   %s", timestamp.format(FMT), command, timestamp.format(FMT), response);
        }
    }

    static class KnowledgeBase {
        private final Map<String, List<Tip>> topics = new LinkedHashMap<>();

        void addTip(String topic, String summary, String detail) {
            topics.computeIfAbsent(topic.toLowerCase(), k -> new ArrayList<>())
                    .add(new Tip(summary, detail));
        }

        Set<String> topics() {
            return topics.keySet();
        }

        List<Tip> tipsFor(String topic) {
            return topics.getOrDefault(topic.toLowerCase(), List.of());
        }

        boolean hasTopic(String topic) {
            return topics.containsKey(topic.toLowerCase());
        }
    }

    private static final String DIVIDER = "─".repeat(46);

    private final KnowledgeBase        kb      = buildKnowledgeBase();
    private final List<ConversationTurn> history = new ArrayList<>();
    private       String               activeTopic = null;

    private static KnowledgeBase buildKnowledgeBase() {
        KnowledgeBase k = new KnowledgeBase();

        k.addTip("password", "Reset via login page",
                "Go to the login page, click 'Forgot Password', and follow the email link.");
        k.addTip("password", "Password requirements",
                "Passwords must be at least 8 characters with letters, numbers, and symbols.");
        k.addTip("password", "Account locked out",
                "After 5 failed attempts the account locks for 15 minutes. Wait, then retry.");

        k.addTip("internet", "Check Wi-Fi connection",
                "Ensure your device shows a Wi-Fi or Ethernet connection in system settings.");
        k.addTip("internet", "Restart your router",
                "Unplug the router for 30 seconds, plug it back in, and wait 60 seconds.");
        k.addTip("internet", "Run a speed test",
                "Visit speedtest.net to check whether your speeds match your plan.");

        k.addTip("slow", "Close background apps",
                "Close unused applications and browser tabs to free up RAM and CPU.");
        k.addTip("slow", "Check resource usage",
                "Open Task Manager (Windows) or Activity Monitor (Mac) and look for high CPU/RAM processes.");
        k.addTip("slow", "Restart your device",
                "A fresh restart clears memory leaks and applies pending updates.");

        k.addTip("login", "Verify credentials",
                "Double-check Caps Lock is off and that you are using the correct username/email.");
        k.addTip("login", "Account recovery",
                "Use 'Forgot Password' or contact support with your registered email to recover access.");

        k.addTip("error", "Note the exact message",
                "Write down or screenshot the full error text and any error codes shown.");
        k.addTip("error", "Clear browser cache",
                "In your browser settings clear cookies and cached files, then reload the page.");
        k.addTip("error", "Try a different browser",
                "Test in Chrome, Firefox, or Edge to rule out a browser-specific issue.");

        k.addTip("update", "Update the app",
                "Open the app, go to Settings → About, and select 'Check for Updates'.");
        k.addTip("update", "Update the OS",
                "Run Windows Update or macOS Software Update to install the latest system patches.");
        k.addTip("update", "Free up disk space",
                "Updates need space. Delete unused files or empty the Recycle Bin first.");

        k.addTip("crash", "Record the steps",
                "Note exactly what you were doing before the crash so support can reproduce it.");
        k.addTip("crash", "Check crash logs",
                "Look in Event Viewer (Windows) or Console (Mac) for error entries around the crash time.");
        k.addTip("crash", "Reinstall the application",
                "Uninstall completely, reboot, then reinstall the latest version from the official source.");

        k.addTip("wifi", "Reconnect from scratch",
                "Forget the network on your device, then reconnect and re-enter the password.");
        k.addTip("wifi", "Check router band",
                "Try switching between the 2.4 GHz and 5 GHz bands in your Wi-Fi settings.");
        k.addTip("wifi", "Update router firmware",
                "Log into your router admin panel and check for firmware updates.");

        return k;
    }

    private void handleCommand(String raw) {
        String[] parts = raw.trim().split("\\s+", 2);
        String   cmd   = parts[0].toLowerCase();
        String   arg   = parts.length > 1 ? parts[1].toLowerCase().trim() : "";

        switch (cmd) {
            case "tips"    -> cmdTips();
            case "topic"   -> cmdTopic(arg);
            case "history" -> cmdHistory();
            case "clear"   -> cmdClear();
            case "back"    -> cmdBack();
            case "help"    -> cmdHelp();
            default        -> {
                if (activeTopic != null && raw.matches("\\d+")) {
                    cmdSelectTip(Integer.parseInt(raw));
                } else {
                    print("Unknown command. Type 'help' for available commands.");
                    record(raw, "unknown command");
                }
            }
        }
    }

    private void cmdTips() {
        print("\nAvailable topics:");
        int i = 1;
        for (String t : kb.topics()) {
            System.out.printf("  %-2d  %s%n", i++, t);
        }
        System.out.println("\nUse:  topic <name>   e.g.  topic wifi\n");
        record("tips", "listed all topics");
    }

    private void cmdTopic(String name) {
        if (name.isBlank()) {
            print("Usage:  topic <name>   (run 'tips' to see available topics)");
            return;
        }
        if (!kb.hasTopic(name)) {
            print("Topic '" + name + "' not found. Type 'tips' to see all topics.");
            record("topic " + name, "not found");
            return;
        }
        activeTopic = name;
        printTopicMenu(name);
        record("topic " + name, "opened topic");
    }

    private void printTopicMenu(String name) {
        List<Tip> tips = kb.tipsFor(name);
        System.out.println("\n" + DIVIDER);
        System.out.println("  Topic: " + name.toUpperCase());
        System.out.println(DIVIDER);
        for (int i = 0; i < tips.size(); i++) {
            System.out.printf("  %-2d  %s%n", i + 1, tips.get(i).summary());
        }
        System.out.println(DIVIDER);
        System.out.println("  Enter a number for details, or 'back' to return.\n");
    }

    private void cmdSelectTip(int number) {
        List<Tip> tips = kb.tipsFor(activeTopic);
        if (number < 1 || number > tips.size()) {
            print("Please enter a number between 1 and " + tips.size() + ".");
            return;
        }
        Tip tip = tips.get(number - 1);
        System.out.println("\n" + DIVIDER);
        System.out.println("  " + tip.summary());
        System.out.println(DIVIDER);
        System.out.println("  " + tip.detail());
        System.out.println(DIVIDER + "\n");
        record(activeTopic + " > " + number, tip.detail());
    }

    private void cmdBack() {
        if (activeTopic == null) {
            print("You are already at the top level. Type 'tips' to list topics.");
        } else {
            print("Left topic '" + activeTopic + "'. Type 'tips' to choose another.");
            record("back", "left topic " + activeTopic);
            activeTopic = null;
        }
    }

    private void cmdHistory() {
        if (history.isEmpty()) {
            print("No history yet.");
            return;
        }
        System.out.println("\n─── Session History (" + history.size() + " entries) ───");
        history.forEach(t -> System.out.println(t + "\n"));
        System.out.println(DIVIDER + "\n");
    }

    private void cmdClear() {
        history.clear();
        activeTopic = null;
        print("Session cleared.");
    }

    private void cmdHelp() {
        System.out.println("""

                Commands
                ────────────────────────────────────────────
                  tips              List all support topics
                  topic <name>      Open a topic
                  <number>          Select a tip from the open topic
                  back              Return from the current topic
                  history           Show session history
                  clear             Clear session and return to top
                  exit              Quit
                ────────────────────────────────────────────
                """);
        record("help", "displayed help");
    }

    private static void print(String msg) {
        System.out.println("  " + msg);
    }

    private void record(String command, String response) {
        history.add(new ConversationTurn(LocalDateTime.now(), command, response));
    }

    public static void main(String[] args) {
        TechSupport app     = new TechSupport();
        Scanner     scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║          TechSupport Assistant               ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("  Type 'help' to get started.\n");

        while (true) {
            System.out.print(app.activeTopic != null
                    ? "[" + app.activeTopic + "] > "
                    : "> ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("  Goodbye!");
                scanner.close();
                return;
            }

            if (!input.isEmpty()) {
                app.handleCommand(input);
            }
        }
    }
}