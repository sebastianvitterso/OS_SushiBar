/**
 * This class implements the consumer part of the producer/consumer problem. One
 * waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {
    private WaitingArea waitingArea;
    private Customer currentCustomer;

    /**
     * Creates a new waitress.
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is created for this
     * instance.
     */
    @Override
    public synchronized void run() {
        while(waitingArea.getCurrentSize() > 0 || SushiBar.isOpen) {
            while (currentCustomer == null) {
                currentCustomer = waitingArea.fetch();
            }
            SushiBar.write(String.format("Waitress fetched customer %d", currentCustomer.getCustomerID()));
            try {
                wait(SushiBar.waitressWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCustomer.order(this);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SushiBar.write(String.format("Waitress let customer %d go", currentCustomer.getCustomerID()));
            currentCustomer = null;
        }
    }
}

