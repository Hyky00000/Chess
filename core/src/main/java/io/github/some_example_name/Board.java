package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
    public float width = 480;
    public float height = 480;
    private Texture boardTexture;
    public Piece[] pieces;
    public float squareSize = 49.5f;
    public float borderOffsetX = 40;
    public float borderOffsetY = 39.5f;
    public float boardX = 0f;
    public float boardY = 0f;
    private Texture whiteQueenTex;
    private Texture blackQueenTex;
    private Texture whiteRookTex;
    private Texture blackRookTex;
    private Texture whiteBishopTex;
    private Texture blackBishopTex;
    private Texture whiteKnightTex;
    private Texture blackKnightTex;
    public boolean gameOver = false;
    public String gameResult = "";
    int whitePCaptured;
    int blackPCaptured;

    public Piece promotingPawn = null;
    public boolean whiteTurn = true;
    private Piece lastMovedPiece = null;

    public Piece[] capturedWhitePieces = new Piece[16];
    public Piece[] capturedBlackPieces = new Piece[16];
    public int whiteCapturedCount = 0;
    public int blackCapturedCount = 0;

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

    public void ResetGame(){
        float pieceOffsetX = boardX + borderOffsetX;
        float pieceOffsetY = boardY + borderOffsetY;
        float pieceMiddle = (squareSize - 56) / 2f;
        float px = 0;
        float py = 0;
        for (int i = 0; i < 8; i++) {
            pieces[i].setX(pieceOffsetX + i * squareSize + px);
            pieces[i].setY(pieceOffsetY + 0 * squareSize + py);
        }
        for (int i = 0; i < 8; i++) {
            pieces[8 + i].setX(pieceOffsetX + i * squareSize + px);
            pieces[8 + i].setY(pieceOffsetY + 1 * squareSize + py);
        }
        for (int i = 16; i < 24; i++){
            pieces[i].setX(pieceOffsetX + (i - 16) * squareSize + px);
            pieces[i].setY(pieceOffsetY + 7 * squareSize + py);
        }
        for (int i = 0; i < 8; i++) {
            pieces[24 + i].setX(pieceOffsetX + i * squareSize + px);
            pieces[24 + i].setY(pieceOffsetY + 6 * squareSize + py);
        }
        for (int i = 0; i < capturedWhitePieces.length; i++){
            if (capturedWhitePieces[i] != null){
                capturedWhitePieces[i].setX(1000);
            }
            if(capturedBlackPieces[i] != null){
                capturedBlackPieces[i].setX(1000);
            }
        }
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public boolean tryMove(Piece piece, float targetX, float targetY, boolean whiteTurn) {
        if (gameOver) return false;
        if (promotingPawn != null) return false;
        this.whiteTurn = whiteTurn;

        if (whiteTurn == true) {
            if (piece.getColour() != PieceColour.WHITE) {
                return false;
            }
        } else {
            if (piece.getColour() != PieceColour.BLACK) {
                return false;
            }
        }

        int targetCol = (int)((targetX - boardX - borderOffsetX) / squareSize);
        int targetRow = (int)((targetY - boardY - borderOffsetY) / squareSize);

        float snapX = boardX + borderOffsetX + targetCol * squareSize;
        float snapY = boardY + borderOffsetY + targetRow * squareSize;

        if (piece.isValidMove(snapX, snapY, this)) {

            float oldX = piece.getX();
            float oldY = piece.getY();
            Piece capturedPiece = null;

            // check if a piece is being captured so it can be put back if the move turns out to be illegal
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i] != piece && pieces[i].getX() < 1000) {
                    int pieceCol = (int)((pieces[i].getX() - boardX - borderOffsetX) / squareSize);
                    int pieceRow = (int)((pieces[i].getY() - boardY - borderOffsetY) / squareSize);
                    if (pieceCol == targetCol && pieceRow == targetRow) {
                        capturedPiece = pieces[i];
                        break;
                    }
                }
            }

            if (piece instanceof Pawn && capturedPiece == null) {
                int currentCol = (int) ((oldX - boardX - borderOffsetX) / squareSize);
                int currentRow = (int) ((oldY - boardY - borderOffsetY) / squareSize);

                int colDiff = targetCol - currentCol;
                int rowDiff = targetRow - currentRow;


                if (piece.getColour() == PieceColour.WHITE) {
                    if (rowDiff == 1 && (colDiff == 1 || colDiff == -1)) {
                        int enemyRow = targetRow - 1;
                        for (Piece p : pieces) {
                            if (p != piece && p.getX() < 1000 && p instanceof Pawn) {
                                int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                                int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);
                                if (pCol == targetCol && pRow == enemyRow) {
                                    if (p.getColour() == PieceColour.BLACK) {
                                        Pawn enemyPawn = (Pawn) p;
                                        if (enemyPawn.justMovedTwoSquares == true) {
                                            capturedPiece = p;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (piece.getColour() == PieceColour.BLACK) {
                    if (rowDiff == -1 && (colDiff == 1 || colDiff == -1)) {
                        int enemyRow = targetRow + 1;
                        for (Piece p : pieces) {
                            if (p != piece && p.getX() < 1000 && p instanceof Pawn) {
                                int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                                int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);
                                if (pCol == targetCol && pRow == enemyRow) {
                                    if (p.getColour() == PieceColour.WHITE) {
                                        Pawn enemyPawn = (Pawn) p;
                                        if (enemyPawn.justMovedTwoSquares == true) {
                                            capturedPiece = p;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            piece.setX(snapX);
            piece.setY(snapY);
            lastMovedPiece = piece;
            if (capturedPiece != null) {
                capturedPiece.setX(1000);
                if (capturedPiece != null) {
                    capturedPiece.setX(1000);


                    if (capturedPiece.getColour() == PieceColour.WHITE) {
                        capturedWhitePieces[whiteCapturedCount] = capturedPiece;
                        whiteCapturedCount = whiteCapturedCount + 1;

                        for (int i = 0; i < whiteCapturedCount - 1; i++) {
                            for (int j = 0; j < whiteCapturedCount - i - 1; j++) {
                                if (getPieceValueSimple(capturedWhitePieces[j]) < getPieceValueSimple(capturedWhitePieces[j + 1])) {

                                    Piece temp = capturedWhitePieces[j];
                                    capturedWhitePieces[j] = capturedWhitePieces[j + 1];
                                    capturedWhitePieces[j + 1] = temp;
                                }
                            }
                        }
                    } else {

                        capturedBlackPieces[blackCapturedCount] = capturedPiece;
                        blackCapturedCount = blackCapturedCount + 1;


                        for (int i = 0; i < blackCapturedCount - 1; i++) {
                            for (int j = 0; j < blackCapturedCount - i - 1; j++) {
                                if (getPieceValueSimple(capturedBlackPieces[j]) < getPieceValueSimple(capturedBlackPieces[j + 1])) {

                                    Piece temp = capturedBlackPieces[j];
                                    capturedBlackPieces[j] = capturedBlackPieces[j + 1];
                                    capturedBlackPieces[j + 1] = temp;
                                }
                            }
                        }
                    }
                }
                if (capturedPiece.getColour() == PieceColour.WHITE){
                    whitePCaptured += 1;
                } else if (capturedPiece.getColour() == PieceColour.BLACK) {
                    blackPCaptured += 1;
                }
            }


            // if castling then remember that the rook moved
            if (piece instanceof King) {
                handleCastling((King) piece, oldX, snapX);
            }

            if (piece instanceof Rook) {
                ((Rook) piece).hasMoved = true;
            }

            boolean inCheck = isKingInCheck(piece.getColour());
            if (!inCheck) {

                checkPawnPromotion(piece, snapY);
                resetEnPassantFlags(piece.getColour());

                // checkmate stalemate
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


    public int getPieceValueSimple(Piece piece) {
        if (piece instanceof Pawn) {
            return 1;
        }
        if (piece instanceof Knight || piece instanceof Bishop) {
            return 3;
        }
        if (piece instanceof Rook) {
            return 5;
        }
        if (piece instanceof Queen) {
            return 9;
        }
        return 0;
    }

    public void drawCapturedPieces(SpriteBatch batch) {
        float startX = 485;
        float pieceSize = 18;
        float gap = 1;

        float blackY = 140;
        float blackX = startX;
        int blackOnThisLine = 0;

        for (int i = 0; i < blackCapturedCount; i++) {
            if (capturedBlackPieces[i] != null) {
                batch.draw(capturedBlackPieces[i].getTexture(), blackX, blackY, pieceSize, pieceSize);
                blackX = blackX + pieceSize + gap;
                blackOnThisLine = blackOnThisLine + 1;

                if (blackOnThisLine >= 7) {
                    blackX = startX;
                    blackY = blackY - pieceSize - 5;  // Move UP for next line
                    blackOnThisLine = 0;
                }
            }
        }

        float whiteY = 320;
        float whiteX = startX;
        int whiteOnThisLine = 0;

        for (int i = 0; i < whiteCapturedCount; i++) {
            if (capturedWhitePieces[i] != null) {
                batch.draw(capturedWhitePieces[i].getTexture(), whiteX, whiteY, pieceSize, pieceSize);
                whiteX = whiteX + pieceSize + gap;
                whiteOnThisLine = whiteOnThisLine + 1;

                if (whiteOnThisLine >= 7) {
                    whiteX = startX;
                    whiteY = whiteY - pieceSize - 5;  // Move UP for next line
                    whiteOnThisLine = 0;
                }
            }
        }
    }


    // Make a move for ai as ai already chooses from array of legal moves
    public void makeMove(Move move) {
        Piece piece = move.piece;
        float targetX = move.targetX;
        float targetY = move.targetY;
        Piece capturedPiece = move.capturedPiece;

        piece.setX(targetX);
        piece.setY(targetY);

        if (piece instanceof Pawn) {
            int row = (int)((targetY - boardY - borderOffsetY) / squareSize);
            if (piece.getColour() == PieceColour.WHITE && row == 7) {
                promotePawn(piece, 0, whiteQueenTex, whiteRookTex, whiteBishopTex, whiteKnightTex);
            }
            else if (piece.getColour() == PieceColour.BLACK && row == 0) {
                promotePawn(piece, 0, blackQueenTex, blackRookTex, blackBishopTex, blackKnightTex);
            }
        }


        if (capturedPiece != null) {
            capturedPiece.setX(1000);

            if (capturedPiece.getColour() == PieceColour.WHITE) {
                capturedWhitePieces[whiteCapturedCount] = capturedPiece;
                whiteCapturedCount = whiteCapturedCount + 1;

                for (int i = 0; i < whiteCapturedCount - 1; i++) {
                    for (int j = 0; j < whiteCapturedCount - i - 1; j++) {
                        if (getPieceValueSimple(capturedWhitePieces[j]) < getPieceValueSimple(capturedWhitePieces[j + 1])) {

                            Piece temp = capturedWhitePieces[j];
                            capturedWhitePieces[j] = capturedWhitePieces[j + 1];
                            capturedWhitePieces[j + 1] = temp;
                        }
                    }
                }
            } else {
                capturedBlackPieces[blackCapturedCount] = capturedPiece;
                blackCapturedCount = blackCapturedCount + 1;

                for (int i = 0; i < blackCapturedCount - 1; i++) {
                    for (int j = 0; j < blackCapturedCount - i - 1; j++) {
                        if (getPieceValueSimple(capturedBlackPieces[j]) < getPieceValueSimple(capturedBlackPieces[j + 1])) {

                            Piece temp = capturedBlackPieces[j];
                            capturedBlackPieces[j] = capturedBlackPieces[j + 1];
                            capturedBlackPieces[j + 1] = temp;
                        }
                    }
                }
            }

            if (capturedPiece.getColour() == PieceColour.WHITE){
                whitePCaptured += 1;
            } else if (capturedPiece.getColour() == PieceColour.BLACK) {
                blackPCaptured += 1;
            }
        }

        if (piece instanceof King) {
            float oldX = piece.getX() - (targetX - piece.getX());
            handleCastling((King) piece, oldX, targetX);
        }

        if (piece instanceof Rook) {
            ((Rook) piece).hasMoved = true;
        }

        resetEnPassantFlags(piece.getColour());

        PieceColour opponentColour;
        if (piece.getColour() == PieceColour.WHITE) {
            opponentColour = PieceColour.BLACK;
        } else {
            opponentColour = PieceColour.WHITE;
        }

        if (isCheckmate(opponentColour)) {
            gameOver = true;
            if (piece.getColour() == PieceColour.WHITE) {
                gameResult = "White wins by checkmate!";
            } else {
                gameResult = "Black wins by checkmate!";
            }
        } else if (isStalemate(opponentColour)) {
            gameOver = true;
            gameResult = "Draw by stalemate!";
        }
    }


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

    public boolean isKingInCheck(PieceColour kingColour) {
        King king = null;
        for (Piece piece : pieces) {
            if (piece instanceof King && piece.getColour() == kingColour && piece.getX() < 1000) {
                king = (King) piece;
                break;
            }
        }
        if (king == null) {
            return false;
        }

        for (Piece piece : pieces) {
            if (piece.getColour() != kingColour && piece.getX() < 1000) {
                if (piece.isValidMove(king.getX(), king.getY(), this)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(PieceColour colour) {
        if (!isKingInCheck(colour)) return false;

        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);
        return legalMoves.size() == 0;
    }

    public boolean isStalemate(PieceColour colour) {
        if (isKingInCheck(colour)) return false;

        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);
        return legalMoves.size() == 0;
    }

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

    // Reset en passant flags
    private void resetEnPassantFlags(PieceColour colour) {
        for (Piece piece : pieces) {
            if (piece instanceof Pawn) {
                Pawn pawn = (Pawn) piece;
                pawn.wasLastMove = false;
            }
        }

        if (lastMovedPiece instanceof Pawn) {
            Pawn lastPawn = (Pawn) lastMovedPiece;
            if (lastPawn.justMovedTwoSquares == true) {
                lastPawn.wasLastMove = true;
            }
        }

        for (Piece piece : pieces) {
            if (piece instanceof Pawn) {
                Pawn pawn = (Pawn) piece;
                if (pawn.getColour() != colour) {
                    pawn.justMovedTwoSquares = false;
                }
            }
        }
    }

    // pawn promotion
    private void checkPawnPromotion(Piece piece, float nextY) {
        if (piece instanceof Pawn) {
            int row = (int)((nextY - boardY - borderOffsetY) / squareSize);

            if (piece.getColour() == PieceColour.WHITE && row == 7) {
                promotingPawn = piece;

            }
            else if (piece.getColour() == PieceColour.BLACK && row == 0) {
                promotingPawn = piece;
            }
        }
    }

    public void promotePawn(Piece pawn, int choice, Texture queenTex, Texture rookTex, Texture bishopTex, Texture knightTex) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == pawn) {
                Texture newTexture;
                Piece newPiece;
                float x = pawn.getX();
                float y = pawn.getY();
                float width = pawn.getWidth();
                float height = pawn.getHeight();
                PieceColour colour = pawn.getColour();

                // choice 0 is Queen, 1 is Rook, 2 is Bishop, 3 is Knight
                if (choice == 0) {
                    // Queen
                    newTexture = queenTex;
                    newPiece = new Queen(x, y, width, height, colour, newTexture);
                }
                else if (choice == 1) {
                    // Rook
                    newTexture = rookTex;
                    newPiece = new Rook(x, y, width, height, colour, newTexture);
                }
                else if (choice == 2) {
                    // Bishop
                    newTexture = bishopTex;
                    newPiece = new Bishop(x, y, width, height, colour, newTexture);
                }
                else if (choice == 3) {
                    // Knight
                    newTexture = knightTex;
                    newPiece = new Knight(x, y, width, height, colour, newTexture);
                }
                else {
                    // Queen just incase
                    newTexture = queenTex;
                    newPiece = new Queen(x, y, width, height, colour, newTexture);
                }

                pieces[i] = newPiece;
                promotingPawn = null;
                break;
            }
        }
    }

    private boolean attacksOpponentQueen(Piece piece, float targetX, float targetY, PieceColour aiColour) {
        float oldX = piece.getX();
        float oldY = piece.getY();

        piece.setX(targetX);
        piece.setY(targetY);

        boolean attacksQueen = false;
        for (Piece opponentPiece : pieces) {
            if (opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000 && opponentPiece instanceof Queen) {
                if (piece.isValidMove(opponentPiece.getX(), opponentPiece.getY(), this)) {
                    attacksQueen = true;
                    break;
                }
            }
        }

        piece.setX(oldX);
        piece.setY(oldY);
        return attacksQueen;
    }

    // NEW: Check if pawn is passed (no enemy pawns in front)
    private boolean isPassedPawn(Piece pawn, float targetX, float targetY, PieceColour aiColour) {
        int col = (int)((targetX - boardX - borderOffsetX) / squareSize);
        int row = (int)((targetY - boardY - borderOffsetY) / squareSize);

        PieceColour enemyColour = (aiColour == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;

        // Check adjacent columns for enemy pawns
        for (int c = col - 1; c <= col + 1; c++) {
            if (c >= 0 && c < 8) {
                for (Piece p : pieces) {
                    if (p.getColour() == enemyColour && p instanceof Pawn && p.getX() < 1000) {
                        int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                        int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);

                        if (pCol == c) {
                            if (aiColour == PieceColour.WHITE && pRow > row) {
                                return false;
                            }
                            if (aiColour == PieceColour.BLACK && pRow < row) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }



    // from here on is for AI level 2 and 3 ofcofc
    public int evaluateMove(Move move, PieceColour aiColour) {
        int score = 0;  // Each move will start with score 0
        Piece piece = move.piece;

        // method numero 1 Check if move captures a piece and ADDDD points of captured piece value
        if (move.capturedPiece != null) {
            // Get the value of the captured piece ANDDDDD multiply by 20 to make it important
            int pieceValue = getPieceValue(move.capturedPiece);
            score = score + (pieceValue * 20);

            // NEW: Extra bonus for capturing with less valuable piece
            int ourPieceValue = getPieceValue(piece);
            if (ourPieceValue < pieceValue) {
                score = score + (pieceValue - ourPieceValue) * 15;
            }
        }

        boolean leavesHanging = leavesPieceHanging(piece, move.targetX, move.targetY, aiColour);
        if (leavesHanging) {
            int ourPieceValue = getPieceValue(piece);
            score -= (ourPieceValue * 100);
            boolean isDefended = isSquareDefended(move.targetX, move.targetY, aiColour);
            if (!isDefended) {
                score -= (ourPieceValue * 50);
            }
        }

        if (move.capturedPiece != null) {
            int ourPieceValue = getPieceValue(piece);
            int capturedValue = getPieceValue(move.capturedPiece);

            // Simulate the capture
            float oldX = piece.getX();
            float oldY = piece.getY();
            piece.setX(move.targetX);
            piece.setY(move.targetY);

            // Check if any opponent piece can capture our piece now
            boolean canBeRecaptured = false;
            int smallestRecapturerValue = 100;
            for (Piece opponentPiece : pieces) {
                if (opponentPiece != move.capturedPiece && opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                    if (opponentPiece.isValidMove(move.targetX, move.targetY, this)) {
                        canBeRecaptured = true;
                        int oppValue = getPieceValue(opponentPiece);
                        if (oppValue < smallestRecapturerValue) {
                            smallestRecapturerValue = oppValue;
                        }
                    }
                }
            }

            // Restore position
            piece.setX(oldX);
            piece.setY(oldY);

            if (canBeRecaptured) {
                if (ourPieceValue > smallestRecapturerValue) {
                    score -= (ourPieceValue - smallestRecapturerValue) * 200;
                } else if (ourPieceValue == smallestRecapturerValue) {
                    score -= 50;
                }
            }
        }





        // NEW: Check if move gives check
        float oldX = piece.getX();
        float oldY = piece.getY();
        piece.setX(move.targetX);
        piece.setY(move.targetY);
        Piece tempCaptured = null;
        if (move.capturedPiece != null) {
            tempCaptured = move.capturedPiece;
            tempCaptured.setX(1000);
        }

        PieceColour opponentColour = (aiColour == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
        if (isKingInCheck(opponentColour)) {
            score = score + 30;
        }

        // Restore position
        piece.setX(oldX);
        piece.setY(oldY);
        if (tempCaptured != null) {
            tempCaptured.setX(move.targetX);
        }

        // Check if the destination square is attacked by opponent
        boolean squareIsAttacked = isSquareAttacked(move.targetX, move.targetY, aiColour);
        if (squareIsAttacked) {
            int attackerValue = findSmallestAttackerValue(move.targetX, move.targetY, aiColour);
            int ourPieceValue = getPieceValue(piece);

            if (ourPieceValue > attackerValue) {
                score -= (ourPieceValue - attackerValue) * 30;
            } else if (ourPieceValue == attackerValue) {
                score -= 5;
            }
        }

        // NEW: Check if move attacks opponent's queen
        boolean attacksQueen = attacksOpponentQueen(piece, move.targetX, move.targetY, aiColour);
        if (attacksQueen) {
            score = score + 25;
        }

        // Check if move creates a threat (attacks undefended piece)
        boolean createsThreat = createsNewThreat(piece, move.targetX, move.targetY, aiColour);
        if (createsThreat) {
            score += 15;
        }

        // NEW: Penalty for moving piece to edge of board
        int col = (int)((move.targetX - boardX - borderOffsetX) / squareSize);
        int row = (int)((move.targetY - boardY - borderOffsetY) / squareSize);
        if (col == 0 || col == 7 || row == 0 || row == 7) {
            score = score - 2;
        }

        // Check if it's early game for development that extra bonuses :)_
        boolean earlyGame = isEarlyGame();
        if (earlyGame) {
            if (piece.getX() < 1000) {
                int currentCol = (int)((piece.getX() - boardX - borderOffsetX) / squareSize);
                int currentRow = (int)((piece.getY() - boardY - borderOffsetY) / squareSize);
                int newCol = (int)((move.targetX - boardX - borderOffsetX) / squareSize);
                int newRow = (int)((move.targetY - boardY - borderOffsetY) / squareSize);

                if (newCol >= 2 && newCol <= 5 && newRow >= 2 && newRow <= 5) {
                    score = score + 3;
                }

                if ((piece instanceof Knight || piece instanceof Bishop) && currentRow <= 1) {
                    score = score + 2;
                }
            }
        }

        // Penalty for moving king stupidly INNN the early game
        if (piece instanceof King) {
            King kingPiece = (King) piece;
            if (!kingPiece.hasMoved && earlyGame) {
                score = score - 2;
            }
        }

        // Bonus for piece activity basically more squares controlled silly
        int activityBonus = calculateActivityBonus(piece, move.targetX, move.targetY);
        score = score + activityBonus;

        //  Special bonus for pawn moves that advance past first 3 rows or something
        if (piece instanceof Pawn) {
            row = (int)((move.targetY - boardY - borderOffsetY) / squareSize);
            if (aiColour == PieceColour.WHITE && row > 3) {
                score = score + 1;
            }
            if (aiColour == PieceColour.BLACK && row < 4) {
                score = score + 1;
            }

            // NEW: Bonus for passed pawns
            if (isPassedPawn(piece, move.targetX, move.targetY, aiColour)) {
                score = score + 10;
            }
        }
        if (squareIsAttacked && !isSquareDefended(move.targetX, move.targetY, aiColour)) {
            score = score - 1000;
        }
        return score;
    }

    //Get the value of a piece for scoring
    private int getPieceValue(Piece piece) {
        if (piece instanceof Pawn) {
            return 1;
        }
        if (piece instanceof Knight) {
            return 3;
        }
        if (piece instanceof Bishop) {
            return 3;
        }
        if (piece instanceof Rook) {
            return 5;
        }
        if (piece instanceof Queen) {
            return 9;
        }
        if (piece instanceof King) {
            return 100;
        }
        return 0;
    }

    //Check if it's early game for the pawn and bishop and knight movess
    private boolean isEarlyGame() {
        int capturedPieces = 0;
        // Count how many pieces have been captured basically by checking the pieces off the board BUT remember if I wanna queue the pieces in a mini queue
        for (Piece piece : pieces) {
            if (piece.getX() >= 1000) {
                capturedPieces = capturedPieces + 1;
            }
        }
        // If there's less than 10 pieces captured then it's still early game
        return capturedPieces < 10;
    }

    //Check if move leaves piece undefended
    private boolean leavesPieceHanging(Piece piece, float targetX, float targetY, PieceColour aiColour) {

        boolean isDefended = isSquareDefended(targetX, targetY, aiColour);
        if (isDefended) {
            return false;
        }

        float oldX = piece.getX();
        float oldY = piece.getY();
        piece.setX(targetX);
        piece.setY(targetY);

        boolean canBeCaptured = false;
        for (Piece opponentPiece : pieces) {
            if (opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                if (opponentPiece.isValidMove(targetX, targetY, this)) {
                    canBeCaptured = true;
                    break;
                }
            }
        }

        piece.setX(oldX);
        piece.setY(oldY);

        return canBeCaptured;
    }

    // Calculate bonus for piece activity basically the amount of space
    private int calculateActivityBonus(Piece piece, float targetX, float targetY) {
        int bonus = 0;
        int col = (int)((targetX - boardX - borderOffsetX) / squareSize);
        int row = (int)((targetY - boardY - borderOffsetY) / squareSize);

        // Bonus for controlling center squares
        if (col >= 3 && col <= 4 && row >= 3 && row <= 4) {
            bonus = bonus + 2;  // Add 2 points for controlling the centre
        }

        // bonus for rooks on open files
        if (piece instanceof Rook) {
            if (col == 0 || col == 7) {
                bonus = bonus + 1;  // Add 1 point for controlling edge files
            }
        }

        return bonus;
    }

    // Get all legal moves sorted by quality finallyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy for AI done by getting all the legal moves mhm then making a list to give every move their scores then when they are sorted, they are finally moved onto this new array to be used by the ai
    public java.util.ArrayList<Move> getSortedLegalMoves(PieceColour colour) {

        // First get all the legal moves
        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);

        // If no moves available then return empty list and opponent won
        if (legalMoves.size() == 0) {
            return legalMoves;
        }

        // Create yet ANOTHER list to store moves with their scores
        java.util.ArrayList<MoveWithScore> movesWithScores = new java.util.ArrayList<MoveWithScore>();

        // Calculate score for each move
        for (int i = 0; i < legalMoves.size(); i++) {
            Move move = legalMoves.get(i);
            int score = evaluateMove(move, colour);
            movesWithScores.add(new MoveWithScore(move, score));
        }

        // BUBLE SORT so remember to talk about this in the analysis
        for (int i = 0; i < movesWithScores.size() - 1; i++) {
            for (int j = 0; j < movesWithScores.size() - i - 1; j++) {
                MoveWithScore first = movesWithScores.get(j);
                MoveWithScore second = movesWithScores.get(j + 1);

                // If the first score is less than second score swap them of course
                if (first.score < second.score) {
                    movesWithScores.set(j, second);
                    movesWithScores.set(j + 1, first);
                }
            }
        }

        // Shuffle top moves a bit so AI doesn't always play same moves
        if (movesWithScores.size() >= 5) {
            for (int i = 0; i < 3; i++) {
                int index1 = i;
                int index2 = i + 1 + (int)(Math.random() * 2);
                if (index2 < 5) {
                    MoveWithScore temp = movesWithScores.get(index1);
                    movesWithScores.set(index1, movesWithScores.get(index2));
                    movesWithScores.set(index2, temp);
                }
            }
        }

        // Create the final third new list with just the sorted moves
        java.util.ArrayList<Move> sortedMoves = new java.util.ArrayList<Move>();
        for (int i = 0; i < movesWithScores.size(); i++) {
            sortedMoves.add(movesWithScores.get(i).move);
        }

        return sortedMoves;
    }

    // Check if a square is defended by own pieces (REALLY check)
    private boolean isSquareDefended(float squareX, float squareY, PieceColour defenderColour) {
        int defendedCount = 0;

        // First, count how many pieces are defending
        for (Piece piece : pieces) {
            if (piece.getColour() == defenderColour && piece.getX() < 1000) {
                if (piece.isValidMove(squareX, squareY, this)) {
                    defendedCount = defendedCount + 1;
                }
            }
        }

        // Need at least 2 defenders to be safe, or 1 defender if it's a minor piece
        if (defendedCount >= 2) {
            return true;
        }

        // If only 1 defender, check what type of piece it is
        if (defendedCount == 1) {
            for (Piece piece : pieces) {
                if (piece.getColour() == defenderColour && piece.getX() < 1000) {
                    if (piece.isValidMove(squareX, squareY, this)) {
                        // Pawns and knights are good defenders
                        if (piece instanceof Pawn || piece instanceof Knight) {
                            return true;
                        }
                        // For bishops/rooks/queens, need to check if they're pinned
                        return !isPiecePinned(piece, squareX, squareY, defenderColour);
                    }
                }
            }
        }
        return false;
    }


    private boolean isPiecePinned(Piece piece, float targetX, float targetY, PieceColour defenderColour) {
        float oldX = piece.getX();
        float oldY = piece.getY();

        piece.setX(targetX);
        piece.setY(targetY);

        boolean kingInCheck = isKingInCheck(defenderColour);

        piece.setX(oldX);
        piece.setY(oldY);

        return kingInCheck;
    }

    // Check if move creates a new threat (attacks undefended opponent piece)
    private boolean createsNewThreat(Piece piece, float targetX, float targetY, PieceColour aiColour) {
        // Save original position
        float oldX = piece.getX();
        float oldY = piece.getY();

        // Temporarily move the piece
        piece.setX(targetX);
        piece.setY(targetY);

        boolean createsThreat = false;

        // Check if we now attack any opponent pieces that aren't defended
        for (Piece opponentPiece : pieces) {
            if (opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                if (piece.isValidMove(opponentPiece.getX(), opponentPiece.getY(), this)) {
                    // Check if this opponent piece is defended
                    boolean isDefended = isSquareDefended(opponentPiece.getX(), opponentPiece.getY(), opponentPiece.getColour());
                    if (!isDefended) {
                        createsThreat = true;
                        break;
                    }
                }
            }
        }

        // Restore position
        piece.setX(oldX);
        piece.setY(oldY);

        return createsThreat;
    }

    // Check if the destination square is attacked by opponent
    private boolean isSquareAttacked(float squareX, float squareY, PieceColour defenderColour) {
        for (Piece piece : pieces) {
            if (piece.getColour() != defenderColour && piece.getX() < 1000) {
                if (piece.isValidMove(squareX, squareY, this)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Find the value of the smallest piece attacking a square
    private int findSmallestAttackerValue(float squareX, float squareY, PieceColour defenderColour) {
        int smallestValue = 100; // Start high
        for (Piece piece : pieces) {
            if (piece.getColour() != defenderColour && piece.getX() < 1000) {
                if (piece.isValidMove(squareX, squareY, this)) {
                    int value = getPieceValue(piece);
                    if (value < smallestValue) {
                        smallestValue = value;
                    }
                }
            }
        }
        return smallestValue;
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

    // NEW HELPER CLASS toooo be used to Store a move with its score
    private class MoveWithScore {
        public Move move;
        public int score;

        public MoveWithScore(Move move, int score) {
            this.move = move;
            this.score = score;
        }
    }
}
