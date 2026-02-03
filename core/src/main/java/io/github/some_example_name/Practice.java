package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Practice {

    private Board board;
    int difficulty;

    private boolean waitingForClick = false;
    private boolean moveCorrect = false;
    boolean puzzleForWhite;

    public int easyPositionCounter = 0;
    public int mediumPositionCounter = 0;
    public int hardPositionCounter = 0;

    private Texture whiteKingTex, whiteQueenTex, whiteRookTex, whiteKnightTex, whiteBishopTex, whitePawnTex;
    private Texture blackKingTex, blackQueenTex, blackRookTex, blackKnightTex, blackBishopTex, blackPawnTex;

    private int correctTargetCol = -1;
    private int correctTargetRow = -1;
    private int correctStartCol = -1;
    private int correctStartRow = -1;
    private Piece correctPieceToMove = null;

    private Piece selectedPiece = null;

    private int step = 0;

    public Practice(Board board, int difficulty,
                    Texture whiteKingTex, Texture whiteQueenTex, Texture whiteRookTex,
                    Texture whiteKnightTex, Texture whiteBishopTex, Texture whitePawnTex,
                    Texture blackKingTex, Texture blackQueenTex, Texture blackRookTex,
                    Texture blackKnightTex, Texture blackBishopTex, Texture blackPawnTex) {

        this.board = board;
        this.difficulty = difficulty;

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
        this.puzzleForWhite = board.whiteTurn;
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

    private void clearBoardPieces() {
        for (Piece piece : board.pieces) {
            if (piece != null) {
                piece.setX(1000);
                piece.setY(1000);
            }
        }
    }

    private void setupPosition() {
        board.ResetGame();

        selectedPiece = null;
        waitingForClick = false;
        moveCorrect = false;
        step = 0;

        clearBoardPieces();

        if (difficulty == 1) {
            easyPositionCounter = (easyPositionCounter % 3) + 1;

            if (easyPositionCounter == 1) {
                setPieceAt(5, 2, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(2, 6, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(1, 5, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(5, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));

                correctStartCol = 1;
                correctStartRow = 5;
                correctTargetCol = 1;
                correctTargetRow = 7;
                board.whiteTurn = true;

            } else if (easyPositionCounter == 2) {
                setPieceAt(4, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(3, 0, new Knight(0, 0, 56, 56, PieceColour.WHITE, whiteKnightTex));
                setPieceAt(1, 0, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(3, 4, new Queen(0, 0, 56, 56, PieceColour.BLACK, blackQueenTex));

                correctStartCol = 3;
                correctStartRow = 0;
                correctTargetCol = 2;
                correctTargetRow = 2;
                board.whiteTurn = true;

            } else {
                setPieceAt(6, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(5, 1, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
                setPieceAt(0, 5, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(3, 7, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(7, 7, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));

                correctStartCol = 3;
                correctStartRow = 7;
                correctTargetCol = 1;
                correctTargetRow = 5;
                board.whiteTurn = false;
            }

        } else if (difficulty == 2) {
            mediumPositionCounter = (mediumPositionCounter % 3) + 1;

            if (mediumPositionCounter == 1) {
                setPieceAt(6, 2, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(2, 3, new Queen(0, 0, 56, 56, PieceColour.BLACK, blackQueenTex));
                setPieceAt(0, 3, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(1, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(3, 5, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(6, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(7, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));

                setPieceAt(5, 4, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(5, 7, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
                setPieceAt(6, 3, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(7, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(6, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(4, 5, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(0, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));

                correctStartCol = 2;
                correctStartRow = 3;
                correctTargetCol = 5;
                correctTargetRow = 3;
                board.whiteTurn = false;

            } else if (mediumPositionCounter == 2) {
                setPieceAt(7, 5, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(1, 6, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(3, 1, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(6, 5, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(6, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(7, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));

                setPieceAt(7, 1, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(2, 7, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(2, 2, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(3, 5, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(4, 3, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(6, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));

                correctStartCol = 3;
                correctStartRow = 1;
                correctTargetCol = 5;
                correctTargetRow = 3;
                board.whiteTurn = false;

            } else {
                setPieceAt(6, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(1, 1, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
                setPieceAt(1, 0, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(4, 1, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(4, 2, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(2, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(4, 3, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(5, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(6, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(7, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));

                setPieceAt(6, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(0, 4, new Queen(0, 0, 56, 56, PieceColour.BLACK, blackQueenTex));
                setPieceAt(3, 7, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(4, 5, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(6, 6, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(0, 3, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(0, 5, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(4, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(5, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(7, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));

                correctStartCol = 4;
                correctStartRow = 2;
                correctTargetCol = 1;
                correctTargetRow = 5;
                board.whiteTurn = true;
            }

        } else if (difficulty == 3) {
            hardPositionCounter = (hardPositionCounter % 3) + 1;

            if (hardPositionCounter == 1) {
                setPieceAt(5, 4, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(5, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));

                setPieceAt(6, 6, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(5, 3, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));

                correctStartCol = 6;
                correctStartRow = 6;
                correctTargetCol = 5;
                correctTargetRow = 6;
                board.whiteTurn = false;

            } else if (hardPositionCounter == 2) {
                setPieceAt(6, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(5, 4, new Queen(0, 0, 56, 56, PieceColour.BLACK, blackQueenTex));
                setPieceAt(4, 7, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(4, 6, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(3, 5, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(7, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(6, 3, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(5, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(3, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(1, 5, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(0, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));

                setPieceAt(6, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(3, 0, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
                setPieceAt(2, 0, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(2, 5, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(3, 1, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(5, 0, new Knight(0, 0, 56, 56, PieceColour.WHITE, whiteKnightTex));
                setPieceAt(7, 3, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(6, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(3, 3, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(1, 4, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(1, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));

                correctStartCol = 4;
                correctStartRow = 6;
                correctTargetCol = 4;
                correctTargetRow = 1;
                board.whiteTurn = false;

            } else if (hardPositionCounter == 3) {
                setPieceAt(6, 0, new King(0, 0, 56, 56, PieceColour.WHITE, whiteKingTex));
                setPieceAt(6, 3, new Queen(0, 0, 56, 56, PieceColour.WHITE, whiteQueenTex));
                setPieceAt(4, 0, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(3, 0, new Rook(0, 0, 56, 56, PieceColour.WHITE, whiteRookTex));
                setPieceAt(3, 3, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(6, 1, new Bishop(0, 0, 56, 56, PieceColour.WHITE, whiteBishopTex));
                setPieceAt(2, 2, new Knight(0, 0, 56, 56, PieceColour.WHITE, whiteKnightTex));
                setPieceAt(7, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(5, 4, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(2, 1, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(1, 3, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));
                setPieceAt(6, 2, new Pawn(0, 0, 56, 56, PieceColour.WHITE, whitePawnTex));

                setPieceAt(6, 7, new King(0, 0, 56, 56, PieceColour.BLACK, blackKingTex));
                setPieceAt(1, 6, new Queen(0, 0, 56, 56, PieceColour.BLACK, blackQueenTex));
                setPieceAt(3, 7, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(2, 7, new Rook(0, 0, 56, 56, PieceColour.BLACK, blackRookTex));
                setPieceAt(2, 5, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(4, 6, new Bishop(0, 0, 56, 56, PieceColour.BLACK, blackBishopTex));
                setPieceAt(2, 3, new Knight(0, 0, 56, 56, PieceColour.BLACK, blackKnightTex));
                setPieceAt(7, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(6, 5, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(5, 6, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(3, 5, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));
                setPieceAt(1, 4, new Pawn(0, 0, 56, 56, PieceColour.BLACK, blackPawnTex));

                correctStartCol = 2;
                correctStartRow = 5;
                correctTargetCol = 6;
                correctTargetRow = 1;
                board.whiteTurn = false;
            }
        }

        correctPieceToMove = null;
        for (Piece piece : board.pieces) {
            if (piece != null && piece.getX() < 1000) {
                int pieceCol = (int) ((piece.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                int pieceRow = (int) ((piece.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                if (pieceCol == correctStartCol && pieceRow == correctStartRow) {
                    correctPieceToMove = piece;
                    break;
                }
            }
        }
    }

    public boolean click(float x, float y) {
        if (waitingForClick) {
            reset();
            return true;
        }

        int col = (int) ((x - board.boardX - board.borderOffsetX) / board.squareSize);
        int row = (int) ((y - board.boardY - board.borderOffsetY) / board.squareSize);

        if (col < 0 || col > 7 || row < 0 || row > 7) return false;

        if (selectedPiece == null) {
            if (correctPieceToMove == null) return false;

            int startCol = correctStartCol;
            int startRow = correctStartRow;

            if (col == startCol && row == startRow) {
                int currentCol = (int) ((correctPieceToMove.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                int currentRow = (int) ((correctPieceToMove.getY() - board.boardY - board.borderOffsetY) / board.squareSize);

                if (currentCol == startCol && currentRow == startRow) {
                    selectedPiece = correctPieceToMove;
                    return true;
                }
            }

            return false;
        }

        int targetCol = correctTargetCol;
        int targetRow = correctTargetRow;

        if (col != targetCol || row != targetRow) {
            selectedPiece = null;
            return false;
        }

        float targetX = board.boardX + board.borderOffsetX + targetCol * board.squareSize;
        float targetY = board.boardY + board.borderOffsetY + targetRow * board.squareSize;

        boolean moveSuccess = board.tryMove(selectedPiece, targetX, targetY,
            selectedPiece.getColour() == PieceColour.WHITE);

        if (!moveSuccess) {
            selectedPiece = null;
            return false;
        }

        selectedPiece = null;
        moveCorrect = true;

        if (difficulty == 1 && easyPositionCounter == 1) {
            waitingForClick = true;
            return true;
        }

        if (difficulty == 1 && easyPositionCounter == 2) {
            if (step == 0) {
                step = 1;

                Piece blackKing = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000 &&
                        p instanceof King &&
                        p.getColour() == PieceColour.BLACK) {
                        blackKing = p;
                        break;
                    }
                }

                if (blackKing != null) {
                    int kingTargetCol = 1;
                    int kingTargetRow = 1;
                    float tx = board.boardX + board.borderOffsetX + kingTargetCol * board.squareSize;
                    float ty = board.boardY + board.borderOffsetY + kingTargetRow * board.squareSize;
                    board.tryMove(blackKing, tx, ty, false);
                }

                correctStartCol = 2;
                correctStartRow = 2;
                correctTargetCol = 3;
                correctTargetRow = 4;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p.getColour() == PieceColour.WHITE) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 1 && easyPositionCounter == 3) {
            if (step == 0) {
                step = 1;

                Piece whiteQueen = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 5 && r == 1 && p instanceof Queen && p.getColour() == PieceColour.WHITE) {
                            whiteQueen = p;
                            break;
                        }
                    }
                }

                if (whiteQueen != null) {
                    float queenTargetX = board.boardX + board.borderOffsetX + 1 * board.squareSize;
                    float queenTargetY = board.boardY + board.borderOffsetY + 5 * board.squareSize;
                    board.tryMove(whiteQueen, queenTargetX, queenTargetY, true);
                }

                correctStartCol = 0;
                correctStartRow = 5;
                correctTargetCol = 1;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof King && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 2 && mediumPositionCounter == 1) {
            if (step == 0) {
                step = 1;

                Piece blackKing = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000 &&
                        p instanceof King &&
                        p.getColour() == PieceColour.BLACK) {
                        blackKing = p;
                        break;
                    }
                }

                if (blackKing != null) {
                    int kingTargetCol = 4;
                    int kingTargetRow = 5;
                    float tx = board.boardX + board.borderOffsetX + kingTargetCol * board.squareSize;
                    float ty = board.boardY + board.borderOffsetY + kingTargetRow * board.squareSize;
                    board.tryMove(blackKing, tx, ty, false);
                }

                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 2 && mediumPositionCounter == 2) {
            if (step == 0) {
                step = 1;

                Piece whitePawn = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 6 && r == 1 && p instanceof Pawn && p.getColour() == PieceColour.WHITE) {
                            whitePawn = p;
                            break;
                        }
                    }
                }

                if (whitePawn != null) {
                    float pawnTargetX = board.boardX + board.borderOffsetX + 6 * board.squareSize;
                    float pawnTargetY = board.boardY + board.borderOffsetY + 2 * board.squareSize;
                    board.tryMove(whitePawn, pawnTargetX, pawnTargetY, true);
                }

                correctStartCol = 5;
                correctStartRow = 3;
                correctTargetCol = 3;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof Bishop && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 2 && mediumPositionCounter == 3) {
            if (step == 0) {
                step = 1;

                Piece blackRook = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 3 && r == 7 && p instanceof Rook && p.getColour() == PieceColour.BLACK) {
                            blackRook = p;
                            break;
                        }
                    }
                }

                if (blackRook != null) {
                    float rookTargetX = board.boardX + board.borderOffsetX + 1 * board.squareSize;
                    float rookTargetY = board.boardY + board.borderOffsetY + 7 * board.squareSize;
                    board.tryMove(blackRook, rookTargetX, rookTargetY, false);
                }

                correctStartCol = 1;
                correctStartRow = 5;
                correctTargetCol = 0;
                correctTargetRow = 4;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof Bishop && p.getColour() == PieceColour.WHITE) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                step = 2;

                Piece blackRook = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 1 && r == 7 && p instanceof Rook && p.getColour() == PieceColour.BLACK) {
                            blackRook = p;
                            break;
                        }
                    }
                }

                if (blackRook != null) {
                    float rookTargetX = board.boardX + board.borderOffsetX + 1 * board.squareSize;
                    float rookTargetY = board.boardY + board.borderOffsetY + 1 * board.squareSize;
                    board.tryMove(blackRook, rookTargetX, rookTargetY, false);
                }

                correctStartCol = 1;
                correctStartRow = 0;
                correctTargetCol = 1;
                correctTargetRow = 1;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof Rook && p.getColour() == PieceColour.WHITE) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 2) {
                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 3 && hardPositionCounter == 1) {
            if (step == 0) {
                step = 1;

                Piece whiteKing = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000 &&
                        p instanceof King &&
                        p.getColour() == PieceColour.WHITE) {
                        whiteKing = p;
                        break;
                    }
                }

                if (whiteKing != null) {
                    int kingTargetCol = 5;
                    int kingTargetRow = 3;
                    float tx = board.boardX + board.borderOffsetX + kingTargetCol * board.squareSize;
                    float ty = board.boardY + board.borderOffsetY + kingTargetRow * board.squareSize;
                    board.tryMove(whiteKing, tx, ty, true);
                }

                correctStartCol = 5;
                correctStartRow = 6;
                correctTargetCol = 5;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof King && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                step = 2;

                Piece whiteKing = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000 &&
                        p instanceof King &&
                        p.getColour() == PieceColour.WHITE) {
                        whiteKing = p;
                        break;
                    }
                }

                if (whiteKing != null) {
                    int kingTargetCol = 6;
                    int kingTargetRow = 3;
                    float tx = board.boardX + board.borderOffsetX + kingTargetCol * board.squareSize;
                    float ty = board.boardY + board.borderOffsetY + kingTargetRow * board.squareSize;
                    board.tryMove(whiteKing, tx, ty, true);
                }

                correctStartCol = 5;
                correctStartRow = 5;
                correctTargetCol = 6;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof King && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 2) {
                step = 3;

                Piece whiteKing = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000 &&
                        p instanceof King &&
                        p.getColour() == PieceColour.WHITE) {
                        whiteKing = p;
                        break;
                    }
                }

                if (whiteKing != null) {
                    int kingTargetCol = 5;
                    int kingTargetRow = 3;
                    float tx = board.boardX + board.borderOffsetX + kingTargetCol * board.squareSize;
                    float ty = board.boardY + board.borderOffsetY + kingTargetRow * board.squareSize;
                    board.tryMove(whiteKing, tx, ty, true);
                }

                correctStartCol = 6;
                correctStartRow = 5;
                correctTargetCol = 5;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof King && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 3) {
                step = 4;

                Piece whiteKing = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000 &&
                        p instanceof King &&
                        p.getColour() == PieceColour.WHITE) {
                        whiteKing = p;
                        break;
                    }
                }

                if (whiteKing != null) {
                    int kingTargetCol = 6;
                    int kingTargetRow = 3;
                    float tx = board.boardX + board.borderOffsetX + kingTargetCol * board.squareSize;
                    float ty = board.boardY + board.borderOffsetY + kingTargetRow * board.squareSize;
                    board.tryMove(whiteKing, tx, ty, true);
                }

                correctStartCol = 5;
                correctStartRow = 5;
                correctTargetCol = 6;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof King && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 4) {
                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 3 && hardPositionCounter == 2) {
            if (step == 0) {
                step = 1;

                Piece whiteBishop = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 3 && r == 1 && p instanceof Bishop && p.getColour() == PieceColour.WHITE) {
                            whiteBishop = p;
                            break;
                        }
                    }
                }

                if (whiteBishop != null) {
                    float bishopTargetX = board.boardX + board.borderOffsetX + 4 * board.squareSize;
                    float bishopTargetY = board.boardY + board.borderOffsetY + 0 * board.squareSize;
                    board.tryMove(whiteBishop, bishopTargetX, bishopTargetY, true);
                }

                correctStartCol = 5;
                correctStartRow = 4;
                correctTargetCol = 5;
                correctTargetRow = 2;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof Queen && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                step = 2;

                Piece whiteBishop = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 4 && r == 0 && p instanceof Bishop && p.getColour() == PieceColour.WHITE) {
                            whiteBishop = p;
                            break;
                        }
                    }
                }

                if (whiteBishop != null) {
                    float bishopTargetX = board.boardX + board.borderOffsetX + 5 * board.squareSize;
                    float bishopTargetY = board.boardY + board.borderOffsetY + 1 * board.squareSize;
                    board.tryMove(whiteBishop, bishopTargetX, bishopTargetY, true);
                }

                correctStartCol = 5;
                correctStartRow = 2;
                correctTargetCol = 5;
                correctTargetRow = 1;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof Queen && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 2) {
                waitingForClick = true;
                return true;
            }
        }

        if (difficulty == 3 && hardPositionCounter == 3) {
            if (step == 0) {
                step = 1;

                // Auto: White pawn f5 (5,4) takes pawn g6 (6,5)
                Piece whitePawn = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == 5 && r == 4 && p instanceof Pawn && p.getColour() == PieceColour.WHITE) {
                            whitePawn = p;
                            break;
                        }
                    }
                }

                if (whitePawn != null) {
                    float pawnTargetX = board.boardX + board.borderOffsetX + 6 * board.squareSize;
                    float pawnTargetY = board.boardY + board.borderOffsetY + 5 * board.squareSize;
                    board.tryMove(whitePawn, pawnTargetX, pawnTargetY, true);
                }

                // Next move: Black pawn h7 (7,6) takes pawn g6 (6,5)
                correctStartCol = 7;
                correctStartRow = 6;
                correctTargetCol = 6;
                correctTargetRow = 5;

                correctPieceToMove = null;
                for (Piece p : board.pieces) {
                    if (p != null && p.getX() < 1000) {
                        int c = (int) ((p.getX() - board.boardX - board.borderOffsetX) / board.squareSize);
                        int r = (int) ((p.getY() - board.boardY - board.borderOffsetY) / board.squareSize);
                        if (c == correctStartCol && r == correctStartRow && p instanceof Pawn && p.getColour() == PieceColour.BLACK) {
                            correctPieceToMove = p;
                            break;
                        }
                    }
                }

                moveCorrect = false;
                return true;
            } else if (step == 1) {
                waitingForClick = true;
                return true;
            }
        }

        waitingForClick = true;
        return true;
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
    }

    public boolean isMoveMade() {
        return waitingForClick;
    }

    public boolean isMoveCorrect() {
        return moveCorrect;
    }

    public void reset() {
        selectedPiece = null;
        setupPosition();
        this.puzzleForWhite = board.whiteTurn;
    }
}
