import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SushiBar {
    // SushiBar settings.
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 7;
    private static int duration = 5;
    public static int maxOrder = 15;
    public static int waitressWait = 60; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 2500; // Used to calculate the time the customer spends eating
    public static int doorWait = 120; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    // Creating log file.
    private static File log;
    private static String path = "./";

    // Variables related to statistics.
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;

    public static void main(String[] args) {
        log = new File(path + "log.txt");

        // Initializing shared variables for counting number of orders.
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);
        Door door = new Door(waitingArea);
        ArrayList<Thread> waitressThreads = new ArrayList<>();
        for (int i = 0; i < waitressCount; i++) {
            waitressThreads.add(new Thread(new Waitress(waitingArea), "WaitressThread"+i));
        }
        Thread doorThread = new Thread(door, "DoorThread");
        Clock clock = new Clock(duration);
        doorThread.start();
        waitressThreads.forEach((wt) -> wt.start());

        for (Thread waitressThread : waitressThreads) {
            try {
                waitressThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            doorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SushiBar.write("Total number of orders: " + totalOrders.get());
        SushiBar.write("Orders eaten here: " + servedOrders.get());
        SushiBar.write("Orders taken out: " + takeawayOrders.get());
    }

    /**
     *  Writes actions in the log file and console.
     */
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
