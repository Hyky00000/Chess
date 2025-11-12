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
    private int aiDifficulty; // 1 is easy 2 is medium 3 is hard
    private Random random = new Random();

    public PlayerVsComputer(Board board, boolean playerIsWhite, int aiDifficulty) {
        this.board = board;
        this.playerIsWhite = playerIsWhite;
        this.aiDifficulty = aiDifficulty;
    }

    public void click(float x, float y) {
        if (board.gameOver) return;

        // Ignore the click if computer's turn
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
            aiColour = PieceColour.BLACK;  // If player is white AI is black
        } else {
            aiColour = PieceColour.WHITE;  // If player is black AI is white
        }

        java.util.ArrayList<Board.Move> legalMoves = board.getAllLegalMoves(aiColour);

        if (legalMoves.size() > 0) {
            Board.Move chosenMove;

            switch (aiDifficulty) {
                case 1: // Easy bot completely random moves
                    chosenMove = getRandomMove(legalMoves);
                    break;
                case 2: // Medium bot picks from good but not best moves
                    chosenMove = getMediumMove(aiColour);
                    break;
                case 3: // Hard bot - picks from best moves
                    chosenMove = getHardMove(aiColour);
                    break;
                default:
                    chosenMove = getRandomMove(legalMoves);
                    break;
            }

            // ACTUALLY making the move on the board
            board.makeMove(chosenMove);
            whiteTurn = !whiteTurn;  // Switch turns after AI moves
        }
    }

    // Easy AI picking random move from all legal moves
    private Board.Move getRandomMove(java.util.ArrayList<Board.Move> legalMoves) {
        // Pick a random number between 0 and size of legal moves
        int randomIndex = random.nextInt(legalMoves.size());
        return legalMoves.get(randomIndex);
    }

    // Medium AI pick from good moves from the top third
    private Board.Move getMediumMove(PieceColour aiColour) {
        // Get all legal moves sorted from best to worst
        java.util.ArrayList<Board.Move> sortedMoves = board.getSortedLegalMoves(aiColour);
        if (sortedMoves.size() == 0) {
            return null;  // No moves available
        }

        // Calculate how many moves to consider from the top
        int topMovesCount = sortedMoves.size() / 3;  // Top third of moves
        if (topMovesCount < 1) {
            topMovesCount = 1;  //make sure if theres one move left then its 1 instead of 0.333333......
        }

        // Pick a random move from the top moves
        int randomIndex = random.nextInt(topMovesCount);
        return sortedMoves.get(randomIndex);
    }

    // Hard AI - picks from the very best moves
    private Board.Move getHardMove(PieceColour aiColour) {
        // Get all legal moves sorted from best to worst
        java.util.ArrayList<Board.Move> sortedMoves = board.getSortedLegalMoves(aiColour);
        if (sortedMoves.size() == 0) {
            return null;  // No moves :_[
        }

        // Decide how many top moves to consider
        int topMovesToConsider = 2;  // Always pick from top 2 moves so games dont repeat
        if (sortedMoves.size() < 2) {
            topMovesToConsider = sortedMoves.size();  // if there is only one move then of course the one move
        }

        // Pick randomly from the top moves
        int randomIndex = random.nextInt(topMovesToConsider);
        return sortedMoves.get(randomIndex);
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
