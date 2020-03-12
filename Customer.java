import java.util.Random;

/**
 * This class implements a customer, which is used for holding data and update
 * the statistics
 */
public class Customer {
    private static Random random = new Random();
    private static int ID_COUNTER = 0;
    private final int id;

    public Customer() {
        id = ID_COUNTER++;
    }

    /**
     * Here you should implement the functionality for ordering food as described in
     * the assignment.
     */
    public synchronized void order() {
        SushiBar.write(Thread.currentThread().getName() +  String.format("Customer %d is now eating.", id));
        int servedOrders = random.nextInt(SushiBar.maxOrder);
        int takeAwayOrders = random.nextInt(SushiBar.maxOrder);
        SushiBar.servedOrders.add(servedOrders);
        SushiBar.takeawayOrders.add(takeAwayOrders);
        SushiBar.totalOrders.add(servedOrders + takeAwayOrders);
        try {
            wait(SushiBar.customerWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SushiBar.write(Thread.currentThread().getName() +  String.format("Customer %d is now leaving.", id));
        return;
    }

    public int getCustomerID() {
        return id;
    }


    // Add more methods as you see fit.
}
