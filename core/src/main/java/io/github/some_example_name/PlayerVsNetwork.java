package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class PlayerVsNetwork {
    private Board board;
    private boolean whiteTurn = true;
    private Piece selectedPiece;
    private boolean pieceSelected = false;

    private boolean isHost = false;
    private boolean isWhite = true;
    private Server gameServer;
    private Client gameClient;

    private String opponentName = "Opponent";
    private String playerName = "Player";

    private float lastSentFromX = -1;
    private float lastSentFromY = -1;
    private float lastSentToX = -1;
    private float lastSentToY = -1;

    private String statusMessage = "";
    private float statusMessageTime = 0;

    public PlayerVsNetwork(Board board, boolean isHost, String playerName) {
        this.board = board;
        this.isHost = isHost;
        this.playerName = playerName;

        if (isHost) {
            isWhite = true;
            gameServer = new Server();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    gameServer.startServer();
                    statusMessage = "Waiting for opponent to connect";
                }
            }).start();

            statusMessage = "Starting server";
        } else {
            isWhite = false;
            gameClient = new Client();
            statusMessage = "Ready to connect to host";
        }
    }

    public boolean connectToServer(String serverIpAddress, int portNumber) {
        if (!isHost && gameClient != null) {
            boolean connected = gameClient.connectToServer(serverIpAddress, portNumber);

            if (connected) {
                statusMessage = "Connected! Waiting for host to start...";
                sendJoinMessage();
                return true;
            } else {
                statusMessage = "Failed to connect. Check IP and port.";
                return false;
            }
        }
        return false;
    }

    public boolean click(float x, float y) {
        if (board.gameOver) {
            return false;
        }

        if (board.promotingPawn != null) {
            return false;
        }

        boolean isMyTurn = (isWhite && whiteTurn) || (!isWhite && !whiteTurn);

        if (!isMyTurn) {
            statusMessage = "Wait for opponent's move";
            statusMessageTime = 3;
            return false;
        }

        if (!pieceSelected) {
            for (Piece piece : board.getPieces()) {
                if (piece.getX() < 1000 &&
                    x >= piece.getX() && x <= piece.getX() + piece.getWidth() &&
                    y >= piece.getY() && y <= piece.getY() + piece.getHeight()) {

                    if (whiteTurn && piece.getColour() == PieceColour.WHITE) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        return false;
                    } else if (!whiteTurn && piece.getColour() == PieceColour.BLACK) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        return false;
                    } else {
                        statusMessage = "Can't select opponent's piece!";
                        statusMessageTime = 2;
                    }
                    break;
                }
            }
        } else {
            boolean moveSuccessful = board.tryMove(selectedPiece, x, y, whiteTurn);

            if (moveSuccessful) {
                sendMoveToOpponent(selectedPiece.getX(), selectedPiece.getY(), x, y);

                if (board.promotingPawn == null) {
                    whiteTurn = !whiteTurn;
                }

                pieceSelected = false;
                return true;
            } else {
                statusMessage = "Invalid move!";
                statusMessageTime = 2;
            }

            pieceSelected = false;
        }

        return false;
    }

    private void sendMoveToOpponent(float fromX, float fromY, float toX, float toY) {
        if (fromX == lastSentFromX && fromY == lastSentFromY &&
            toX == lastSentToX && toY == lastSentToY) {
            return;
        }

        lastSentFromX = fromX;
        lastSentFromY = fromY;
        lastSentToX = toX;
        lastSentToY = toY;

        NetworkMessage moveMessage = new NetworkMessage("MOVE", fromX, fromY, toX, toY);

        if (isHost && gameServer != null) {
            gameServer.sendChessMove(moveMessage);
            statusMessage = "Sent move to opponent";
            statusMessageTime = 1;
        } else if (!isHost && gameClient != null) {
            gameClient.sendChessMove(moveMessage);
            statusMessage = "Sent move to opponent";
            statusMessageTime = 1;
        }
    }

    public void update() {
        if (statusMessageTime > 0) {
            float deltaTime = Gdx.graphics.getDeltaTime();
            statusMessageTime -= deltaTime;
        }

        if (isHost && gameServer != null) {
            if (gameServer.isServerOn() && !gameServer.areWeConnected()) {
                statusMessage = "Waiting for opponent";
            } else if (gameServer.areWeConnected()) {
                // Process messages
                NetworkMessage receivedMessage = gameServer.getLastChessMove();
                if (receivedMessage != null) {
                    handleNetworkMessage(receivedMessage);
                }
            }
        } else if (!isHost && gameClient != null && gameClient.areWeConnected()) {
            NetworkMessage receivedMessage = gameClient.getLastChessMove();
            if (receivedMessage != null) {
                handleNetworkMessage(receivedMessage);
            }
        }
    }

    private void handleNetworkMessage(NetworkMessage message) {
        if (message.messageType.equals("MOVE")) {
            handleOpponentMove(message.fromX, message.fromY, message.toX, message.toY);
        } else if (message.messageType.equals("JOIN")) {
            opponentName = message.playerName;
            statusMessage = opponentName + " joined the game!";
            statusMessageTime = 3;
        } else if (message.messageType.equals("DISCONNECT")) {
            statusMessage = opponentName + " disconnected. You win!";
            statusMessageTime = 10;
            board.gameOver = true;
            board.gameResult = "Opponent disconnected. You win!";
        } else if (message.messageType.equals("CHAT")) {
            statusMessage = opponentName + ": " + message.chatMessage;
            statusMessageTime = 5;
        } else if (message.messageType.equals("PROMOTION")) {
            // Handle promotion if needed
        }
    }

    private void handleOpponentMove(float fromX, float fromY, float toX, float toY) {
        Piece opponentPiece = null;

        for (Piece piece : board.getPieces()) {
            float pieceX = piece.getX();
            float pieceY = piece.getY();

            if (Math.abs(pieceX - fromX) < 5 && Math.abs(pieceY - fromY) < 5 &&
                piece.getX() < 1000) {
                opponentPiece = piece;
                break;
            }
        }

        if (opponentPiece != null) {
            boolean moveSuccess = board.tryMove(opponentPiece, toX, toY, !whiteTurn);

            if (moveSuccess) {
                whiteTurn = !whiteTurn;
                statusMessage = "Opponent moved. Your turn";
                statusMessageTime = 2;
            } else {
                statusMessage = "Error applying opponent's move";
                statusMessageTime = 3;
            }
        } else {
            statusMessage = "Error: Piece not found";
            statusMessageTime = 3;
        }
    }

    public void sendJoinMessage() {
        NetworkMessage joinMessage = new NetworkMessage("JOIN", playerName);

        if (isHost && gameServer != null) {
            gameServer.sendChessMove(joinMessage);
        } else if (!isHost && gameClient != null) {
            gameClient.sendChessMove(joinMessage);
        }
    }

    public void sendChatMessage(String chatText) {
        NetworkMessage chatMessage = new NetworkMessage("CHAT");
        chatMessage.chatMessage = chatText;

        if (isHost && gameServer != null) {
            gameServer.sendChessMove(chatMessage);
        } else if (!isHost && gameClient != null) {
            gameClient.sendChessMove(chatMessage);
        }
    }

    public void sendPromotionChoice(int choice) {
        NetworkMessage promotionMessage = new NetworkMessage("PROMOTION");
        promotionMessage.pieceType = Integer.toString(choice);

        if (isHost && gameServer != null) {
            gameServer.sendChessMove(promotionMessage);
        } else if (!isHost && gameClient != null) {
            gameClient.sendChessMove(promotionMessage);
        }
    }

    public void disconnect() {
        NetworkMessage disconnectMessage = new NetworkMessage("DISCONNECT");

        if (isHost && gameServer != null) {
            gameServer.sendChessMove(disconnectMessage);
            gameServer.stopServer();
        } else if (!isHost && gameClient != null) {
            gameClient.sendChessMove(disconnectMessage);
            gameClient.disconnect();
        }

        statusMessage = "Disconnected";
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
        board.drawCapturedPieces(batch);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public boolean isConnected() {
        if (isHost && gameServer != null) {
            return gameServer.areWeConnected();
        } else if (!isHost && gameClient != null) {
            return gameClient.areWeConnected();
        }
        return false;
    }

    public boolean isHost() {
        return isHost;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
