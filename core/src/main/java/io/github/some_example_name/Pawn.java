package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;

public class Pawn extends Piece {
    public boolean justMovedTwoSquares = false;

    public Pawn(float x, float y, float width, float height, PieceColour colour, Texture texture) {
        super(x, y, width, height, colour, texture);
    }

    public boolean isValidMove(float nextX, float nextY, Board board) {
        int currentCol = (int) ((x - board.boardX - board.borderOffsetX) / board.squareSize);
        int currentRow = (int) ((y - board.boardY - board.borderOffsetY) / board.squareSize);
        int nextCol = (int) ((nextX - board.boardX - board.borderOffsetX) / board.squareSize);
        int nextRow = (int) ((nextY - board.boardY - board.borderOffsetY) / board.squareSize);

        int changeX = nextCol - currentCol;
        int changeY = nextRow - currentRow;

        //white pawn movement
        if (colour == PieceColour.WHITE) {
            if (changeX == 0 && changeY == 1) {
                for (Piece piece : board.getPieces()) {
                    if (nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        nextRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return false;
                    }
                }
                return true;
            }

            //white pawn forward 2 squares
            if (currentRow == 1 && changeX == 0 && changeY == 2) {
                int middleRow = currentRow + 1;
                for (Piece piece : board.getPieces()) {
                    if (nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        nextRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return false;
                    }
                    if (nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        middleRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return false;
                    }
                }
                justMovedTwoSquares = true;
                return true;
            }

            // White capture
            if ((changeX == 1 || changeX == -1) && changeY == 1) {
                for (Piece piece : board.getPieces()) {
                    if (piece.getColour() == PieceColour.BLACK &&
                        nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        nextRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return true;
                    }
                }

                //white en passant capture
                for (Piece piece : board.getPieces()) {
                    if (piece.getColour() == PieceColour.BLACK) {
                        int pieceCol = (int)((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int pieceRow = (int)((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (pieceCol == nextCol && pieceRow == currentRow) {
                            if (piece instanceof Pawn) {
                                Pawn blackPawn = (Pawn) piece;
                                if (blackPawn.justMovedTwoSquares) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        //black pawn movement
        else {
            if (changeX == 0 && changeY == -1) {
                for (Piece piece : board.getPieces()) {
                    if (nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        nextRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return false;
                    }
                }
                return true;
            }

            //black pawn forward 2 squares
            if (currentRow == 6 && changeX == 0 && changeY == -2) {
                int middleRow = currentRow - 1;
                for (Piece piece : board.getPieces()) {
                    if (nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        nextRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return false;
                    }
                    if (nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        middleRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return false;
                    }
                }
                justMovedTwoSquares = true;
                return true;
            }

            //black capture
            if ((changeX == 1 || changeX == -1) && changeY == -1) {
                for (Piece piece : board.getPieces()) {
                    if (piece.getColour() == PieceColour.WHITE &&
                        nextCol == (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize) &&
                        nextRow == (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize)) {
                        return true;
                    }
                }

                // black en passent
                for (Piece piece : board.getPieces()) {
                    if (piece.getColour() == PieceColour.WHITE) {
                        int pieceCol = (int)((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int pieceRow = (int)((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (pieceCol == nextCol && pieceRow == currentRow) {
                            if (piece instanceof Pawn) {
                                Pawn whitePawn = (Pawn) piece;
                                if (whitePawn.justMovedTwoSquares) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
