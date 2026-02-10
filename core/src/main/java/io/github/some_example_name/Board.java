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
    private Texture whiteKingTex;
    private Texture whitePawnTex;
    private Texture blackKingTex;
    private Texture blackPawnTex;
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
        this.whiteRookTex = whiteRookTex;
        this.blackRookTex = blackRookTex;
        this.whiteBishopTex = whiteBishopTex;
        this.blackBishopTex = blackBishopTex;
        this.whiteKnightTex = whiteKnightTex;
        this.blackKnightTex = blackKnightTex;
        this.whiteKingTex = whiteKingTex;
        this.blackKingTex = blackKingTex;
        this.whitePawnTex = whitePawnTex;
        this.blackPawnTex = blackPawnTex;
        this.pieces = new Piece[32];

        float pieceOffsetX = boardX + borderOffsetX;
        float pieceOffsetY = boardY + borderOffsetY;

        float px = 0;
        float py = 0;

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

    public void ResetGame() {
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = null;
        }
        float pieceOffsetX = boardX + borderOffsetX;
        float pieceOffsetY = boardY + borderOffsetY;
        float px = 0;
        float py = 0;

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

        for (int i = 0; i < capturedWhitePieces.length; i++) {
            capturedWhitePieces[i] = null;
        }
        for (int i = 0; i < capturedBlackPieces.length; i++) {
            capturedBlackPieces[i] = null;
        }
        whiteCapturedCount = 0;
        blackCapturedCount = 0;
        gameOver = false;
        gameResult = "";
        promotingPawn = null;
        whiteTurn = true;
        lastMovedPiece = null;
        whitePCaptured = 0;
        blackPCaptured = 0;

        for (Piece piece : pieces) {
            if (piece instanceof Pawn) {
                Pawn pawn = (Pawn) piece;
                pawn.justMovedTwoSquares = false;
                pawn.wasLastMove = false;
            }
            if (piece instanceof Rook) {
                ((Rook) piece).hasMoved = false;
            }
            if (piece instanceof King) {
                ((King) piece).hasMoved = false;
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

            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i] != null && pieces[i] != piece && pieces[i].getX() < 1000) {
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
                            if (p != null && p != piece && p.getX() < 1000 && p instanceof Pawn) {
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
                            if (p != null && p != piece && p.getX() < 1000 && p instanceof Pawn) {
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
                King king = (King) piece;
                if (!king.hasMoved) {
                    handleCastling(king, oldX, snapX);
                }
                king.hasMoved = true;
            }

            if (piece instanceof Rook) {
                ((Rook) piece).hasMoved = true;
            }

            boolean inCheck = isKingInCheck(piece.getColour());
            if (!inCheck) {

                checkPawnPromotion(piece, snapY);
                resetEnPassantFlags(piece.getColour());

                PieceColour opponentColour = (piece.getColour() == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
                if (isCheckmate(opponentColour)) {
                    gameOver = true;
                } else if (isStalemate(opponentColour)) {
                    gameOver = true;
                }
                if (hasInsufficientMaterial()) {
                    gameOver = true;
                }
                return true;
            } else {
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
                    blackY = blackY - pieceSize - 5;
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
                    whiteY = whiteY - pieceSize - 5;
                    whiteOnThisLine = 0;
                }
            }
        }
    }

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
        } else if (isStalemate(opponentColour)) {
            gameOver = true;
        }
        if (hasInsufficientMaterial()) {
            gameOver = true;
        }
    }

    public boolean hasInsufficientMaterial() {
        int whitePieceCount = 0;
        int blackPieceCount = 0;
        int whiteBishops = 0;
        int blackBishops = 0;
        int whiteKnights = 0;
        int blackKnights = 0;
        boolean whiteHasOtherPieces = false;
        boolean blackHasOtherPieces = false;

        for (Piece piece : pieces) {
            if (piece != null && piece.getX() < 1000) {
                if (piece.getColour() == PieceColour.WHITE) {
                    whitePieceCount++;
                    if (piece instanceof Bishop) whiteBishops++;
                    else if (piece instanceof Knight) whiteKnights++;
                    else if (!(piece instanceof King)) whiteHasOtherPieces = true;
                } else {
                    blackPieceCount++;
                    if (piece instanceof Bishop) blackBishops++;
                    else if (piece instanceof Knight) blackKnights++;
                    else if (!(piece instanceof King)) blackHasOtherPieces = true;
                }
            }
        }

        if (whitePieceCount == 1 && blackPieceCount == 1) {
            return true;
        }

        if (!whiteHasOtherPieces && !blackHasOtherPieces) {
            if (whitePieceCount == 2 && blackPieceCount == 1 && whiteBishops == 1) return true;
            if (blackPieceCount == 2 && whitePieceCount == 1 && blackBishops == 1) return true;

            if (whitePieceCount == 2 && blackPieceCount == 1 && whiteKnights == 1) return true;
            if (blackPieceCount == 2 && whitePieceCount == 1 && blackKnights == 1) return true;

            if (whitePieceCount == 2 && blackPieceCount == 2 &&
                whiteBishops == 1 && blackBishops == 1) return true;

            if (whitePieceCount == 2 && blackPieceCount == 2 &&
                whiteKnights == 1 && blackKnights == 1) return true;

            if (whitePieceCount == 2 && blackPieceCount == 2 &&
                ((whiteBishops == 1 && blackKnights == 1) ||
                    (whiteKnights == 1 && blackBishops == 1))) return true;
        }

        return false;
    }

    public java.util.ArrayList<Move> getAllLegalMoves(PieceColour colour) {
        java.util.ArrayList<Move> legalMoves = new java.util.ArrayList<Move>();

        for (Piece piece : pieces) {
            if (piece != null && piece.getColour() == colour && piece.getX() < 1000) {
                float originalX = piece.getX();
                float originalY = piece.getY();

                for (int col = 0; col < 8; col++) {
                    for (int row = 0; row < 8; row++) {
                        float testX = boardX + borderOffsetX + col * squareSize;
                        float testY = boardY + borderOffsetY + row * squareSize;

                        if (piece.isValidMove(testX, testY, this)) {
                            Piece capturedPiece = null;
                            for (Piece p : pieces) {
                                if (p != null && p != piece && p.getX() < 1000) {
                                    int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                                    int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);
                                    if (pCol == col && pRow == row) {
                                        capturedPiece = p;
                                        break;
                                    }
                                }
                            }

                            piece.setX(testX);
                            piece.setY(testY);
                            if (capturedPiece != null) {
                                capturedPiece.setX(1000);
                            }

                            boolean inCheck = isKingInCheck(piece.getColour());

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
            if (piece != null && piece instanceof King && piece.getColour() == kingColour && piece.getX() < 1000) {
                king = (King) piece;
                break;
            }
        }
        if (king == null) {
            return false;
        }

        for (Piece piece : pieces) {
            if (piece != null && piece.getColour() != kingColour && piece.getX() < 1000) {
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

        if (nextCol == currentCol + 2) {
            for (Piece rook : pieces) {
                if (rook != null && rook instanceof Rook && rook.getColour() == king.getColour() && rook.getX() < 1000) {
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
        else if (nextCol == currentCol - 2) {
            for (Piece rook : pieces) {
                if (rook != null && rook instanceof Rook && rook.getColour() == king.getColour() && rook.getX() < 1000) {
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
        if (piece instanceof King) {
            King king = (King) piece;
            int currentCol = (int)((oldX - boardX - borderOffsetX) / squareSize);
            int nextCol = (int)((newX - boardX - borderOffsetX) / squareSize);
            if (nextCol == currentCol + 2 || nextCol == currentCol - 2) {
                for (Piece rook : pieces) {
                    if (rook != null && rook instanceof Rook && rook.getColour() == piece.getColour()) {
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

    private void resetEnPassantFlags(PieceColour colour) {
        for (Piece piece : pieces) {
            if (piece != null && piece instanceof Pawn) {
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
            if (piece != null && piece instanceof Pawn) {
                Pawn pawn = (Pawn) piece;
                if (pawn.getColour() != colour) {
                    pawn.justMovedTwoSquares = false;
                }
            }
        }
    }

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

                if (choice == 0) {
                    newTexture = queenTex;
                    newPiece = new Queen(x, y, width, height, colour, newTexture);
                }
                else if (choice == 1) {
                    newTexture = rookTex;
                    newPiece = new Rook(x, y, width, height, colour, newTexture);
                }
                else if (choice == 2) {
                    newTexture = bishopTex;
                    newPiece = new Bishop(x, y, width, height, colour, newTexture);
                }
                else if (choice == 3) {
                    newTexture = knightTex;
                    newPiece = new Knight(x, y, width, height, colour, newTexture);
                }
                else {
                    newTexture = queenTex;
                    newPiece = new Queen(x, y, width, height, colour, newTexture);
                }

                pieces[i] = newPiece;
                promotingPawn = null;
                return;
            }
        }
        promotingPawn = null;
    }

    private boolean attacksOpponentQueen(Piece piece, float targetX, float targetY, PieceColour aiColour) {
        float oldX = piece.getX();
        float oldY = piece.getY();

        piece.setX(targetX);
        piece.setY(targetY);

        boolean attacksQueen = false;
        for (Piece opponentPiece : pieces) {
            if (opponentPiece != null && opponentPiece.getColour() != aiColour &&
                opponentPiece.getX() < 1000 && opponentPiece instanceof Queen) {
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

    private boolean isPassedPawn(Piece pawn, float targetX, float targetY, PieceColour aiColour) {
        int col = (int)((targetX - boardX - borderOffsetX) / squareSize);
        int row = (int)((targetY - boardY - borderOffsetY) / squareSize);

        PieceColour enemyColour = (aiColour == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;

        for (int c = col - 1; c <= col + 1; c++) {
            if (c >= 0 && c < 8) {
                for (Piece p : pieces) {
                    if (p != null && p.getColour() == enemyColour && p instanceof Pawn && p.getX() < 1000) {
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

    public MoveWithScore evaluateMove(Move move, PieceColour aiColour) {
        int score = 0;
        Piece piece = move.piece;
        StringBuilder details = new StringBuilder();

        float currentX = piece.getX();
        float currentY = piece.getY();
        boolean pieceCurrentlyAttacked = isSquareAttacked(currentX, currentY, aiColour);

        if (pieceCurrentlyAttacked) {
            score = score + 50000;
            details.append("piece currently attacked +50000, ");
        }

        if (move.capturedPiece != null) {
            int pieceValue = getPieceValue(move.capturedPiece);
            score = score + (pieceValue * 35);
            details.append("capture +" + (pieceValue * 35) + ", ");

            int ourPieceValue = getPieceValue(piece);
            if (ourPieceValue < pieceValue) {
                int bonus = (pieceValue - ourPieceValue) * 40;
                score = score + bonus;
                details.append("good trade +" + bonus + ", ");
            }
        }

        boolean leavesHanging = leavesPieceHanging(piece, move.targetX, move.targetY, aiColour);
        if (leavesHanging) {
            int ourPieceValue = getPieceValue(piece);
            boolean isDefended = isSquareDefended(move.targetX, move.targetY, aiColour);
            if (isDefended) {
                score = score - 100000;
                details.append("hanging piece -100000 (but defended), ");
            } else {
                score = score - 1000000;
                details.append("hanging piece -1000000, ");
            }
        }

        if (move.capturedPiece != null) {
            int ourPieceValue = getPieceValue(piece);
            int capturedValue = getPieceValue(move.capturedPiece);

            float oldX = piece.getX();
            float oldY = piece.getY();
            piece.setX(move.targetX);
            piece.setY(move.targetY);

            boolean canBeRecaptured = false;
            int smallestRecapturerValue = 100;
            for (Piece opponentPiece : pieces) {
                if (opponentPiece != null && opponentPiece != move.capturedPiece && opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                    if (opponentPiece.isValidMove(move.targetX, move.targetY, this)) {
                        canBeRecaptured = true;
                        int oppValue = getPieceValue(opponentPiece);
                        if (oppValue < smallestRecapturerValue) {
                            smallestRecapturerValue = oppValue;
                        }
                    }
                }
            }

            piece.setX(oldX);
            piece.setY(oldY);

            if (canBeRecaptured) {
                if (ourPieceValue > smallestRecapturerValue) {
                    score = score - 500000;
                    details.append("bad recapture -500000, ");
                } else if (ourPieceValue == smallestRecapturerValue) {
                    score = score - 200000;
                    details.append("equal recapture -200000, ");
                }
            }
        }

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
            score = score + 60;
            details.append("check +60, ");
        }

        piece.setX(oldX);
        piece.setY(oldY);
        if (tempCaptured != null) {
            tempCaptured.setX(move.targetX);
        }

        boolean newSquareAttacked = isSquareAttacked(move.targetX, move.targetY, aiColour);
        boolean newSquareDefended = isSquareDefended(move.targetX, move.targetY, aiColour);

        if (newSquareAttacked) {
            int attackerValue = findSmallestAttackerValue(move.targetX, move.targetY, aiColour);
            int ourPieceValue = getPieceValue(piece);

            if (ourPieceValue > attackerValue) {
                if (pieceCurrentlyAttacked) {
                    score = score - 1000;
                    details.append("bad move from attacked square -1000, ");
                } else {
                    score = score - 5000000;
                    details.append("piece to attacked square -5000000, ");
                }
            } else if (ourPieceValue == attackerValue) {
                if (pieceCurrentlyAttacked) {
                    score = score - 500;
                    details.append("equal trade from attacked square -500, ");
                } else {
                    score = score - 10000;
                    details.append("equal piece to attacked square -10000, ");
                }
            } else if (ourPieceValue < attackerValue) {
                if (pieceCurrentlyAttacked) {
                    score = score + 2000;
                    details.append("good escape +2000, ");
                }
            }
        }

        if (!newSquareAttacked && pieceCurrentlyAttacked) {
            score = score + 100000;
            details.append("escape from attack +100000, ");
        }

        boolean attacksQueen = attacksOpponentQueen(piece, move.targetX, move.targetY, aiColour);
        if (attacksQueen) {
            score = score + 80;
            details.append("attacks queen +80, ");
        }

        float oldX2 = piece.getX();
        float oldY2 = piece.getY();
        piece.setX(move.targetX);
        piece.setY(move.targetY);

        for (Piece opponentPiece : pieces) {
            if (opponentPiece != null && opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                if (piece.isValidMove(opponentPiece.getX(), opponentPiece.getY(), this)) {
                    int targetValue = getPieceValue(opponentPiece);
                    boolean isDefended = isSquareDefended(opponentPiece.getX(), opponentPiece.getY(), opponentPiece.getColour());

                    if (!isDefended) {
                        score = score + targetValue * 15;
                        details.append("attacks undefended piece +" + (targetValue * 15) + ", ");
                    } else {
                        score = score + targetValue * 5;
                        details.append("attacks defended piece +" + (targetValue * 5) + ", ");
                    }
                }
            }
        }

        piece.setX(oldX2);
        piece.setY(oldY2);

        int col = (int)((move.targetX - boardX - borderOffsetX) / squareSize);
        int row = (int)((move.targetY - boardY - borderOffsetY) / squareSize);

        if (!newSquareAttacked) {
            if ((col == 3 || col == 4) && (row == 3 || row == 4)) {
                score = score + 25;
                details.append("center control +25, ");
            }
            else if (col >= 2 && col <= 5 && row >= 2 && row <= 5) {
                score = score + 15;
                details.append("near center +15, ");
            }
            else if (col == 0 || col == 7 || row == 0 || row == 7) {
                score = score - 20;
                details.append("edge square -20, ");
            }
        }

        if (piece instanceof Knight) {
            if (col == 0 || col == 7 || row == 0 || row == 7) {
                score = score - 40;
                details.append("knight on edge -40, ");
            }
            if ((col == 0 || col == 7) && (row == 0 || row == 7)) {
                score = score - 60;
                details.append("knight in corner -60, ");
            }
            if (!newSquareAttacked && col >= 2 && col <= 5 && row >= 2 && row <= 5) {
                score = score + 30;
                details.append("knight in center +30, ");
            }
        }

        if (piece instanceof Bishop) {
            if (col == 0 || col == 7 || row == 0 || row == 7) {
                score = score - 15;
                details.append("bishop on edge -15, ");
            }
            if (col == row || col == (7 - row)) {
                score = score + 10;
                details.append("bishop on diagonal +10, ");
            }
        }

        boolean earlyGame = isEarlyGame();
        if (earlyGame) {
            if (piece.getX() < 1000) {
                int currentCol = (int)((piece.getX() - boardX - borderOffsetX) / squareSize);
                int currentRow = (int)((piece.getY() - boardY - borderOffsetY) / squareSize);
                int newCol = (int)((move.targetX - boardX - borderOffsetX) / squareSize);
                int newRow = (int)((move.targetY - boardY - borderOffsetY) / squareSize);

                int oldDistanceToCenter = Math.abs(currentCol - 3) + Math.abs(currentCol - 4) +
                    Math.abs(currentRow - 3) + Math.abs(currentRow - 4);
                int newDistanceToCenter = Math.abs(newCol - 3) + Math.abs(newCol - 4) +
                    Math.abs(newRow - 3) + Math.abs(newRow - 4);

                if (newDistanceToCenter < oldDistanceToCenter) {
                    score = score + 20;
                    details.append("toward center +20, ");
                }

                if ((piece instanceof Knight || piece instanceof Bishop) && currentRow <= 1) {
                    score = score + 15;
                    details.append("minor piece development +15, ");
                }

                if (piece instanceof Queen && earlyGame) {
                    score = score - 10;
                    details.append("queen early -10, ");
                }
            }
        } else {
            if (piece instanceof Knight || piece instanceof Bishop) {
                int supportingPieces = 0;
                for (Piece ourPiece : pieces) {
                    if (ourPiece != null && ourPiece != piece && ourPiece.getColour() == aiColour && ourPiece.getX() < 1000) {
                        int dist = Math.abs(col - (int)((ourPiece.getX() - boardX - borderOffsetX) / squareSize)) +
                            Math.abs(row - (int)((ourPiece.getY() - boardY - borderOffsetY) / squareSize));
                        if (dist <= 2) {
                            supportingPieces++;
                        }
                    }
                }
                score += supportingPieces * 10;
                if (supportingPieces > 0) {
                    details.append("piece support +" + (supportingPieces * 10) + ", ");
                }
            }

            if (piece instanceof Pawn && !earlyGame) {
                boolean hasFriendlyPawnAdjacent = false;
                for (Piece p : pieces) {
                    if (p != null && p instanceof Pawn && p.getColour() == aiColour && p.getX() < 1000 && p != piece) {
                        int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                        if (Math.abs(pCol - col) == 1) {
                            hasFriendlyPawnAdjacent = true;
                            break;
                        }
                    }
                }
                if (!hasFriendlyPawnAdjacent) {
                    score -= 30;
                    details.append("isolated pawn -30, ");
                }
            }

            if (piece instanceof Rook && !earlyGame) {
                boolean hasPawnOnFile = false;
                for (Piece p : pieces) {
                    if (p != null && p instanceof Pawn && p.getColour() == aiColour && p.getX() < 1000) {
                        int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                        if (pCol == col) {
                            hasPawnOnFile = true;
                            break;
                        }
                    }
                }
                if (!hasPawnOnFile) {
                    score += 40;
                    details.append("rook on open file +40, ");
                }
            }
        }

        if (piece instanceof King) {
            King kingPiece = (King) piece;
            if (!kingPiece.hasMoved && earlyGame) {
                score = score - 15;
                details.append("king unmoved in opening -15, ");
            }

            if (!earlyGame) {
                int piecesOnBoard = 0;
                for (Piece p : pieces) {
                    if (p != null && p.getX() < 1000) {
                        piecesOnBoard++;
                    }
                }
                if (piecesOnBoard > 20) {
                    if (col >= 3 && col <= 4 && row >= 3 && row <= 4) {
                        score -= 50;
                        details.append("king too exposed -50, ");
                    }
                }
            }
        }

        if (piece instanceof Pawn) {
            row = (int)((move.targetY - boardY - borderOffsetY) / squareSize);
            if (aiColour == PieceColour.WHITE && row > 3) {
                score = score + 5;
                details.append("pawn advance +5, ");
            }
            if (aiColour == PieceColour.BLACK && row < 4) {
                score = score + 5;
                details.append("pawn advance +5, ");
            }

            if (isPassedPawn(piece, move.targetX, move.targetY, aiColour)) {
                score = score + 30;
                details.append("passed pawn +30, ");

                if (aiColour == PieceColour.WHITE && row >= 5) {
                    score = score + row * 5;
                    details.append("advanced passed pawn +" + (row * 5) + ", ");
                }
                if (aiColour == PieceColour.BLACK && row <= 2) {
                    score = score + (7 - row) * 5;
                    details.append("advanced passed pawn +" + ((7 - row) * 5) + ", ");
                }
            }

            int pawnsInColumn = 0;
            for (Piece p : pieces) {
                if (p != null && p instanceof Pawn && p.getColour() == aiColour && p.getX() < 1000) {
                    int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                    if (pCol == col && p != piece) {
                        pawnsInColumn = pawnsInColumn + 1;
                    }
                }
            }
            if (pawnsInColumn > 0) {
                score = score - pawnsInColumn * 10;
                details.append("doubled pawns -" + (pawnsInColumn * 10) + ", ");
            }
        }

        if (piece instanceof Rook) {
            boolean openFile = true;
            for (Piece p : pieces) {
                if (p != null && p instanceof Pawn && p.getColour() == aiColour && p.getX() < 1000) {
                    int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                    if (pCol == col) {
                        openFile = false;
                        break;
                    }
                }
            }
            if (openFile) {
                score = score + 25;
                details.append("rook on open file +25, ");
            }
        }

        if (!earlyGame && move.capturedPiece == null && !(piece instanceof Pawn)) {
            float tempX = piece.getX();
            float tempY = piece.getY();
            piece.setX(move.targetX);
            piece.setY(move.targetY);

            int bestOpponentCapture = 0;
            java.util.ArrayList<Move> opponentMoves = getAllLegalMoves(opponentColour);
            for (Move oppMove : opponentMoves) {
                if (oppMove.capturedPiece != null) {
                    int captureValue = getPieceValue(oppMove.capturedPiece);
                    if (captureValue > bestOpponentCapture) {
                        bestOpponentCapture = captureValue;
                    }
                }
            }

            piece.setX(tempX);
            piece.setY(tempY);

            if (bestOpponentCapture > 0) {
                score -= bestOpponentCapture * 20;
                details.append("opponent threat -" + (bestOpponentCapture * 20) + ", ");
            }
        }

        int activityBonus = calculateActivityBonus(piece, move.targetX, move.targetY);
        score = score + activityBonus * 2;
        if (activityBonus > 0) {
            details.append("activity bonus +" + (activityBonus * 2) + ", ");
        }

        if (newSquareAttacked && !newSquareDefended) {
            if (pieceCurrentlyAttacked) {
                score = score - 50000;
                details.append("moving attacked piece to undefended square -50000, ");
            } else {
                score = score - 5000000;
                details.append("moving to attacked undefended square -5000000, ");
            }
        }

        return new MoveWithScore(move, score, details.toString());
    }
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

    private boolean isEarlyGame() {
        int capturedPieces = 0;
        for (Piece piece : pieces) {
            if (piece != null && piece.getX() >= 1000) {
                capturedPieces = capturedPieces + 1;
            }
        }
        return capturedPieces < 8;
    }

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
            if (opponentPiece != null && opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
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

    private int calculateActivityBonus(Piece piece, float targetX, float targetY) {
        int bonus = 0;
        int col = (int)((targetX - boardX - borderOffsetX) / squareSize);
        int row = (int)((targetY - boardY - borderOffsetY) / squareSize);

        if (col >= 2 && col <= 5 && row >= 2 && row <= 5) {
            bonus = bonus + 5;
        }
        if ((col == 3 || col == 4) && (row == 3 || row == 4)) {
            bonus = bonus + 10;
        }

        if (piece instanceof Knight) {
            if (col >= 2 && col <= 5 && row >= 2 && row <= 5) {
                bonus += 15;
            }
        }

        if (piece instanceof Bishop) {
            if (col == row || col == (7 - row)) {
                bonus += 10;
            }
        }

        if (piece instanceof Rook) {
            if ((piece.getColour() == PieceColour.WHITE && row > 0) ||
                (piece.getColour() == PieceColour.BLACK && row < 7)) {
                bonus += 5;
            }
        }

        return bonus;
    }

    public java.util.ArrayList<Move> getSortedLegalMoves(PieceColour colour) {
        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);

        if (legalMoves.size() == 0) {
            return legalMoves;
        }

        java.util.ArrayList<MoveWithScore> movesWithScores = new java.util.ArrayList<MoveWithScore>();

        for (int i = 0; i < legalMoves.size(); i++) {
            Move move = legalMoves.get(i);
            MoveWithScore moveWithScore = evaluateMove(move, colour);
            movesWithScores.add(moveWithScore);
        }

        for (int i = 0; i < movesWithScores.size() - 1; i++) {
            for (int j = 0; j < movesWithScores.size() - i - 1; j++) {
                if (movesWithScores.get(j).score < movesWithScores.get(j + 1).score) {
                    MoveWithScore temp = movesWithScores.get(j);
                    movesWithScores.set(j, movesWithScores.get(j + 1));
                    movesWithScores.set(j + 1, temp);
                }
            }
        }

        java.util.ArrayList<Move> sortedMoves = new java.util.ArrayList<Move>();
        for (int i = 0; i < movesWithScores.size(); i++) {
            sortedMoves.add(movesWithScores.get(i).move);
        }

        return sortedMoves;
    }

    private boolean isSquareDefended(float squareX, float squareY, PieceColour defenderColour) {
        int defendedCount = 0;

        for (Piece piece : pieces) {
            if (piece != null && piece.getColour() == defenderColour && piece.getX() < 1000) {
                if (piece.isValidMove(squareX, squareY, this)) {
                    defendedCount = defendedCount + 1;
                    if (defendedCount >= 2) {
                        return true;
                    }
                }
            }
        }

        return defendedCount > 0;
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

    private boolean createsNewThreat(Piece piece, float targetX, float targetY, PieceColour aiColour) {
        float oldX = piece.getX();
        float oldY = piece.getY();

        piece.setX(targetX);
        piece.setY(targetY);

        boolean createsThreat = false;

        for (Piece opponentPiece : pieces) {
            if (opponentPiece != null && opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                if (piece.isValidMove(opponentPiece.getX(), opponentPiece.getY(), this)) {
                    boolean isDefended = isSquareDefended(opponentPiece.getX(), opponentPiece.getY(), opponentPiece.getColour());
                    if (!isDefended) {
                        createsThreat = true;
                        break;
                    }
                }
            }
        }

        piece.setX(oldX);
        piece.setY(oldY);

        return createsThreat;
    }

    private boolean isSquareAttacked(float squareX, float squareY, PieceColour defenderColour) {
        for (Piece piece : pieces) {
            if (piece != null && piece.getColour() != defenderColour && piece.getX() < 1000) {
                if (piece.isValidMove(squareX, squareY, this)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int findSmallestAttackerValue(float squareX, float squareY, PieceColour defenderColour) {
        int smallestValue = 100;
        for (Piece piece : pieces) {
            if (piece != null && piece.getColour() != defenderColour && piece.getX() < 1000) {
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
            if (piece != null && piece.getX() < 1000) {
                piece.draw(batch);
            }
        }
    }

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

        public String toString() {
            int fromCol = (int)((piece.getX() - boardX - borderOffsetX) / squareSize);
            int fromRow = (int)((piece.getY() - boardY - borderOffsetY) / squareSize);
            int toCol = (int)((targetX - boardX - borderOffsetX) / squareSize);
            int toRow = (int)((targetY - boardY - borderOffsetY) / squareSize);
            char fromColChar = (char)('A' + fromCol);
            char toColChar = (char)('A' + toCol);
            int fromRowNum = fromRow + 1;
            int toRowNum = toRow + 1;
            return fromColChar + "" + fromRowNum + " to " + toColChar + "" + toRowNum;
        }
    }

    public class MoveWithScore {
        public String details;
        public Move move;
        public int score;

        public MoveWithScore(Move move, int score, String details) {
            this.move = move;
            this.score = score;
            this.details = details;
        }
    }

    public java.util.ArrayList<MoveWithScore> getSortedLegalMovesWithScores(PieceColour colour) {
        java.util.ArrayList<Move> legalMoves = getAllLegalMoves(colour);

        if (legalMoves.size() == 0) {
            return new java.util.ArrayList<MoveWithScore>();
        }

        java.util.ArrayList<MoveWithScore> movesWithScores = new java.util.ArrayList<MoveWithScore>();

        for (int i = 0; i < legalMoves.size(); i++) {
            Move move = legalMoves.get(i);
            MoveWithScore moveWithScore = evaluateMove(move, colour);
            movesWithScores.add(moveWithScore);
        }

        for (int i = 0; i < movesWithScores.size() - 1; i++) {
            for (int j = 0; j < movesWithScores.size() - i - 1; j++) {
                if (movesWithScores.get(j).score < movesWithScores.get(j + 1).score) {
                    MoveWithScore temp = movesWithScores.get(j);
                    movesWithScores.set(j, movesWithScores.get(j + 1));
                    movesWithScores.set(j + 1, temp);
                }
            }
        }

        return movesWithScores;
    }

}
