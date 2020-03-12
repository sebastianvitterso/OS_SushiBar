
/**
 * This class implements the Door component of the sushi bar assignment The Door
 * corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {
    private WaitingArea waitingArea;

    /**
     * Creates a new Door. Make sure to save the
     * 
     * @param waitingArea The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    public WaitingArea getWaitingArea() {
        return waitingArea;
    }


    /**
     * This method will run when the door thread is created (and started). The
     * method creates customers at random intervals and tries to put them in the
     * waiting area.
     */
    @Override
    public synchronized void run() {
        while (SushiBar.isOpen) {
            try {
                wait(SushiBar.doorWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Customer customer = new Customer();
            if(waitingArea.enter(customer)) {
                SushiBar.write(String.format("Door added customer %d to waitingarea", customer.getCustomerID()));
            }

        }

        SushiBar.write("*** DOOR IS NOW CLOSED ***");
    }

    // Add more methods as you see fit
}
