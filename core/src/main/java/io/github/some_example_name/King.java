package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;

public class King extends Piece {
    public boolean hasMoved = false;

    public King(float x, float y, float width, float height, PieceColour colour, Texture texture) {
        super(x, y, width, height, colour, texture);
    }

    public boolean isValidMove(float nextX, float nextY, Board board) {
        int currentCol = (int) ((getX() - board.boardX - board.borderOffsetX) / board.squareSize);
        int currentRow = (int) ((getY() - board.boardY - board.borderOffsetY) / board.squareSize);
        int nextCol = (int) ((nextX - board.boardX - board.borderOffsetX) / board.squareSize);
        int nextRow = (int) ((nextY - board.boardY - board.borderOffsetY) / board.squareSize);

        int changeX = nextCol - currentCol;
        int changeY = nextRow - currentRow;

        if (changeX == 0 && changeY == 0) {
            return false;
        }

        // Normal king move
        boolean validMove = false;
        if (changeX >= -1 && changeX <= 1 && changeY >= -1 && changeY <= 1) {
            validMove = true;
        }

        // Castling move
        if (!hasMoved && changeY == 0 && (changeX == 2 || changeX == -2)) {
            // Check if squares between king and rook are empty
            if (changeX == 2) { // Kingside
                // Check squares between king and rook (columns 5 and 6)
                for (int col = currentCol + 1; col <= currentCol + 2; col++) {
                    for (Piece piece : board.getPieces()) {
                        if (piece.getX() < 1000) { // Only check pieces on board
                            int pieceCol = (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                            int pieceRow = (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                            if (pieceCol == col && pieceRow == currentRow) {
                                return false; // Piece in the way
                            }
                        }
                    }
                }
                // Check if rook hasn't moved and is in right position
                boolean rookFound = false;
                for (Piece piece : board.getPieces()) {
                    if (piece instanceof Rook && piece.getColour() == this.colour && !((Rook) piece).hasMoved) {
                        int pieceCol = (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int pieceRow = (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (pieceCol == 7 && pieceRow == currentRow) {
                            rookFound = true;
                            break;
                        }
                    }
                }
                if (!rookFound) return false;
            } else { // Queenside
                // Check squares between king and rook (columns 1, 2, 3)
                for (int col = currentCol - 1; col >= currentCol - 2; col--) {
                    for (Piece piece : board.getPieces()) {
                        if (piece.getX() < 1000) { // Only check pieces on board
                            int pieceCol = (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                            int pieceRow = (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                            if (pieceCol == col && pieceRow == currentRow) {
                                return false; // Piece in the way
                            }
                        }
                    }
                }
                // Check if rook hasn't moved and is in right position
                boolean rookFound = false;
                for (Piece piece : board.getPieces()) {
                    if (piece instanceof Rook && piece.getColour() == this.colour && !((Rook) piece).hasMoved) {
                        int pieceCol = (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int pieceRow = (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (pieceCol == 0 && pieceRow == currentRow) {
                            rookFound = true;
                            break;
                        }
                    }
                }
                if (!rookFound) return false;
            }

            // Check if castling through check
            if (changeX == 2) { // Kingside
                for (int col = currentCol; col <= currentCol + 2; col++) {
                    for (Piece piece : board.getPieces()) {
                        if (piece.getColour() != this.colour && piece.getX() < 1000) {
                            if (piece.isValidMove(board.boardX + board.borderOffsetX + col * board.squareSize,
                                board.boardY + board.borderOffsetY + currentRow * board.squareSize, board)) {
                                return false;
                            }
                        }
                    }
                }
            } else { // Queenside
                for (int col = currentCol; col >= currentCol - 2; col--) {
                    for (Piece piece : board.getPieces()) {
                        if (piece.getColour() != this.colour && piece.getX() < 1000) {
                            if (piece.isValidMove(board.boardX + board.borderOffsetX + col * board.squareSize,
                                board.boardY + board.borderOffsetY + currentRow * board.squareSize, board)) {
                                return false;
                            }
                        }
                    }
                }
            }
            validMove = true;
        }

        if (validMove == false) {
            return false;
        }

        // Check if target square has friendly piece
        for (Piece piece : board.getPieces()) {
            if (piece.getX() < 1000) { // Only check pieces on board
                int pieceCol = (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                int pieceRow = (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);

                if (pieceCol == nextCol && pieceRow == nextRow) {
                    if (piece.getColour() == this.colour) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
