package Assignment3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock {
    private volatile boolean running = true;
    private LocalDateTime currentTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    /**
     * Updates the current time in a background thread
     */
    private class TimeUpdater implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    currentTime = LocalDateTime.now();
                    Thread.sleep(100); // Update every 100ms for precision
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Time updater thread interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the current time in a separate thread
     */
    private class TimeDisplay implements Runnable {
        @Override
        public void run() {
            try {
                while (running) {
                    if (currentTime != null) {
                        String formattedTime = currentTime.format(formatter);
                        System.out.print("\r" + formattedTime); // \r for carriage return to update in place
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
     * Starts the clock with appropriate thread priorities
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
     * Stops the clock gracefully
     */
    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        Clock clock = new Clock();
        clock.start();

        // Run for 30 seconds then stop
        try {
            Thread.sleep(30000);
            clock.stop();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}
