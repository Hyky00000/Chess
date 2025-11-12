package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;

public class Knight extends Piece {
    public Knight(float x, float y, float width, float height, PieceColour colour, Texture texture) {
        super(x, y, width, height, colour, texture);
    }

    public boolean isValidMove(float nextX, float nextY, Board board) {
        int currentCol = (int)((getX() - board.boardX - board.borderOffsetX) / board.squareSize);
        int currentRow = (int)((getY() - board.boardY - board.borderOffsetY) / board.squareSize);
        int nextCol = (int)((nextX - board.boardX - board.borderOffsetX) / board.squareSize);
        int nextRow = (int)((nextY - board.boardY - board.borderOffsetY) / board.squareSize);

        int changeX = nextCol - currentCol;
        int changeY = nextRow - currentRow;

        if ((changeX == 2 && (changeY == 1 || changeY == -1)) ||
            (changeX == -2 && (changeY == 1 || changeY == -1)) ||
            (changeX == 1 && (changeY == 2 || changeY == -2)) ||
            (changeX == -1 && (changeY == 2 || changeY == -2))) {

            // next square
            for (Piece piece : board.getPieces()) {
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
            return true; // Empty square
        }
        return false;
    }
}
