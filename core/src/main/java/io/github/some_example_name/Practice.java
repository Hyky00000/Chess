package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class Practice {
    private Board board;
    private int difficulty;
    private boolean moveMade = false;
    private boolean moveCorrect = false;
    private Random rand;

    private Texture whiteKingTex, whiteQueenTex, whiteRookTex, whiteKnightTex, whiteBishopTex, whitePawnTex;
    private Texture blackKingTex, blackQueenTex, blackRookTex, blackKnightTex, blackBishopTex, blackPawnTex;

    private int correctTargetCol = -1;
    private int correctTargetRow = -1;

    public Practice(Board board, int difficulty,
                    Texture whiteKingTex, Texture whiteQueenTex, Texture whiteRookTex,
                    Texture whiteKnightTex, Texture whiteBishopTex, Texture whitePawnTex,
                    Texture blackKingTex, Texture blackQueenTex, Texture blackRookTex,
                    Texture blackKnightTex, Texture blackBishopTex, Texture blackPawnTex) {
        this.board = board;
        this.difficulty = difficulty;
        this.rand = new Random();

        this.whiteKingTex = whiteKingTex;
        this.whiteQueenTex = whiteQueenTex;
        this.whiteRookTex = whiteRookTex;
        this.whiteKnightTex = whiteKnightTex;
        this.whiteBishopTex = whiteBishopTex;
        this.whitePawnTex = whitePawnTex;
        this.blackKingTex = blackKingTex;
        this.blackQueenTex = blackQueenTex;
        this.blackRookTex = blackRookTex;
        this.blackKnightTex = blackKnightTex;
        this.blackBishopTex = blackBishopTex;
        this.blackPawnTex = blackPawnTex;

        setupPosition();
    }

    private void setPieceAt(int col, int row, Piece piece) {
        float x = board.boardX + board.borderOffsetX + col * board.squareSize;
        float y = board.boardY + board.borderOffsetY + row * board.squareSize;
        piece.setX(x);
        piece.setY(y);

        for (int i = 0; i < board.pieces.length; i++) {
            if (board.pieces[i] == null || board.pieces[i].getX() >= 1000) {
                board.pieces[i] = piece;
                break;
            }
        }
    }

    private void setupPosition() {
        board.ResetGame();

        for (Piece piece : board.pieces) {
            piece.setX(1000);
            piece.setY(1000);
        }

        if (difficulty == 1) {
            int randomPos = rand.nextInt(3) + 1;

            if (randomPos == 1) {
                setPieceAt(4, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(3, 2, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
                setPieceAt(4, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                correctTargetCol = 7;
                correctTargetRow = 6;
            } else if (randomPos == 2) {
                setPieceAt(4, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(0, 0, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(1, 1, new Knight(0, 0, 56, 56, PieceColour.BLACK, blackKnightTex));
                setPieceAt(7, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                correctTargetCol = 1;
                correctTargetRow = 1;
            } else {
                setPieceAt(4, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(2, 2, new Knight(0, 0, 56, 56, PieceColour.WHITE, whiteKnightTex));
                setPieceAt(3, 1, new Queen(0, 0, 56, 56, PieceColour.BLACK, blackQueenTex));
                setPieceAt(1, 1, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(4, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                correctTargetCol = 1;
                correctTargetRow = 4;
            }
        } else if (difficulty == 2) {
            setPieceAt(4, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
            setPieceAt(3, 2, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
            setPieceAt(4, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
            correctTargetCol = 7;
            correctTargetRow = 6;
        } else if (difficulty == 3) {
            setPieceAt(4, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
            setPieceAt(3, 2, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
            setPieceAt(4, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
            correctTargetCol = 7;
            correctTargetRow = 6;
        }

        board.whiteTurn = true;
        moveMade = false;
        moveCorrect = false;
    }

    public boolean click(float x, float y) {
        if (moveMade) return false;

        int col = (int)((x - board.boardX - board.borderOffsetX) / board.squareSize);
        int row = (int)((y - board.boardY - board.borderOffsetY) / board.squareSize);

        if (col < 0 || col > 7 || row < 0 || row > 7) return false;

        float targetX = board.boardX + board.borderOffsetX + col * board.squareSize;
        float targetY = board.boardY + board.borderOffsetY + row * board.squareSize;

        Piece clickedPiece = null;
        for (Piece piece : board.pieces) {
            if (piece != null && piece.getX() < 1000 && piece.getColour() == PieceColour.WHITE) {
                int pieceCol = (int)((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                int pieceRow = (int)((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                if (pieceCol == col && pieceRow == row) {
                    clickedPiece = piece;
                    break;
                }
            }
        }

        if (clickedPiece != null) {
            boolean moveSuccess = board.tryMove(clickedPiece, targetX, targetY, true);

            if (moveSuccess) {
                moveMade = true;
                moveCorrect = (col == correctTargetCol && row == correctTargetRow);
                return true;
            }
        }

        return false;
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
    }

    public boolean isMoveMade() {
        return moveMade;
    }

    public boolean isMoveCorrect() {
        return moveCorrect;
    }

    public void reset() {
        setupPosition();
    }
}
