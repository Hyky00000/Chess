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

        int col = (int)((x - board.boardX - board.borderOffsetX) / board.squareSize);
        int row = (int)((y - board.boardY - board.borderOffsetY) / board.squareSize);

        if (col >= 0 && col < 8 && row >= 0 && row < 8) {
            String square = getSquareNotation(col, row);
            System.out.println("clicked " + square);
        }

        if (!pieceSelected) {
            for (Piece piece : board.getPieces()) {
                if (piece.getX() < 1000 && x >= piece.getX() && x <= piece.getX() + piece.getWidth() &&
                    y >= piece.getY() && y <= piece.getY() + piece.getHeight()) {

                    if (whiteTurn && piece.getColour() == PieceColour.WHITE) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        String pieceType = getPieceType(piece);
                        System.out.println("selected white " + pieceType);
                        return false;
                    } else if (!whiteTurn && piece.getColour() == PieceColour.BLACK) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        String pieceType = getPieceType(piece);
                        System.out.println("selected black " + pieceType);
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

    private String getSquareNotation(int col, int row) {
        char colChar = (char)('A' + col);
        int displayRow = row + 1;
        return "" + colChar + displayRow;
    }

    private String getPieceType(Piece piece) {
        if (piece instanceof Pawn) return "pawn";
        if (piece instanceof Knight) return "knight";
        if (piece instanceof Bishop) return "bishop";
        if (piece instanceof Rook) return "rook";
        if (piece instanceof Queen) return "queen";
        if (piece instanceof King) return "king";
        return "unknown";
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
        board.drawCapturedPieces(batch);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
}
