package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class PlayerVsComputer {
    private Board board;
    private boolean whiteTurn = true;
    private Piece selectedPiece;
    private boolean pieceSelected = false;
    private boolean playerIsWhite;
    private int aiDifficulty; // 1 is easy 2 iss medium 3 is hard
    private Random random = new Random();

    public PlayerVsComputer(Board board, boolean playerIsWhite, int aiDifficulty) {
        this.board = board;
        this.playerIsWhite = playerIsWhite;
        this.aiDifficulty = aiDifficulty;
    }

    public void click(float x, float y) {
        if (board.gameOver) return;

        // Ignore thee click if computers turn s
        if (playerIsWhite && !whiteTurn) {
            return;
        }
        else if (!playerIsWhite && whiteTurn){
            makeAIMove();
            return;
        }

        if (!pieceSelected) {
            for (Piece piece : board.getPieces()) {
                if (piece.getX() < 1000 && x >= piece.getX() && x <= piece.getX() + piece.getWidth() &&
                    y >= piece.getY() && y <= piece.getY() + piece.getHeight()) {

                    if (whiteTurn && piece.getColour() == PieceColour.WHITE) {
                        selectedPiece = piece;
                        pieceSelected = true;
                    } else if (!whiteTurn && piece.getColour() == PieceColour.BLACK) {
                        selectedPiece = piece;
                        pieceSelected = true;
                    }
                    break;
                }
            }
        } else {
            // move the selected piece
            boolean moveSuccessful = board.tryMove(selectedPiece, x, y, whiteTurn);
            if (moveSuccessful) {
                whiteTurn = !whiteTurn;
                // If its computers turn then it makes a move
                if (!board.gameOver && ((playerIsWhite && !whiteTurn) || (!playerIsWhite && whiteTurn))) {
                    makeAIMove();
                    System.out.println("testing ai move");
                }
            }
            pieceSelected = false;
        }
    }

    // AI makes a move based on difficulty level
    private void makeAIMove() {
        PieceColour aiColour;
        if (playerIsWhite) {
            aiColour = PieceColour.BLACK;
        } else {
            aiColour = PieceColour.WHITE;
        }

        java.util.ArrayList<Board.Move> legalMoves = board.getAllLegalMoves(aiColour);

        if (legalMoves.size() > 0) {
            Board.Move chosenMove;

            switch (aiDifficulty) {
                case 1: //easyy bot
                    chosenMove = getRandomMove(legalMoves);
                    break;
                case 2: // Medium bot :O
                    chosenMove = getMediumMove(legalMoves);
                    break;
                case 3: // Hard bot
                    chosenMove = getHardMove(legalMoves);
                    break;
                default:
                    chosenMove = getRandomMove(legalMoves);
                    break;
            }

            //ACTUALLY making the move
            board.makeMove(chosenMove);
            whiteTurn = !whiteTurn;
        }
    }

    // Easy AI picking random move
    private Board.Move getRandomMove(java.util.ArrayList<Board.Move> legalMoves) {
        return legalMoves.get(random.nextInt(legalMoves.size()));
    }

    // Medium AI not finished =(
    private Board.Move getMediumMove(java.util.ArrayList<Board.Move> legalMoves) {
        return getRandomMove(legalMoves);
    }

    // Hard AI not finished :(
    private Board.Move getHardMove(java.util.ArrayList<Board.Move> legalMoves) {
        return getRandomMove(legalMoves);
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setAiDifficulty(int difficulty) {
        this.aiDifficulty = difficulty;
    }
}
