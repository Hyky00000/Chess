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

    public boolean tryMove(Piece piece, float targetX, float targetY, boolean whiteTurn) {
        if (gameOver) return false;
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
                int currentCol = (int)((oldX - boardX - borderOffsetX) / squareSize);
                int currentRow = (int)((oldY - boardY - borderOffsetY) / squareSize);

                int colDiff = targetCol - currentCol;
                int rowDiff = targetRow - currentRow;

                // Check if this is a diagonal move for el en passent
                if (colDiff == 1 || colDiff == -1) {
                    if (piece.getColour() == PieceColour.WHITE && rowDiff == 1) {
                        int behindRow = targetRow - 1;
                        for (Piece p : pieces) {
                            if (p != piece && p.getX() < 1000 && p instanceof Pawn) {
                                int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                                int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);
                                if (pCol == targetCol && pRow == behindRow) {
                                    Pawn enemyPawn = (Pawn) p;
                                    if (enemyPawn.justMovedTwoSquares) {
                                        capturedPiece = p;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if (piece.getColour() == PieceColour.BLACK && rowDiff == -1) {
                        int behindRow = targetRow + 1;
                        for (Piece p : pieces) {
                            if (p != piece && p.getX() < 1000 && p instanceof Pawn) {
                                int pCol = (int)((p.getX() - boardX - borderOffsetX) / squareSize);
                                int pRow = (int)((p.getY() - boardY - borderOffsetY) / squareSize);
                                if (pCol == targetCol && pRow == behindRow) {
                                    Pawn enemyPawn = (Pawn) p;
                                    if (enemyPawn.justMovedTwoSquares) {
                                        capturedPiece = p;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            piece.setX(snapX);
            piece.setY(snapY);
            if (capturedPiece != null) {
                capturedPiece.setX(1000);
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

                // checkmate stalemate?
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

    // Make a move for ai as ai already chooses from array of legal moves
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

        if (piece instanceof King) {
            float oldX = piece.getX() - (targetX - piece.getX());
            handleCastling((King) piece, oldX, targetX);
        }

        if (piece instanceof Rook) {
            ((Rook) piece).hasMoved = true;
        }

        checkPawnPromotion(piece, targetY);
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

    // from here on is for AI level 2 and 3 ofcofc
    public int evaluateMove(Move move, PieceColour aiColour) {
        int score = 0;  // Each move will start with score 0
        Piece piece = move.piece;

        // method numero 1 Check if move captures a piece and ADDDD points of captured piece value
        if (move.capturedPiece != null) {
            // Get the value of the captured piece ANDDDDD multiply by 20 to make it important
            int pieceValue = getPieceValue(move.capturedPiece);
            score = score + (pieceValue * 20);
        }

        //  CRITICAL: Check if move leaves our piece undefended and can be captured
        boolean leavesHanging = leavesPieceHanging(piece, move.targetX, move.targetY, aiColour);
        if (leavesHanging) {
            int ourPieceValue = getPieceValue(piece);
            score -= (ourPieceValue * 25); // BIG penalty for hanging our piece
        }

        // Check if the destination square is attacked by opponent
        boolean squareIsAttacked = isSquareAttacked(move.targetX, move.targetY, aiColour);
        if (squareIsAttacked) {
            // If moving to attacked square, check if we're trading equally or losing material
            int attackerValue = findSmallestAttackerValue(move.targetX, move.targetY, aiColour);
            int ourPieceValue = getPieceValue(piece);

            if (ourPieceValue > attackerValue) {
                // We're losing material! BIG penalty
                score -= (ourPieceValue - attackerValue) * 30;
            } else if (ourPieceValue == attackerValue) {
                // Equal trade - small penalty since we're moving into capture
                score -= 5;
            }
            // If our piece is worth less than attacker, it's actually good trade
        }

        // Check if move creates a threat (attacks undefended piece)
        boolean createsThreat = createsNewThreat(piece, move.targetX, move.targetY, aiColour);
        if (createsThreat) {
            score += 15; // Bonus for creating threats
        }

        // Check if it's early game for development that extra bonuses :)_
        boolean earlyGame = isEarlyGame();
        if (earlyGame) {
            if (piece.getX() < 1000) {  // If piece is still on the board ofc ofc
                int currentCol = (int)((piece.getX() - boardX - borderOffsetX) / squareSize);
                int currentRow = (int)((piece.getY() - boardY - borderOffsetY) / squareSize);
                int newCol = (int)((move.targetX - boardX - borderOffsetX) / squareSize);
                int newRow = (int)((move.targetY - boardY - borderOffsetY) / squareSize);

                // Bonus for moving pieces toward the CENTREEE of the board
                if (newCol >= 2 && newCol <= 5 && newRow >= 2 && newRow <= 5) {
                    score = score + 3;  // Add 3 points for center control
                }

                // Bonus for developing knights and bishops early very smart
                if ((piece instanceof Knight || piece instanceof Bishop) && currentRow <= 1) {
                    score = score + 2;  // Add 2 points for developing minor pieces
                }
            }
        }

        // Penalty for moving king stupidly INNN the early game
        if (piece instanceof King) {
            King kingPiece = (King) piece;
            if (!kingPiece.hasMoved && earlyGame) {
                score = score - 2;  // Subtract 2 points for moving the king stupidly
            }
        }

        // Bonus for piece activity basically more squares controlled silly
        int activityBonus = calculateActivityBonus(piece, move.targetX, move.targetY);
        score = score + activityBonus;

        //  Special bonus for pawn moves that advance past first 3 rows or something
        if (piece instanceof Pawn) {
            int row = (int)((move.targetY - boardY - borderOffsetY) / squareSize);
            if (aiColour == PieceColour.WHITE && row > 3) {
                score = score + 1;  // Add 1 point for white pawn advancement
            }
            if (aiColour == PieceColour.BLACK && row < 4) {
                score = score + 1;  // Add 1 point for black pawn advancement
            }
        }

        return score;
    }

    //Get the value of a piece for scoring
    private int getPieceValue(Piece piece) {
        if (piece instanceof Pawn) {
            return 1;  // Pawns are worth 1 point
        }
        if (piece instanceof Knight) {
            return 3;  // Knights are 3
        }
        if (piece instanceof Bishop) {
            return 3;  // Bishops 3
        }
        if (piece instanceof Rook) {
            return 5;  // Rooks 5
        }
        if (piece instanceof Queen) {
            return 9;  // Queens 9
        }
        if (piece instanceof King) {
            return 100;  // Kings are worth 100 points
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

        float oldX = piece.getX();
        float oldY = piece.getY();

        piece.setX(targetX);
        piece.setY(targetY);

        // Check if any opponent piece can capture this piece at the new position
        boolean canBeCaptured = false;
        for (Piece opponentPiece : pieces) {
            // Only check opponent pieces that are still on the board AGAIn remember if i wanna enqueue mini pieces
            if (opponentPiece.getColour() != aiColour && opponentPiece.getX() < 1000) {
                if (opponentPiece.isValidMove(targetX, targetY, this)) {
                    canBeCaptured = true;
                    break;  // No need to check if theres even 1
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

        // Create the final third new list with just the sorted moves
        java.util.ArrayList<Move> sortedMoves = new java.util.ArrayList<Move>();
        for (int i = 0; i < movesWithScores.size(); i++) {
            sortedMoves.add(movesWithScores.get(i).move);
        }

        return sortedMoves;
    }

    // Check if a square is defended by own pieces
    private boolean isSquareDefended(float squareX, float squareY, PieceColour defenderColour) {
        for (Piece piece : pieces) {
            if (piece.getColour() == defenderColour && piece.getX() < 1000) {
                if (piece.isValidMove(squareX, squareY, this)) {
                    return true;
                }
            }
        }
        return false;
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
