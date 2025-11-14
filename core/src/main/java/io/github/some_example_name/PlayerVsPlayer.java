package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerVsPlayer {
    private Board board;
    private boolean whiteTurn = true;
    private Piece selectedPiece;
    private boolean pieceSelected = false;

    public PlayerVsPlayer(Board board) {
        this.board = board;
    }

    public void click(float x, float y) {
        if (board.gameOver) return;

        if (!pieceSelected) {
            // Select a piece
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
            boolean moveSuccessful = board.tryMove(selectedPiece, x, y, whiteTurn);
            if (moveSuccessful) {
                whiteTurn = !whiteTurn;
            }
            pieceSelected = false;
        }
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
}
