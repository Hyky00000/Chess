package io.github.some_example_name;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket myServerSocket;    // The thing that listens for connections
    private Socket clientSocket;            // The connection to the other player
    private PrintWriter sendToClient;       // To send messages to other player
    private BufferedReader receiveFromClient; // To get messages from other player
    private boolean isServerRunning = false; // Is server on or off?
    private boolean isClientConnected = false; // Is someone connected?
    private int portNumber = 12345;         // The "door number" to connect to

    // These store messages we get
    private String lastMessageWeGot = "";
    private boolean weHaveNewMessage = false;

    // Constructor - runs when we create a new Server
    public Server() {
        // Just set up basic things
        lastMessageWeGot = "";
        weHaveNewMessage = false;
        System.out.println("Made a new Server");
    }

    public void startServer() {
        if (isServerRunning == true) {
            System.out.println("Server is already running");
            return;
        }

        isServerRunning = true;
        System.out.println("Trying to start server");

        try {
            // STEP 1: Open the door for connections
            System.out.println("Opening port number " + portNumber);
            myServerSocket = new ServerSocket(portNumber);
            System.out.println("Port number is opened and waiting");

            // STEP 2: Wait for someone to connect
            System.out.println("Waiting");
            clientSocket = myServerSocket.accept();
            System.out.println("Someone connected");

            // STEP 3: Set up ways to talk to each other
            sendToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            // Make a way to RECEIVE messages
            receiveFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            isClientConnected = true;
            System.out.println("Ready to play chess");

            // STEP 4: Start checking for messages in the background
            // We need to check for messages while the game is running
            // new thread allows for 2 things to run at thesame time
            Thread messageChecker = new Thread(new Runnable() {
                public void run() {
                    System.out.println("Starting to check for messages");

                    try {
                        // Keep checking forever (or until disconnected)
                        while (isClientConnected == true) {
                            // This line WAITS for a message
                            String messageWeJustGot = receiveFromClient.readLine();

                            if (messageWeJustGot == null) {
                                System.out.println("Client isconnected");
                                isClientConnected = false;
                                break;
                            }

                            System.out.println("Got message: " + messageWeJustGot);
                            lastMessageWeGot = messageWeJustGot;
                            weHaveNewMessage = true;
                        }
                    } catch (Exception e) {
                        System.out.println("Problem checking messages: " + e.getMessage());
                        isClientConnected = false;
                    }
                    System.out.println("Stopped checking for messages");
                }
            });

            // Actually start checking for messages
            messageChecker.start();
            System.out.println("Server is completely ready!");

        } catch (Exception e) {
            System.out.println("error starting server: " + e.getMessage());
            isServerRunning = false;
            isClientConnected = false;
        }
    }

    // Send a simple text message to the other player
    public void sendMessage(String messageToSend) {
        if (isClientConnected == true && sendToClient != null) {
            sendToClient.println(messageToSend);
            System.out.println("Sent: " + messageToSend);
        } else {
            System.out.println("Can't send because not connected to client");
        }
    }

    // Send a chess move message using our NetworkMessage class
    public void sendChessMove(NetworkMessage moveMessage) {
        String messageString = moveMessage.toString();
        sendMessage(messageString);
    }

    public String checkForNewMessage() {
        if (weHaveNewMessage == true) {
            weHaveNewMessage = false;
            System.out.println("Returning message: " + lastMessageWeGot);
            return lastMessageWeGot;
        } else {
            return null;
        }
    }

    // Get the last message as a NetworkMessage object
    public NetworkMessage getLastChessMove() {
        String messageString = checkForNewMessage();
        if (messageString != null) {
            // Convert string back to NetworkMessage
            NetworkMessage message = NetworkMessage.fromString(messageString);
            return message;
        }
        return null;
    }


    public void stopServer() {
        System.out.println("Stopping server");

        isServerRunning = false;
        isClientConnected = false;

        try {
            if (sendToClient != null) {
                sendToClient.close();
                System.out.println("Stopped sending");
            }

            if (receiveFromClient != null) {
                receiveFromClient.close();
                System.out.println("Stopped receiving");
            }

            if (clientSocket != null) {
                clientSocket.close();
                System.out.println("Closed the connection");
            }

            if (myServerSocket != null) {
                myServerSocket.close();
                System.out.println("Closed server door");
            }

            System.out.println("Server stopped");

        } catch (Exception e) {
            System.out.println("Problem stopping: " + e.getMessage());
        }
    }

    public boolean areWeConnected() {
        return isClientConnected;
    }

    public boolean isServerOn() {
        return isServerRunning;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int newPort) {
        portNumber = newPort;
        System.out.println("Changed port to: " + portNumber);
    }

    public void clearMessages() {
        lastMessageWeGot = "";
        weHaveNewMessage = false;
        System.out.println("Cleared all messages");
    }
}
