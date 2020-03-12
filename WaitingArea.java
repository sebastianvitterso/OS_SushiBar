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
    public synchronized void enter(Customer customer) {
        customers.add(customer);
        notifyAll();
    }

    public boolean isFull() {
        return customers.size() >= size;
    }

    public boolean isEmpty() {
        return customers.size() == 0;
    }

    /**
     * Pops and returns the next customer in line.
     * @return The customer that is first in line.
     */
    public synchronized Customer fetch() {
        Customer customer = customers.poll();
        notifyAll();
        return customer;
    }

    /**
     * @return Amount of customers in waiting area.
     */
    public synchronized int getCurrentSize() {
        return customers.size();
    }
}
