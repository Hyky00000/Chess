package io.github.some_example_name;

public class NetworkMessage {
    public String messageType; // can be a move or join or disconnect or chat
    public float fromX;
    public float fromY;
    public float toX;
    public float toY;
    public String pieceType;
    public String colour;
    public String playerName;
    public String gameResult;
    public String chatMessage;
    public int portNumber;
    public String ipAddress;


    public NetworkMessage(String messageType) {
        this.messageType = messageType;
    }

    public NetworkMessage(String messageType, float fromX, float fromY, float toX, float toY) {
        this.messageType = messageType;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public NetworkMessage(String messageType, String playerName) {
        this.messageType = messageType;
        this.playerName = playerName;
    }

    // Convert to string for sending
    public String toString() {
        String result = "";

        result = result + messageType + ";";
        result = result + Float.toString(fromX) + ";";
        result = result + Float.toString(fromY) + ";";
        result = result + Float.toString(toX) + ";";
        result = result + Float.toString(toY) + ";";

        if (pieceType != null) {
            result = result + pieceType + ";";
        } else {
            result = result + "null" + ";";
        }

        if (colour != null) {
            result = result + colour + ";";
        } else {
            result = result + "null" + ";";
        }

        if (playerName != null) {
            result = result + playerName + ";";
        } else {
            result = result + "null" + ";";
        }

        if (gameResult != null) {
            result = result + gameResult + ";";
        } else {
            result = result + "null" + ";";
        }

        if (chatMessage != null) {
            result = result + chatMessage + ";";
        } else {
            result = result + "null" + ";";
        }

        result = result + Integer.toString(portNumber) + ";";

        if (ipAddress != null) {
            result = result + ipAddress;
        } else {
            result = result + "null";
        }

        return result;
    }

    // Convert from string after receiving
    public static NetworkMessage fromString(String data) {
        NetworkMessage message = new NetworkMessage("");

        String[] parts = data.split(";");

        if (parts.length > 0) {
            message.messageType = parts[0];
        }

        if (parts.length > 1) {
            message.fromX = Float.parseFloat(parts[1]);
        }

        if (parts.length > 2) {
            message.fromY = Float.parseFloat(parts[2]);
        }

        if (parts.length > 3) {
            message.toX = Float.parseFloat(parts[3]);
        }

        if (parts.length > 4) {
            message.toY = Float.parseFloat(parts[4]);
        }

        if (parts.length > 5 && !parts[5].equals("null")) {
            message.pieceType = parts[5];
        }

        if (parts.length > 6 && !parts[6].equals("null")) {
            message.colour = parts[6];
        }

        if (parts.length > 7 && !parts[7].equals("null")) {
            message.playerName = parts[7];
        }

        if (parts.length > 8 && !parts[8].equals("null")) {
            message.gameResult = parts[8];
        }

        if (parts.length > 9 && !parts[9].equals("null")) {
            message.chatMessage = parts[9];
        }

        if (parts.length > 10 && !parts[10].equals("")) {
            message.portNumber = Integer.parseInt(parts[10]);
        }

        if (parts.length > 11 && !parts[11].equals("null")) {
            message.ipAddress = parts[11];
        }

        return message;
    }
}
