package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
    private Texture boardTexture;
    public Piece[] pieces;
    public float squareSize = 54.5f;
    public float borderOffsetX = 22.5f;
    public float borderOffsetY = 22f;
    public float boardX = 0f;
    public float boardY = 0f;
    private Texture whiteQueenTex;
    private Texture blackQueenTex;
    public boolean gameOver = false;
    public String gameResult = "";

    public Board(Texture boardTexture, Texture whitePawnTex, Texture blackPawnTex, Texture whiteRookTex, Texture blackRookTex, Texture whiteKnightTex, Texture blackKnightTex, Texture whiteBishopTex, Texture blackBishopTex, Texture whiteQueenTex, Texture blackQueenTex, Texture whiteKingTex, Texture blackKingTex) {

        this.boardTexture = boardTexture;
        this.whiteQueenTex = whiteQueenTex;
        this.blackQueenTex = blackQueenTex;
        this.pieces = new Piece[32];

        float pieceOffsetX = boardX + borderOffsetX;
        float pieceOffsetY = boardY + borderOffsetY;

        float pieceMiddle = (squareSize - 56) / 2f;
        float px = 0;
        float py = 0;

        // White pieces
        pieces[0] = new Rook(pieceOffsetX + 0 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteRookTex);
        pieces[1] = new Knight(pieceOffsetX + 1 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteKnightTex);
        pieces[2] = new Bishop(pieceOffsetX + 2 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteBishopTex);
        pieces[3] = new Queen(pieceOffsetX + 3 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteQueenTex);
        pieces[4] = new King(pieceOffsetX + 4 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteKingTex);
        pieces[5] = new Bishop(pieceOffsetX + 5 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteBishopTex);
        pieces[6] = new Knight(pieceOffsetX + 6 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteKnightTex);
        pieces[7] = new Rook(pieceOffsetX + 7 * squareSize + px, pieceOffsetY + 0 * squareSize + py, 56, 56, PieceColour.WHITE, whiteRookTex);

        for (int i = 0; i < 8; i++) {
            pieces[8 + i] = new Pawn(pieceOffsetX + i * squareSize + px, pieceOffsetY + 1 * squareSize + py, 56, 56, PieceColour.WHITE, whitePawnTex);
        }

        // Black pieces
        pieces[16] = new Rook(pieceOffsetX + 0 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackRookTex);
        pieces[17] = new Knight(pieceOffsetX + 1 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackKnightTex);
        pieces[18] = new Bishop(pieceOffsetX + 2 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackBishopTex);
        pieces[19] = new Queen(pieceOffsetX + 3 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackQueenTex);
        pieces[20] = new King(pieceOffsetX + 4 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackKingTex);
        pieces[21] = new Bishop(pieceOffsetX + 5 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackBishopTex);
        pieces[22] = new Knight(pieceOffsetX + 6 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackKnightTex);
        pieces[23] = new Rook(pieceOffsetX + 7 * squareSize + px, pieceOffsetY + 7 * squareSize + py, 56, 56, PieceColour.BLACK, blackRookTex);

        for (int i = 0; i < 8; i++) {
            pieces[24 + i] = new Pawn(pieceOffsetX + i * squareSize + px, pieceOffsetY + 6 * squareSize + py, 56, 56, PieceColour.BLACK, blackPawnTex);
        }
    }

    public Piece[] getPieces() {
        return pieces;
    }

    // Try to make a move and return if successful
    public boolean tryMove(Piece piece, float targetX, float targetY, boolean whiteTurn) {
        if (gameOver) return false;
        if (piece.getColour() != (whiteTurn ? PieceColour.WHITE : PieceColour.BLACK)) return false;

        int targetCol = (int)((targetX - boardX - borderOffsetX) / squareSize);
        int targetRow = (int)((targetY - boardY - borderOffsetY) / squareSize);

        float snapX = boardX + borderOffsetX + targetCol * squareSize;
        float snapY = boardY + borderOffsetY + targetRow * squareSize;

        if (piece.isValidMove(snapX, snapY, this)) {
            // Save old position in case move puts king in check
            float oldX = piece.getX();
            float oldY = piece.getY();
            Piece capturedPiece = null;

            // Check if capturing a piece
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i] != piece && pieces[i].getX() < 1000) {
                    int pieceCol = (int)((pieces[i].getX() - boardX - borderOffsetX) / squareSize);
                    int pieceRow = (int)((pieces[i].getY() - boardY - borderOffsetY) / squareSize);
                    if (pieceCol == targetCol && pieceRow == targetRow) {
                        capturedPiece = pieces[i];
                        break;
                    }
                    //else if ()
                }
            }

            // Make the move temporarily
            piece.setX(snapX);
            piece.setY(snapY);
            if (capturedPiece != null) {
                capturedPiece.setX(1000);
            }

            // Handle castling
            if (piece instanceof King) {
                handleCastling((King) piece, oldX, snapX);
            }

            // Track when rook moves
            if (piece instanceof Rook) {
                ((Rook) piece).hasMoved = true;
            }

            // Check if this move leaves king in check
            boolean inCheck = isKingInCheck(piece.getColour());

            if (!inCheck) {
                // Move is legal - keep it
                checkPawnPromotion(piece, snapY);
                resetEnPassantFlags(piece.getColour());

                // Check for checkmate or stalemate after move
                PieceColour opponentColour = (piece.getColour() == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
                if (isCheckmate(opponentColour)) {
                    gameOver = true;
                    gameResult = (piece.getColour() == PieceColour.WHITE) ? "White wins by checkmate!" : "Black wins by checkmate!";
                } else if (isStalemate(opponentColour)) {
                    gameOver = true;
                    gameResult = "Draw by stalemate!";
                }
                return true;
            } else {
                // Move is illegal sooo undo it
                undoMove(piece, oldX, oldY, snapX, snapY, capturedPiece);
                return false;
            }
        }
        return false;
    }

    // Handle castling movement
    private void handleCastling(King king, float oldX, float newX) {
        int currentCol = (int)((oldX - boardX - borderOffsetX) / squareSize);
        int nextCol = (int)((newX - boardX - borderOffsetX) / squareSize);

        // Kingside castling
        if (nextCol == currentCol + 2) {
            for (Piece rook : pieces) {
                if (rook instanceof Rook && rook.getColour() == king.getColour() && rook.getX() < 1000) {
                    Rook castleRook = (Rook) rook;
                    int rookCol = (int)((rook.getX() - boardX - borderOffsetX) / squareSize);
                    if (rookCol == 7) {
                        rook.setX(boardX + borderOffsetX + (5 * squareSize));
                        castleRook.hasMoved = true;
                        king.hasMoved = true;
                        break;
                    }
                }
            }
        }
        // Queenside castling
        else if (nextCol == currentCol - 2) {
            for (Piece rook : pieces) {
                if (rook instanceof Rook && rook.getColour() == king.getColour() && rook.getX() < 1000) {
                    Rook castleRook = (Rook) rook;
                    int rookCol = (int)((rook.getX() - boardX - borderOffsetX) / squareSize);
                    if (rookCol == 0) {
                        rook.setX(boardX + borderOffsetX + (3 * squareSize));
                        castleRook.hasMoved = true;
                        king.hasMoved = true;
                        break;
                    }
                }
            }
        }
    }

    // Undo a move
    private void undoMove(Piece piece, float oldX, float oldY, float newX, float newY, Piece capturedPiece) {
        piece.setX(oldX);
        piece.setY(oldY);
        if (capturedPiece != null) {
            capturedPiece.setX(newX);
        }
        // Also undo castling if it happened
        if (piece instanceof King) {
            King king = (King) piece;
            int currentCol = (int)((oldX - boardX - borderOffsetX) / squareSize);
            int nextCol = (int)((newX - boardX - borderOffsetX) / squareSize);

            if (nextCol == currentCol + 2 || nextCol == currentCol - 2) {
                for (Piece rook : pieces) {
                    if (rook instanceof Rook && rook.getColour() == piece.getColour()) {
                        Rook castleRook = (Rook) rook;
                        int rookCol = (int)((rook.getX() - boardX - borderOffsetX) / squareSize);
                        if (nextCol == currentCol + 2 && rookCol == 5) {
                            rook.setX(boardX + borderOffsetX + (7 * squareSize));
                            castleRook.hasMoved = false;
                            king.hasMoved = false;
                        } else if (nextCol == currentCol - 2 && rookCol == 3) {
                            rook.setX(boardX + borderOffsetX + (0 * squareSize));
                            castleRook.hasMoved = false;
                            king.hasMoved = false;
                        }
                    }
                }
            }
        }
    }

    // Get all legal moves for a color
    public java.util.ArrayList<Move> getAllLegalMoves(PieceColour colour) {
        java.util.ArrayList<Move> legalMoves = new java.util.ArrayList<Move>();

        for (Piece piece : pieces) {
            if (piece.getColour() == colour && piece.getX() < 1000) {
                float originalX = piece.getX();
                float originalY = piece.getY();

                for (int col = 0; col < 8; col++) {
                    for (int row = 0; row < 8; row++) {
                        float testX = boardX + borderOffsetX + col * squareSize;
                        float testY = boardY + borderOffsetY + row * squareSize;

                        if (piece.isValidMove(testX, testY, this))
                        // PSEUDO LEGALLL
                        {
                            //check for capture
                            Piece capturedPiece = null;
                            for (Piece p : pieces) {
                                if (p != piece && p.getX() < 1000) {
                                    int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                                    int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);
                                    if (pCol == col && pRow == row) {
                                        capturedPiece = p;
                                        break;
                                    }
                                }
                            }

                            // TEMPORARILY make the move
                            piece.setX(testX);
                            piece.setY(testY);
                            if (capturedPiece != null) {
                                capturedPiece.setX(1000);
                            }

                            //check to see if the king is in check after the TEMPORARY move
                            boolean inCheck = isKingInCheck(piece.getColour());

                            //now the checks are done and the pieces can go back sadly
                            piece.setX(originalX);
                            piece.setY(originalY);
                            if (capturedPiece != null) {
                                capturedPiece.setX(testX);
                            }

                            if (!inCheck) {
                                legalMoves.add(new Move(piece, testX, testY, capturedPiece));
                            }
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    // Make a move (used by AI)
    public void makeMove(Move move) {
        Piece piece = move.piece;
        float targetX = move.targetX;
        float targetY = move.targetY;
        Piece capturedPiece = move.capturedPiece;

        piece.setX(targetX);
        piece.setY(targetY);
        if (capturedPiece != null) {
            capturedPiece.setX(1000);
        }

        // Handle castling
        if (piece instanceof King) {
            float oldX = piece.getX() - (targetX - piece.getX());
            handleCastling((King) piece, oldX, targetX);
        }

        // Track rook movement
        if (piece instanceof Rook) {
            ((Rook) piece).hasMoved = true;
        }

        checkPawnPromotion(piece, targetY);
        resetEnPassantFlags(piece.getColour());

        // Check for game end
        PieceColour opponentColour = (piece.getColour() == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
        if (isCheckmate(opponentColour)) {
            gameOver = true;
            gameResult = (piece.getColour() == PieceColour.WHITE) ? "White wins by checkmate!" : "Black wins by checkmate!";
        } else if (isStalemate(opponentColour)) {
            gameOver = true;
            gameResult = "Draw by stalemate!";
        }
    }

    // Check if king is in check
    public boolean isKingInCheck(PieceColour kingColour) {
        King king = null;
        for (Piece piece : pieces) {
            if (piece instanceof King && piece.getColour() == kingColour && piece.getX() < 1000) {
                king = (King) piece;
                break;
            }
        }
        if (king == null) return false;

        for (Piece piece : pieces) {
            if (piece.getColour() != kingColour && piece.getX() < 1000) {
                if (piece.isValidMove(king.getX(), king.getY(), this)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check for checkmate
    public boolean isCheckmate(PieceColour colour) {
        if (!isKingInCheck(colour)) return false;

        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);
        return legalMoves.size() == 0;
    }

    // Check for stalemate
    public boolean isStalemate(PieceColour colour) {
        if (isKingInCheck(colour)) return false;

        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);
        return legalMoves.size() == 0;
    }

    // Reset en passant flags
    private void resetEnPassantFlags(PieceColour colour) {
        for (Piece piece : pieces) {
            if (piece instanceof Pawn && piece.getColour() != colour) {
                ((Pawn) piece).justMovedTwoSquares = false;
            }
        }
    }

    // pawn promotion
    private void checkPawnPromotion(Piece piece, float nextY) {
        if (piece instanceof Pawn) {
            int row = (int)((nextY - boardY - borderOffsetY) / squareSize);

            if (piece.getColour() == PieceColour.WHITE && row == 7) {
                promotePawnToQueen(piece);
            }
            else if (piece.getColour() == PieceColour.BLACK && row == 0) {
                promotePawnToQueen(piece);
            }
        }
    }

    private void promotePawnToQueen(Piece pawn) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == pawn) {
                Texture queenTexture;
                if (pawn.getColour() == PieceColour.WHITE) {
                    queenTexture = whiteQueenTex;
                } else {
                    queenTexture = blackQueenTex;
                }
                Queen newQueen = new Queen(pawn.getX(), pawn.getY(), pawn.getWidth(), pawn.getHeight(), pawn.getColour(), queenTexture);
                pieces[i] = newQueen;
                break;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(boardTexture, boardX, boardY);
        for (Piece piece : pieces) {
            if (piece.getX() < 1000) {
                piece.draw(batch);
            }
        }
    }

    // Helper class to store al  validated moves
    public class Move {
        public Piece piece;
        public float targetX;
        public float targetY;
        public Piece capturedPiece;

        public Move(Piece piece, float targetX, float targetY, Piece capturedPiece) {
            this.piece = piece;
            this.targetX = targetX;
            this.targetY = targetY;
            this.capturedPiece = capturedPiece;
        }
    }
}
