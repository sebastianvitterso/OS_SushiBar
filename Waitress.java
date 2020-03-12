/**
 * This class implements the consumer part of the producer/consumer problem. One
 * waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {
    private WaitingArea waitingArea;
    private Customer currentCustomer;

    /**
     * Creates a new waitress.
     * 
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
    public  void run() {
        while (waitingArea.getCurrentSize() > 0 || SushiBar.isOpen) {
            synchronized(waitingArea) {
                while (waitingArea.isEmpty()) {
                    try {
                        waitingArea.wait();
                    } catch (InterruptedException e) { 
                        e.printStackTrace();
                    }
                }
            }
            currentCustomer = waitingArea.fetch();
            if(currentCustomer == null) continue;
            
            SushiBar.write(Thread.currentThread().getName() +  String.format(": Waitress fetched customer %d", currentCustomer.getCustomerID()));
            try {
                wait(SushiBar.waitressWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentCustomer.order();
            SushiBar.write(Thread.currentThread().getName() +  String.format(": Waitress let customer %d go", currentCustomer.getCustomerID()));
            currentCustomer = null;
        }
    }
}

