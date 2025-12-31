package io.github.some_example_name;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket connectionToServer;          // The connection to the server
    private PrintWriter sendToServer;           // To send messages to server
    private BufferedReader receiveFromServer;   // To get messages from server
    private boolean areWeConnected = false;     // Are we connected to server?

    private String serverAddress = "localhost"; // Server's address (where to connect)
    private int portNumber = 12345;            // Same port as server uses
    private String serverIpAddress = "192.168.1.100"; // Actual IP to connect to


    private String lastMessageFromServer = "";
    private boolean doWeHaveNewMessage = false;


    public Client() {
        lastMessageFromServer = "";
        doWeHaveNewMessage = false;
        System.out.println("Made a new Client");
    }


    public boolean connectToServer(String serverIp, int port) {
        this.serverIpAddress = serverIp;
        this.portNumber = port;

        System.out.println("Trying to connect to server at: " + serverIpAddress + " port " + portNumber);

        try {
            // STEP 1: Try to connect to the server
            System.out.println("Trying to connect");
            connectionToServer = new Socket(serverIpAddress, portNumber);
            System.out.println("Server open");

            // STEP 2: Set up ways to talk to each other
            sendToServer = new PrintWriter(connectionToServer.getOutputStream(), true);

            // Make a way to RECEIVE messages from server
            receiveFromServer = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()));

            areWeConnected = true;
            System.out.println("Connected to server");

            // STEP 3: Start checking for messages in the background
            Thread messageChecker = new Thread(new Runnable() {
                public void run() {
                    System.out.println("Starting to check for messages from server");

                    try {
                        while (areWeConnected == true) {
                            String messageWeJustGot = receiveFromServer.readLine();

                            if (messageWeJustGot == null) {
                                System.out.println("Server disconnected");
                                areWeConnected = false;
                                break;
                            }

                            System.out.println("Got message from server: " + messageWeJustGot);
                            lastMessageFromServer = messageWeJustGot;
                            doWeHaveNewMessage = true;
                        }
                    } catch (Exception e) {
                        System.out.println("Problem checking messages: " + e.getMessage());
                        areWeConnected = false;
                    }
                    System.out.println("Stopped checking for messages");
                }
            });

            messageChecker.start();
            System.out.println("Client is ready");

            return true;

        } catch (Exception e) {
            System.out.println("Failed to connect to server: " + e.getMessage());
            areWeConnected = false;
            return false;
        }
    }

    public void sendMessage(String messageToSend) {
        if (areWeConnected == true && sendToServer != null) {
            sendToServer.println(messageToSend);
            System.out.println("Sent to server: " + messageToSend);
        } else {
            System.out.println("Can't send because not connected to server");
        }
    }

    public void sendChessMove(NetworkMessage moveMessage) {
        String messageString = moveMessage.toString();
        sendMessage(messageString);
    }

    // Check if we have a new message from server
    public String checkForNewMessage() {
        if (doWeHaveNewMessage == true) {
            doWeHaveNewMessage = false;
            System.out.println("Returning message from server: " + lastMessageFromServer);
            return lastMessageFromServer;
        } else {
            return null;
        }
    }

    public NetworkMessage getLastChessMove() {
        String messageString = checkForNewMessage();
        if (messageString != null) {
            // Convert string back to NetworkMessage
            NetworkMessage message = NetworkMessage.fromString(messageString);
            return message;
        }
        return null;
    }

    public void disconnect() {
        System.out.println("Disconnecting from server");

        areWeConnected = false;

        try {
            if (sendToServer != null) {
                sendToServer.close();
                System.out.println("Stopped sending to server");
            }

            if (receiveFromServer != null) {
                receiveFromServer.close();
                System.out.println("Stopped receiving from server");
            }

            if (connectionToServer != null) {
                connectionToServer.close();
                System.out.println("Closed connection to server");
            }

            System.out.println("Disconnected successfully");

        } catch (Exception e) {
            System.out.println("Problem disconnecting: " + e.getMessage());
        }
    }

    public boolean areWeConnected() {
        return areWeConnected;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }


    public void setServerAddress(String newAddress) {
        serverAddress = newAddress;
        System.out.println("Changed server address to: " + serverAddress);
    }

    public void setPortNumber(int newPort) {
        portNumber = newPort;
        System.out.println("Changed port to: " + portNumber);
    }

    public void setServerIpAddress(String newIp) {
        serverIpAddress = newIp;
        System.out.println("Changed server IP to: " + serverIpAddress);
    }

    public void clearMessages() {
        lastMessageFromServer = "";
        doWeHaveNewMessage = false;
        System.out.println("Cleared all messages from server");
    }

    public void testConnection() {
        System.out.println("Testing Connection");
        System.out.println("Trying to connect to: " + serverIpAddress + " port " + portNumber);

        boolean connected = connectToServer(serverIpAddress, portNumber);

        if (connected) {
            System.out.println("Connection test: SUCCESS");


            sendMessage("TEST;Hello Server!;0;0;0;0;null;null;null;null;null;12345;null");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {}

            String response = checkForNewMessage();
            if (response != null) {
                System.out.println("Got response: " + response);
            }
            disconnect();
        } else {
            System.out.println("Connection test: failed");
        }

        System.out.println("Test Complete");
    }
}
