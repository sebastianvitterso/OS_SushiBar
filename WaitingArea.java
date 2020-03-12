import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {
    private Queue<Customer> customers;
    private int size;
    private Door door;
    /**
     * Creates a new waiting area.
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        customers = new ArrayDeque<>();
        this.size = size;
        door = null;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    /**
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) { //throws Exception {
        customers.add(customer);
        notifyAll();
    }

    public synchronized boolean isFull() {
        return customers.size() >= size;
    }

    public synchronized boolean isEmpty() {
        return customers.size() == 0;
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
    // Add more methods as you see fit
}
