package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;

public class Queen extends Piece {
    public Queen(float x, float y, float width, float height, PieceColour colour, Texture texture) {
        super(x, y, width, height, colour, texture);
    }

    public boolean isValidMove(float nextX, float nextY, Board board) {
        int currentCol = (int)((getX() - board.boardX - board.borderOffsetX) / board.squareSize);
        int currentRow = (int)((getY() - board.boardY - board.borderOffsetY) / board.squareSize);
        int nextCol = (int)((nextX - board.boardX - board.borderOffsetX) / board.squareSize);
        int nextRow = (int)((nextY - board.boardY - board.borderOffsetY) / board.squareSize);

        int changeX = nextCol - currentCol;
        int changeY = nextRow - currentRow;

        if (changeX == 0 && changeY == 0) {
            return false;
        }

        boolean validDirection = false;
        if (changeX == 0) validDirection = true;
        if (changeY == 0) validDirection = true;
        if (Math.abs(changeX) == Math.abs(changeY)) validDirection = true;

        if (validDirection == false) return false;

        int stepX = 0;
        int stepY = 0;
        if (changeX > 0) stepX = 1;
        else if (changeX < 0) stepX = -1;
        if (changeY > 0) stepY = 1;
        else if (changeY < 0) stepY = -1;

        int checkCol = currentCol + stepX;
        int checkRow = currentRow + stepY;

        while (checkCol != nextCol || checkRow != nextRow) {
            for (Piece piece : board.getPieces()) {
                if (piece.getX() < 1000) {
                    int pieceCol = (int)((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                    int pieceRow = (int)((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                    if (pieceCol == checkCol && pieceRow == checkRow) {
                        return false;
                    }
                }
            }
            checkCol = checkCol + stepX;
            checkRow = checkRow + stepY;
        }

        for (Piece piece : board.getPieces()) {
            if (piece.getX() < 1000) {
                int pieceCol = (int)((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                int pieceRow = (int)((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
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
