package org.example;
class MItem {
    String t, c;
    int playTime;
    boolean gotIt;

    MItem(String t, int playTime) {
        this.t = t;
        this.playTime = playTime;
        this.c = " ";
        this.gotIt = false;
    }

    public void setComnt(String c) {
        this.c = c;
    }

    public void setGotIt(boolean gotIt) {
        this.gotIt = gotIt;
    }

    public String toString() {
        return (t + " | Time: " + playTime + " | GotIt: " + gotIt + " | Comment: " + c);
    }
}

class CD extends MItem {
    String a;
    int tr;

    CD(String t, int playTime, String a, int tr) {
        super(t, playTime);
        this.a = a;
        this.tr = tr;
    }

    public String toString() {
        return ("CD " + super.toString() + " | Artist " + a + " | Tracks " + tr);
    }
}

class Video extends MItem {
    String director;

    Video(String title, String director, int playingTime) {
        super(title, playingTime);
        this.director = director;
    }

    public String toString() {
        return "Video " + super.toString() + " | Director: " + director;
    }
}

class VGame extends MItem {
    String pform, g;

    VGame(String title, String pform, String g, int playingTime) {
        super(title, playingTime);
        this.pform = pform;
        this.g = g;
    }

    public String toString() {
        return "[Game] " + super.toString() + " | Platform: " + pform + " | Genre: " + g;
    }
}

class Database {
    MItem[] items = new MItem[50];
    int count = 0;

    void add(MItem m) {
        items[count] = m;
        count++;
    }

    void list() {
        System.out.println("--- DATABASE ---");
        for (int i = 0; i < count; i++) {
            System.out.println(items[i]);
        }
        System.out.println("---");
    }

    void searchTitle(String t) {
        System.out.println("Search Results for: " + t);
        for (int i = 0; i < count; i++) {
            if (items[i].t.toLowerCase().contains(t.toLowerCase()))
                System.out.println(items[i]);
        }
    }

    void remove(String t) {
        for (int i = 0; i < count; i++) {
            if (items[i].t.equalsIgnoreCase(t)) {
                System.out.println("Removed: " + items[i].t);
                items[i] = items[count - 1];
                count--;
                return;
            }
        }
    }

    int getCount() { return count; }

    void listCDs() {
        System.out.println("--- CDs ---");
        for (int i = 0; i < count; i++) {
            if (items[i] instanceof CD) {
                System.out.println(items[i]);
            }
        }
        System.out.println("---");
    }

    void listVideos() {
        System.out.println("--- Videos ---");
        for (int i = 0; i < count; i++) {
            if (items[i] instanceof Video) {
                System.out.println(items[i]);
            }
        }
        System.out.println("---");
    }

    void listGames() {
        System.out.println("---- Games ----");
        for (int i = 0; i < count; i++) {
            if (items[i] instanceof VGame) {
                System.out.println(items[i]);
            }
        }
        System.out.println("--------------");
    }
}

public class doME {
    public static void main(String[] args) {
        Database db = new Database();
        CD cd1 = new CD("Heart Stereo", 48, "A.Levine", 3);
        Video v1 = new Video("Interstellar", "C.Nolan", 109);
        db.add(cd1);
        db.add(v1);

        System.out.println("TASK 1: List Items");
        db.list();

        CD cd2 = new CD("Test Album", 30, "Unknown", 8);
        db.add(cd2);
        System.out.println("Before comment change:");
        db.list();
        cd2.setComnt("Added later");
        System.out.println("After comment change:");
        db.list();

        CD cd3 = new CD("Inherited CD", 50, "Some Band", 12);
        cd3.setComnt("Using inherited method!");
        cd3.setGotIt(true);
        db.add(cd3);
        System.out.println("TASK 3:");
        db.list();

        VGame g1 = new VGame("GTA V", "Switch", "Rockstar", 120);
        g1.setGotIt(true);
        g1.setComnt("Amazing!");
        db.add(g1);
        System.out.println("TASK 4: Added game");
        db.list();

        System.out.println("\nTASK 5 — Searching:");
        db.searchTitle("Inception");

        System.out.println("\nTASK 5 — Remove item:");
        db.remove("Voyage");
        db.list();

        System.out.println("\nTASK 5 — Count items: " + db.getCount());
        System.out.println("\nTASK 5 — List CDs only:");
        db.listCDs();
        System.out.println("\nTASK 5 — List Videos only:");
        db.listVideos();
        System.out.println("\nTASK 5 — List Games only:");
        db.listGames();
    }
}