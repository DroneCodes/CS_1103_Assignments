package Assignment5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ChatServer - A simple server for a multi-client chat application
 * This server accepts connections from multiple clients, assigns each a unique ID,
 * and broadcasts messages from one client to all other connected clients.
 */
public class ChatServer {
    // Port number for the server socket
    private static final int PORT = 8888;

    // Map to store all connected clients (ClientID -> ClientHandler)
    private static final Map<Integer, ClientHandler> clients = new ConcurrentHashMap<>();

    // Atomic counter for generating unique client IDs
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server started on port " + PORT);
            System.out.println("Waiting for clients to connect...");

            // Continuously accept new client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientIdCounter.getAndIncrement();

                System.out.println("New client connected: Client #" + clientId);

                // Create a new handler thread for this client
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId);
                clients.put(clientId, clientHandler);

                // Start the client handler thread
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts a message to all connected clients except the sender
     * @param message The message to broadcast
     * @param senderId The ID of the client sending the message
     */
    public static void broadcastMessage(String message, int senderId) {
        for (Map.Entry<Integer, ClientHandler> entry : clients.entrySet()) {
            // Don't send the message back to the sender
            if (entry.getKey() != senderId) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    /**
     * Removes a client from the clients map when they disconnect
     * @param clientId The ID of the client to remove
     */
    public static void removeClient(int clientId) {
        clients.remove(clientId);
        System.out.println("Client #" + clientId + " has disconnected.");
        broadcastMessage("User #" + clientId + " has left the chat.", clientId);
    }

    /**
     * ClientHandler class to manage individual client connections
     * Each client runs in a separate thread
     */
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final int clientId;
        private PrintWriter output;
        private BufferedReader input;
        private String username;

        public ClientHandler(Socket socket, int id) {
            this.clientSocket = socket;
            this.clientId = id;
            this.username = "User" + id; // Default username

            try {
                // Initialize input and output streams
                this.output = new PrintWriter(socket.getOutputStream(), true);
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.out.println("Error setting up streams for client #" + id + ": " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                // First message from a client might be their chosen username
                String firstMessage = input.readLine();
                if (firstMessage != null && firstMessage.startsWith("USERNAME:")) {
                    username = firstMessage.substring(9);
                    sendMessage("Welcome to the chat, " + username + "!");
                    broadcastMessage(username + " has joined the chat.", clientId);
                } else {
                    // If no username provided, use default and process the message
                    sendMessage("Welcome to the chat, " + username + "!");
                    broadcastMessage(username + " has joined the chat.", clientId);
                    if (firstMessage != null) {
                        broadcastMessage(username + ": " + firstMessage, clientId);
                    }
                }

                // Process messages from this client
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) {
                        break; // Client wants to disconnect
                    }

                    System.out.println("Message from " + username + ": " + message);
                    broadcastMessage(username + ": " + message, clientId);
                }
            } catch (IOException e) {
                System.out.println("Error handling client #" + clientId + ": " + e.getMessage());
            } finally {
                // Clean up when client disconnects
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
                removeClient(clientId);
            }
        }

        /**
         * Sends a message to this client
         * @param message The message to send
         */
        public void sendMessage(String message) {
            output.println(message);
        }
    }
}
