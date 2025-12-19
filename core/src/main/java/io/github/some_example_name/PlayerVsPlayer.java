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

    public boolean click(float x, float y) {
        if (board.gameOver) return false;

        if (!pieceSelected) {
            for (Piece piece : board.getPieces()) {
                if (piece.getX() < 1000 && x >= piece.getX() && x <= piece.getX() + piece.getWidth() &&
                    y >= piece.getY() && y <= piece.getY() + piece.getHeight()) {

                    if (whiteTurn && piece.getColour() == PieceColour.WHITE) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        return false;
                    } else if (!whiteTurn && piece.getColour() == PieceColour.BLACK) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        return false;
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
            return moveSuccessful;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
        board.drawCapturedPieces(batch);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
}
