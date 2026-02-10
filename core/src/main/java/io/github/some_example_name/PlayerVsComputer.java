package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class PlayerVsComputer {
    private Board board;
    private boolean whiteTurn = true;
    private Piece selectedPiece;
    private boolean pieceSelected = false;
    private boolean playerIsWhite;
    private int aiDifficulty;
    private Random random = new Random();

    public PlayerVsComputer(Board board, boolean playerIsWhite, int aiDifficulty) {
        this.board = board;
        this.playerIsWhite = playerIsWhite;
        this.aiDifficulty = aiDifficulty;
    }

    public boolean click(float x, float y) {
        if (board.gameOver) return false;

        if (board.promotingPawn != null) {
            return false;
        }

        int col = (int)((x - board.boardX - board.borderOffsetX) / board.squareSize);
        int row = (int)((y - board.boardY - board.borderOffsetY) / board.squareSize);

        if (col >= 0 && col < 8 && row >= 0 && row < 8) {
            String square = getSquareNotation(col, row);
            System.out.println("clicked " + square);
        }

        if ((playerIsWhite && !whiteTurn) || (!playerIsWhite && whiteTurn)) {
            return false;
        }

        if (!pieceSelected) {
            for (Piece piece : board.getPieces()) {
                if (piece.getX() < 1000 && x >= piece.getX() && x <= piece.getX() + piece.getWidth() &&
                    y >= piece.getY() && y <= piece.getY() + piece.getHeight()) {

                    if (whiteTurn && piece.getColour() == PieceColour.WHITE) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        String pieceType = getPieceType(piece);
                        String colour = piece.getColour() == PieceColour.WHITE ? "white" : "black";
                        System.out.println("selected " + colour + " " + pieceType);
                        return false;
                    } else if (!whiteTurn && piece.getColour() == PieceColour.BLACK) {
                        selectedPiece = piece;
                        pieceSelected = true;
                        String pieceType = getPieceType(piece);
                        String colour = piece.getColour() == PieceColour.WHITE ? "white" : "black";
                        System.out.println("selected " + colour + " " + pieceType);
                        return false;
                    }
                    break;
                }
            }
        } else {
            boolean moveSuccessful = board.tryMove(selectedPiece, x, y, whiteTurn);
            if (moveSuccessful) {
                if (board.promotingPawn == null) {
                    whiteTurn = !whiteTurn;
                }
            }
            pieceSelected = false;
            return moveSuccessful;
        }
        return false;
    }

    private String getSquareNotation(int col, int row) {
        char colChar = (char)('A' + col);
        int displayRow = row + 1;
        return "" + colChar + displayRow;
    }

    private String getPieceType(Piece piece) {
        if (piece instanceof Pawn) return "pawn";
        if (piece instanceof Knight) return "knight";
        if (piece instanceof Bishop) return "bishop";
        if (piece instanceof Rook) return "rook";
        if (piece instanceof Queen) return "queen";
        if (piece instanceof King) return "king";
        return "unknown";
    }

    public void makeAIMove() {
        if (board.gameOver) return;
        if (board.promotingPawn != null) return;

        PieceColour aiColour;
        if (playerIsWhite) {
            aiColour = PieceColour.BLACK;
        } else {
            aiColour = PieceColour.WHITE;
        }

        java.util.ArrayList<Board.MoveWithScore> sortedMovesWithScores = board.getSortedLegalMovesWithScores(aiColour);

        // Print all moves with their evaluations
        for (int i = 0; i < sortedMovesWithScores.size(); i++) {
            Board.MoveWithScore moveWithScore = sortedMovesWithScores.get(i);
            Board.Move move = moveWithScore.move;
            String details = moveWithScore.details;
            int total = moveWithScore.score;

            // Get square notation
            String fromSquare = getSquareNotationFromCoords(move.piece.getX(), move.piece.getY());
            String toSquare = getSquareNotationFromCoords(move.targetX, move.targetY);

            System.out.println((i + 1) + ". " + fromSquare + " to " + toSquare + ": " + details + "score = " + total);
        }

        if (sortedMovesWithScores.size() == 0) return;

        Board.Move chosenMove;
        switch (aiDifficulty) {
            case 1:
                chosenMove = getRandomMoveFromMoveWithScore(sortedMovesWithScores);
                break;
            case 2:
                chosenMove = getMediumMoveFromMoveWithScore(sortedMovesWithScores);
                break;
            case 3:
                chosenMove = getHardMoveFromMoveWithScore(sortedMovesWithScores);
                break;
            default:
                chosenMove = getRandomMoveFromMoveWithScore(sortedMovesWithScores);
                break;
        }

        // Print the played move
        int playedIndex = -1;
        for (int i = 0; i < sortedMovesWithScores.size(); i++) {
            if (sortedMovesWithScores.get(i).move == chosenMove) {
                playedIndex = i + 1;
                break;
            }
        }

        String fromSquare = getSquareNotationFromCoords(chosenMove.piece.getX(), chosenMove.piece.getY());
        String toSquare = getSquareNotationFromCoords(chosenMove.targetX, chosenMove.targetY);
        String pieceType = getPieceType(chosenMove.piece);

        System.out.println("played " + playedIndex + ". " + pieceType + " " + fromSquare + " to " + toSquare);

        board.makeMove(chosenMove);
        if (board.promotingPawn == null) {
            whiteTurn = !whiteTurn;
        }
    }



    private Board.Move getRandomMoveFromMoveWithScore(java.util.ArrayList<Board.MoveWithScore> movesWithScores) {
        int randomIndex = random.nextInt(movesWithScores.size());
        return movesWithScores.get(randomIndex).move;
    }

    private Board.Move getMediumMoveFromMoveWithScore(java.util.ArrayList<Board.MoveWithScore> movesWithScores) {
        if (movesWithScores.size() == 0) {
            return null;
        }

        int topMovesCount = movesWithScores.size() / 3;
        if (topMovesCount < 1) {
            topMovesCount = 1;
        }

        int randomIndex = random.nextInt(topMovesCount);
        return movesWithScores.get(randomIndex).move;
    }

    private Board.Move getHardMoveFromMoveWithScore(java.util.ArrayList<Board.MoveWithScore> movesWithScores) {
        if (movesWithScores.size() == 0)
            return null;
        int randomIndex = random.nextInt(movesWithScores.size());
        return
            movesWithScores.get(randomIndex).move;
    }


    private String getSquareNotationFromCoords(float x, float y) {
        int col = (int)((x - board.boardX - board.borderOffsetX) / board.squareSize);
        int row = (int)((y - board.boardY - board.borderOffsetY) / board.squareSize);
        return getSquareNotation(col, row);
    }

    private Board.Move getRandomMove(java.util.ArrayList<Board.Move> legalMoves) {
        int randomIndex = random.nextInt(legalMoves.size());
        return legalMoves.get(randomIndex);
    }

    private Board.Move getMediumMove(java.util.ArrayList<Board.Move> sortedMoves) {
        if (sortedMoves.size() == 0) {
            return null;
        }

        int topMovesCount = sortedMoves.size() / 3;
        if (topMovesCount < 1) {
            topMovesCount = 1;
        }

        int randomIndex = random.nextInt(topMovesCount);
        return sortedMoves.get(randomIndex);
    }

    private Board.Move getHardMove(java.util.ArrayList<Board.Move> sortedMoves) {
        if (sortedMoves.size() == 0) {
            return null;
        }

        int topMovesToConsider = 2;
        if (sortedMoves.size() < 2) {
            topMovesToConsider = sortedMoves.size();
        }

        int randomIndex = random.nextInt(topMovesToConsider);
        return sortedMoves.get(randomIndex);
    }

    public void draw(SpriteBatch batch) {
        board.draw(batch);
        board.drawCapturedPieces(batch);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setAiDifficulty(int difficulty) {
        this.aiDifficulty = difficulty;
    }
    public void switchTurn() {
        whiteTurn = !whiteTurn;
    }
}
