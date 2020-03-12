import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {
    private Queue<Customer> customers;
    private int size;
    /**
     * Creates a new waiting area.
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        customers = new ArrayDeque<>();
        this.size = size;
    }

    /**
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized boolean enter(Customer customer) { //throws Exception {
        if(customers.size() >= size) {
            // throw new Exception("WaitingArea is full!");
            return false;
        } else {
            customers.add(customer);
            return true;
        }
    }

    /**
     * Peeks at the next customer in line.
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        return customers.peek();
    }

    /**
     * Pops and returns the next customer in line.
     * @return The customer that is first in line.
     */
    public synchronized Customer fetch() {
        return customers.poll();
    }

    /**
     * @return Amount of customers in waiting area.
     */
    public synchronized int getCurrentSize() {
        return customers.size();
    }
    // Add more methods as you see fit
}
