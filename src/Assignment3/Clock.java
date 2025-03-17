package Assignment3;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Clock class provides functionality to display the current time
 * and update it in the background using separate threads for updating
 * and displaying the time.
 */
public class Clock {
    /**
     * A flag to control the running state of the clock.
     * It is marked as volatile to ensure visibility across threads.
     */
    private volatile boolean running = true;

    /**
     * Holds the current time.
     */
    private LocalDateTime currentTime1, currentTime2, currentTime3;

    /**
     * Formatter to format the current time for display.
     * The pattern used is "HH:mm:ss dd-MM-yyyy".
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    /**
     * The TimeUpdater class is a Runnable that updates the current time
     * in a background thread at regular intervals.
     */
    private class TimeUpdater implements Runnable {
        /**
         * The run method contains the logic to update the current time
         * every 100 milliseconds.
         */
        @Override
        public void run() {
            try {
                while (running) {
                    currentTime1 = LocalDateTime.now();
                    currentTime2 = LocalDateTime.now(ZoneId.of("Europe/London"));
                    currentTime3 = LocalDateTime.now(ZoneId.of("Africa/Lagos"));
                    Thread.sleep(100); // Update every 100ms for precision
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Time updater thread interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * The TimeDisplay class is a Runnable that displays the current time
     * in a separate thread at regular intervals.
     */
    private class TimeDisplay implements Runnable {
        /**
         * The run method contains the logic to display the current time
         * every second.
         */
        @Override
        public void run() {
            try {
                while (running) {
                    if (currentTime1 != null || currentTime2 != null || currentTime3 != null) {
                        String formattedTime1 = currentTime1.format(formatter);
                        String formattedTime2 = currentTime2.format(formatter);
                        String formattedTime3 = currentTime3.format(formatter);
                        System.out.print("\rNew York:" + formattedTime1 + " London:" + formattedTime2 + " Lagos:" + formattedTime3);
                    }
                    Thread.sleep(1000); // Display update every second
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Display thread interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Starts the clock by creating and starting the time updater and display threads.
     * The time updater thread is given normal priority, while the display thread
     * is given maximum priority.
     */
    public void start() {
        // Create and configure the time updater thread (background)
        Thread timeUpdater = new Thread(new TimeUpdater(), "TimeUpdater");
        timeUpdater.setPriority(Thread.NORM_PRIORITY); // Normal priority for background updates

        // Create and configure the display thread
        Thread timeDisplay = new Thread(new TimeDisplay(), "TimeDisplay");
        timeDisplay.setPriority(Thread.MAX_PRIORITY); // Higher priority for display

        // Start both threads
        timeUpdater.start();
        timeDisplay.start();
    }

    /**
     * Stops the clock gracefully by setting the running flag to false.
     */
    public void stop() {
        running = false;
    }

    /**
     * The main method to start the clock and run it for 30 seconds.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Clock clock = new Clock();
        clock.start();

        // Run for 30 seconds then stop
        try {
            Thread.sleep(60000);
            clock.stop();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}
