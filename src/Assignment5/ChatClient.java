package Assignment5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * ChatClient - A simple client for connecting to the chat server
 * This client connects to the server, sends messages from the user,
 * and displays messages received from other clients.
 */
public class ChatClient {
    // Default server address and port
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8888;

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    private String username;

    public ChatClient() {
        this.username = "Anonymous"; // Default username
    }

    /**
     * Connects to the chat server
     * @return true if connection is successful, false otherwise
     */
    public boolean connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sets the username and sends it to the server
     * @param username The chosen username
     */
    public void setUsername(String username) {
        this.username = username;
        output.println("USERNAME:" + username);
    }

    /**
     * Sends a message to the server
     * @param message The message to send
     */
    public void sendMessage(String message) {
        output.println(message);
    }

    /**
     * Starts a thread to listen for incoming messages from the server
     */
    public void startMessageListener() {
        new Thread(() -> {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error receiving message: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Closes the connection to the server
     */
    public void disconnect() {
        try {
            if (output != null) {
                output.println("/quit");
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error disconnecting: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();

        // Display welcome message and instructions
        System.out.println("==================================");
        System.out.println("       JAVA CHAT APPLICATION      ");
        System.out.println("==================================");
        System.out.println("Connecting to server...");

        if (!client.connectToServer()) {
            System.out.println("Failed to connect to server. Exiting...");
            return;
        }

        System.out.println("Connected to server successfully!");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        if (!username.isEmpty()) {
            client.setUsername(username);
        }

        // Start listening for incoming messages
        client.startMessageListener();

        System.out.println("\n==================================");
        System.out.println("You can now start chatting!");
        System.out.println("Type '/quit' to exit the chat.");
        System.out.println("==================================\n");

        // Main message loop
        String message;
        while (true) {
            message = scanner.nextLine();
            if (message.equalsIgnoreCase("/quit")) {
                break;
            }
            client.sendMessage(message);
        }

        // Disconnect from the server
        client.disconnect();
        scanner.close();
        System.out.println("Disconnected from server. Goodbye!");
    }
}