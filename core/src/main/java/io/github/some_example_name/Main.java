package io.github.some_example_name;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Board board;
    private Menu menu;
    private ColourChoice colourChoice;
    private WhitePromotion whitePromotion;
    private BlackPromotion blackPromotion;
    private Difficulty difficulty;
    // NEW: Network multiplayer variables
    private NetworkMenu networkMenu;
    private Texture networkMenuTexture;
    private PlayerVsNetwork networkGame;
    private int mode = 0;
    float menuChoiceHeight = 55.65861f;
    float menuGapHeight = 14.78852f;
    float difficultyChoiceHeight = 60.570370370370370370f;
    private float promotionGap = 54.5f;

    private Texture whitePromotionTexture;
    private Texture blackPromotionTexture;
    private Texture colourChoiceTexture;
    private Texture difficultyTexture;
    private Texture menuTexture;
    private Texture boardTexture;
    private Texture whitePawnTex, blackPawnTex;
    private Texture whiteRookTex, blackRookTex;
    private Texture whiteKnightTex, blackKnightTex;
    private Texture whiteBishopTex, blackBishopTex;
    private Texture whiteQueenTex, blackQueenTex;
    private Texture whiteKingTex, blackKingTex;

    private PlayerVsPlayer pvpGame;
    private PlayerVsComputer pvcGame;
    int aiDifficulty;
    boolean playerIsWhite;

    private Piece promotingPawn = null;
    private boolean isWhitePromotion = false;
    private float promotionMenuX, promotionMenuY;
    private int previousMode = 0;

    private float blackFTime = 300;
    private float whiteFTime = 300;
    private boolean whiteClockRunning = false;
    private boolean blackClockRunning = false;
    int blackBNumber;
    float blackSNumber;
    int blackSRounded;
    int whiteBNumber;
    float whiteSNumber;
    int whiteSRounded;
    private BitmapFont font;

    private boolean lastWhiteTurn = true;

    // NEW: Variables for network game status
    private boolean networkWaitingForConnection = false;
    private float networkWaitTimer = 0;
    private String networkStatusText = "";

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        whitePromotionTexture = new Texture("WhitePromotion.png");
        blackPromotionTexture = new Texture("BlackPromotion.png");
        difficultyTexture = new Texture("Difficulty.png");
        colourChoiceTexture = new Texture("ColourChoice.png");
        boardTexture = new Texture("ChessBoard.png");
        menuTexture = new Texture("Menu.png");
        networkMenuTexture = new Texture("NetworkMenu.png");

        whitePawnTex = new Texture("WhitePawn.png");
        blackPawnTex = new Texture("BlackPawn.png");
        whiteRookTex = new Texture("WhiteRook.png");
        blackRookTex = new Texture("BlackRook.png");
        whiteKnightTex = new Texture("WhiteKnight.png");
        blackKnightTex = new Texture("BlackKnight.png");
        whiteBishopTex = new Texture("WhiteBishop.png");
        blackBishopTex = new Texture("BlackBishop.png");
        whiteQueenTex = new Texture("WhiteQueen.png");
        blackQueenTex = new Texture("BlackQueen.png");
        whiteKingTex = new Texture("WhiteKing.png");
        blackKingTex = new Texture("BlackKing.png");

        board = new Board(boardTexture, whitePawnTex, blackPawnTex, whiteRookTex, blackRookTex, whiteKnightTex, blackKnightTex, whiteBishopTex, blackBishopTex, whiteQueenTex, blackQueenTex, whiteKingTex, blackKingTex);
        menu = new Menu(menuTexture);
        colourChoice = new ColourChoice(colourChoiceTexture);
        difficulty = new Difficulty(difficultyTexture);
        whitePromotion = new WhitePromotion(whitePromotionTexture);
        blackPromotion = new BlackPromotion(blackPromotionTexture);
        networkMenu = new NetworkMenu(networkMenuTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1);
        batch.begin();

        // 0 is start, 1 is choose colour, 2 is difficulty, 3 is vs ai, 4 is pvp, 5 is pawn promotion, 6 is network menu, 7 is network game

        if (mode == 0) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            menu.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + menu.getHeight()) && (y > menu.getY() + menu.getHeight() - menuChoiceHeight)) {
                    mode = 4;
                    pvpGame = new PlayerVsPlayer(board);
                    whiteClockRunning = false;
                    blackClockRunning = false;
                } else if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + ((3 * menuChoiceHeight) + (2 * menuGapHeight))) &&
                    (y > menu.getY() + ((2 * menuChoiceHeight) + (2 * menuGapHeight)))) {
                    mode = 1;
                }
                // NEW: Check if the third menu option (Network Play) is clicked
                else if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + ((2 * menuChoiceHeight) + (1 * menuGapHeight))) &&
                    (y > menu.getY() + ((1 * menuChoiceHeight) + (1 * menuGapHeight)))) {
                    mode = 6; // Go to network menu
                    networkMenu.reset(); // Reset network menu to fresh state
                    networkStatusText = ""; // Clear any status messages
                }
            }
        } else if (mode == 1) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            colourChoice.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (x >= colourChoice.getX() && x <= colourChoice.getX() + colourChoice.getWidth() &&
                    y >= colourChoice.getY() && y <= colourChoice.getY() + colourChoice.getHeight()) {
                    float clickY = y - colourChoice.getY();
                    playerIsWhite = clickY < colourChoice.getHeight() / 2;
                    mode = 2;
                }
            }
        } else if (mode == 2) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            difficulty.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (difficultyChoiceHeight * 3)) && (y > difficulty.getY())) {
                    aiDifficulty = 3;
                    mode = 3;
                    if (playerIsWhite) {
                        whiteClockRunning = false;
                        blackClockRunning = false;
                    } else {
                        whiteClockRunning = false;
                        blackClockRunning = false;
                    }
                } else if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (difficultyChoiceHeight * 2)) && (y > difficulty.getY())) {
                    aiDifficulty = 2;
                    mode = 3;
                    if (playerIsWhite) {
                        whiteClockRunning = false;
                        blackClockRunning = false;
                    } else {
                        whiteClockRunning = false;
                        blackClockRunning = false;
                    }
                } else if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (difficultyChoiceHeight * 1)) && (y > difficulty.getY())) {
                    aiDifficulty = 1;
                    mode = 3;
                    if (playerIsWhite) {
                        whiteClockRunning = false;
                        blackClockRunning = false;
                    } else {
                        whiteClockRunning = false;
                        blackClockRunning = false;
                    }
                }
            }
        } else if (mode == 3) {
            if (pvcGame == null) {
                pvcGame = new PlayerVsComputer(board, playerIsWhite, aiDifficulty);
            }
            if (board.promotingPawn != null) {
                promotingPawn = board.promotingPawn;
                isWhitePromotion = (promotingPawn.getColour() == PieceColour.WHITE);
                previousMode = 3;
                mode = 5;
                if (isWhitePromotion) {
                    whiteClockRunning = true;
                    blackClockRunning = false;
                } else {
                    whiteClockRunning = false;
                    blackClockRunning = true;
                }
            } else {
                boolean shouldAIMove = (playerIsWhite && !pvcGame.isWhiteTurn()) || (!playerIsWhite && pvcGame.isWhiteTurn());
                if (shouldAIMove) {
                    pvcGame.makeAIMove();
                    boolean currentWhiteTurn = pvcGame.isWhiteTurn();
                    if (currentWhiteTurn != lastWhiteTurn) {
                        if (currentWhiteTurn) {
                            blackClockRunning = false;
                            whiteClockRunning = true;
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                        }
                        lastWhiteTurn = currentWhiteTurn;
                    }
                }

                if (Gdx.input.justTouched()) {
                    float x = Gdx.input.getX();
                    float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                    boolean moveMade = pvcGame.click(x, y);
                    boolean currentWhiteTurn = pvcGame.isWhiteTurn();
                    if (moveMade && currentWhiteTurn != lastWhiteTurn) {
                        if (currentWhiteTurn) {
                            blackClockRunning = false;
                            whiteClockRunning = true;
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                        }
                        lastWhiteTurn = currentWhiteTurn;
                    }
                }
                pvcGame.draw(batch);
            }
        } else if (mode == 4) {
            if (pvpGame == null) {
                pvpGame = new PlayerVsPlayer(board);
            }
            if (board.promotingPawn != null) {
                promotingPawn = board.promotingPawn;
                isWhitePromotion = (promotingPawn.getColour() == PieceColour.WHITE);
                previousMode = 4;
                mode = 5;
                if (isWhitePromotion) {
                    whiteClockRunning = true;
                    blackClockRunning = false;
                } else {
                    whiteClockRunning = false;
                    blackClockRunning = true;
                }
            } else {
                if (Gdx.input.justTouched()) {
                    float x = Gdx.input.getX();
                    float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                    boolean moveMade = pvpGame.click(x, y);
                    boolean currentWhiteTurn = pvpGame.isWhiteTurn();
                    if (moveMade && currentWhiteTurn != lastWhiteTurn) {
                        if (currentWhiteTurn) {
                            blackClockRunning = false;
                            whiteClockRunning = true;
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                        }
                        lastWhiteTurn = currentWhiteTurn;
                    }
                }
                pvpGame.draw(batch);
            }
        } else if (mode == 5) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            float pawnX = promotingPawn.getX();
            int pawnCol = (int)((pawnX - board.boardX - board.borderOffsetX) / board.squareSize);
            float menuX, menuY;
            if (pawnCol < 4) {
                menuX = board.boardX + (board.width - board.borderOffsetX - 60);
            } else {
                menuX = board.borderOffsetX;
            }
            if (isWhitePromotion) {
                menuY = 200;
                whitePromotion.setX(menuX);
                whitePromotion.setY(menuY);
                whitePromotion.draw(batch);
            } else {
                menuY = 100;
                blackPromotion.setX(menuX);
                blackPromotion.setY(menuY);
                blackPromotion.draw(batch);
            }
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                float menuWidth;
                float menuHeight;
                if (isWhitePromotion) {
                    menuWidth = whitePromotion.getWidth();
                    menuHeight = whitePromotion.getHeight();
                } else {
                    menuWidth = blackPromotion.getWidth();
                    menuHeight = blackPromotion.getHeight();
                }
                if (x >= menuX && x <= menuX + menuWidth &&
                    y >= menuY && y <= menuY + menuHeight) {
                    float relativeY = y - menuY;
                    float sectionHeight = menuHeight / 4;
                    int choice = (int)(relativeY / sectionHeight);
                    if (choice == 0) {
                        choice = 3;
                    } else if (choice == 1) {
                        choice = 2;
                    } else if (choice == 2) {
                        choice = 1;
                    } else if (choice == 3) {
                        choice = 0;
                    }
                    if (isWhitePromotion) {
                        board.promotePawn(promotingPawn, choice,
                            whiteQueenTex, whiteRookTex,
                            whiteBishopTex, whiteKnightTex);
                    } else {
                        board.promotePawn(promotingPawn, choice,
                            blackQueenTex, blackRookTex,
                            blackBishopTex, blackKnightTex);
                    }
                    if (previousMode == 3) {
                        mode = 3;
                        if (pvcGame != null) {
                            pvcGame.switchTurn();
                            boolean currentWhiteTurn = pvcGame.isWhiteTurn();
                            if (currentWhiteTurn) {
                                blackClockRunning = false;
                                whiteClockRunning = true;
                            } else {
                                whiteClockRunning = false;
                                blackClockRunning = true;
                            }
                            lastWhiteTurn = currentWhiteTurn;
                        }
                    } else if (previousMode == 4) {
                        mode = 4;
                        if (pvpGame != null) {
                            boolean currentWhiteTurn = pvpGame.isWhiteTurn();
                            if (currentWhiteTurn) {
                                blackClockRunning = false;
                                whiteClockRunning = true;
                            } else {
                                whiteClockRunning = false;
                                blackClockRunning = true;
                            }
                            lastWhiteTurn = currentWhiteTurn;
                        }
                    } else if (previousMode == 7) {
                        // NEW: If we came from network game, return to network game
                        mode = 7;
                        // Network game turn switching is handled by the network game itself
                        // Send promotion choice to opponent if needed
                        if (networkGame != null) {
                            networkGame.sendPromotionChoice(choice);
                        }
                    }
                    promotingPawn = null;
                    board.promotingPawn = null;
                }
            }
        }
        // NEW: Mode 6 - Network Menu (Choose Host or Join)
        else if (mode == 6) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            networkMenu.draw(batch);

            // Draw some instructions for the user
            font.getData().setScale(1f, 1f);
            font.setColor(Color.WHITE);
            font.draw(batch, "Online Multiplayer", 50, 450);

            // Show what the user has selected
            if (networkMenu.wantsToHost()) {
                font.draw(batch, "Selected: HOST GAME", 50, 430);
                font.draw(batch, "Click again to start hosting", 50, 410);
                font.draw(batch, "Tell opponent your IP address", 50, 390);
            } else if (networkMenu.wantsToJoin()) {
                font.draw(batch, "Selected: JOIN GAME", 50, 430);
                font.draw(batch, "Click 'Enter IP' section to type IP", 50, 410);
                font.draw(batch, "Type IP address: " + networkMenu.getTypedText(), 50, 390);
            }

            // Show network status if we have any
            if (!networkStatusText.isEmpty()) {
                font.setColor(Color.YELLOW);
                font.draw(batch, networkStatusText, 50, 370);
            }

            // Handle clicks on the network menu
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                // Check if click is on the network menu
                if (x >= networkMenu.getX() && x <= networkMenu.getX() + networkMenu.getWidth() &&
                    y >= networkMenu.getY() && y <= networkMenu.getY() + networkMenu.getHeight()) {

                    // Let the network menu handle the click
                    networkMenu.handleClick(x, y);

                    // Check if we should start a network game
                    if (networkMenu.wantsToHost() && networkMenu.isReady()) {
                        // Start as HOST
                        startNetworkGameAsHost();
                    } else if (networkMenu.wantsToJoin() && networkMenu.isReady()) {
                        // Start as CLIENT (join existing game)
                        startNetworkGameAsClient();
                    }

                } else {
                    // Clicked outside the menu - stop typing if we were typing
                    networkMenu.stopTyping();
                }
            }

            // Handle keyboard input for typing (very simple version)
            // In a real game, you might want to use Gdx.input.getTextInput() for better typing
            if (networkMenu.isTyping()) {
                // Simple keyboard handling - this is very basic
                // You might want to improve this for better user experience
                if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.BACKSPACE)) {
                    networkMenu.removeLastCharacter();
                }
            }
        }
        // NEW: Mode 7 - Network Game (Actual online gameplay)
        else if (mode == 7) {
            // Check if we have a network game object
            if (networkGame == null) {
                // Something went wrong, go back to network menu
                mode = 6;
                networkStatusText = "Error: Game not created properly";
            } else {
                // Update the network game to check for incoming messages
                networkGame.update();

                // Check for pawn promotion (just like other game modes)
                if (board.promotingPawn != null) {
                    promotingPawn = board.promotingPawn;
                    isWhitePromotion = (promotingPawn.getColour() == PieceColour.WHITE);
                    previousMode = 7; // Remember we came from network mode
                    mode = 5; // Use the same promotion menu
                    if (isWhitePromotion) {
                        whiteClockRunning = true;
                        blackClockRunning = false;
                    } else {
                        whiteClockRunning = false;
                        blackClockRunning = true;
                    }
                } else {
                    // Normal network gameplay
                    if (Gdx.input.justTouched()) {
                        float x = Gdx.input.getX();
                        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                        boolean moveMade = networkGame.click(x, y);
                        boolean currentWhiteTurn = networkGame.isWhiteTurn();
                        if (moveMade && currentWhiteTurn != lastWhiteTurn) {
                            if (currentWhiteTurn) {
                                blackClockRunning = false;
                                whiteClockRunning = true;
                            } else {
                                whiteClockRunning = false;
                                blackClockRunning = true;
                            }
                            lastWhiteTurn = currentWhiteTurn;
                        }
                    }

                    // Draw the game board and pieces
                    networkGame.draw(batch);

                    // Draw network connection status
                    drawNetworkGameStatus();
                }
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();

        boolean gameOver = board.gameOver;



        if (mode == 3 || mode == 4 || mode == 5 || mode == 7) {
            if (!gameOver) {
                if (mode == 3 || mode == 4 || mode == 7) {
                    // Normal game mode - update both clocks based on who's turn it is

                    if (whiteClockRunning && whiteFTime > 0) {
                        whiteFTime = whiteFTime - deltaTime;
                        if (whiteFTime <= 0) {
                            whiteFTime = 0;
                        }
                    }

                    if (blackClockRunning && blackFTime > 0) {
                        blackFTime = blackFTime - deltaTime;
                        if (blackFTime <= 0) {
                            blackFTime = 0;
                        }
                    }
                } else if (mode == 5) {
                    // Promotion mode - only the promoting player's clock runs

                    if (isWhitePromotion) {
                        // White is choosing piece - only white clock runs
                        if (whiteClockRunning && whiteFTime > 0) {
                            whiteFTime = whiteFTime - deltaTime;
                            if (whiteFTime <= 0) {
                                whiteFTime = 0;
                            }
                        }
                    } else {
                        // Black is choosing piece - only black clock runs
                        if (blackClockRunning && blackFTime > 0) {
                            blackFTime = blackFTime - deltaTime;
                            if (blackFTime <= 0) {
                                blackFTime = 0;
                            }
                        }
                    }
                }
            }

            // Draw the black player's clock time
            if (blackFTime > 299) {
                blackBNumber = 5;
                String blackSRoundedS = "00";
                font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime <= 299 && blackFTime > 240) {
                blackBNumber = 4;
                blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                blackSRounded = (int) Math.ceil(blackSNumber);
                if (blackSRounded >= 1 && blackSRounded <= 9) {
                    String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else {
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime > 239 && blackFTime <= 240) {
                blackBNumber = 4;
                String blackSRoundedS = "00";
                font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime <= 239 && blackFTime > 180) {
                blackBNumber = 3;
                blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                blackSRounded = (int) Math.ceil(blackSNumber);
                if (blackSRounded >= 1 && blackSRounded <= 9) {
                    String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else {
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime > 179 && blackFTime <= 180) {
                blackBNumber = 3;
                String blackSRoundedS = "00";
                font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime <= 179 && blackFTime > 120) {
                blackBNumber = 2;
                blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                blackSRounded = (int) Math.ceil(blackSNumber);
                if (blackSRounded >= 1 && blackSRounded <= 9) {
                    String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else {
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime > 119 && blackFTime <= 120) {
                blackBNumber = 2;
                String blackSRoundedS = "00";
                font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime <= 119 && blackFTime > 60) {
                blackBNumber = 1;
                blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                blackSRounded = (int) Math.ceil(blackSNumber);
                if (blackSRounded >= 1 && blackSRounded <= 9) {
                    String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else {
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime > 59 && blackFTime <= 60) {
                blackBNumber = 1;
                String blackSRoundedS = "00";
                font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (blackFTime <= 59 && blackFTime > 0) {
                blackBNumber = 0;
                blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                blackSRounded = (int) Math.ceil(blackSNumber);
                if (blackSRounded >= 1 && blackSRounded <= 9) {
                    String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else {
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }

            // Draw the white player's clock time
            if (whiteFTime > 299) {
                whiteBNumber = 5;
                String whiteSRoundedS = "00";
                font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 485, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime <= 299 && whiteFTime > 240) {
                whiteBNumber = 4;
                whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                whiteSRounded = (int) Math.ceil(whiteSNumber);
                if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                    String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 485, 225);
                } else {
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 485, 225);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime > 239 && whiteFTime <= 240) {
                whiteBNumber = 4;
                String whiteSRoundedS = "00";
                font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 485, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime <= 239 && whiteFTime > 180) {
                whiteBNumber = 3;
                whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                whiteSRounded = (int) Math.ceil(whiteSNumber);
                if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                    String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else {
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime > 179 && whiteFTime <= 180) {
                whiteBNumber = 3;
                String whiteSRoundedS = "00";
                font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime <= 179 && whiteFTime > 120) {
                whiteBNumber = 2;
                whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                whiteSRounded = (int) Math.ceil(whiteSNumber);
                if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                    String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else {
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime > 119 && whiteFTime <= 120) {
                whiteBNumber = 2;
                String whiteSRoundedS = "00";
                font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime <= 119 && whiteFTime > 60) {
                whiteBNumber = 1;
                whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                whiteSRounded = (int) Math.ceil(whiteSNumber);
                if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                    String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else {
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime > 59 && whiteFTime <= 60) {
                whiteBNumber = 1;
                String whiteSRoundedS = "00";
                font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
            if (whiteFTime <= 59 && whiteFTime > 0) {
                whiteBNumber = 0;
                whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                whiteSRounded = (int) Math.ceil(whiteSNumber);
                if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                    String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else {
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
        }

        batch.end();
    }

    // NEW: Helper method to start a network game as HOST
    private void startNetworkGameAsHost() {
        // Get player name from the network menu
        String playerName = networkMenu.getPlayerName();
        if (playerName.isEmpty()) {
            playerName = "HostPlayer"; // Default name
        }

        // Create the network game as HOST
        networkGame = new PlayerVsNetwork(board, true, playerName);

        // Set status message
        networkStatusText = "Waiting for opponent to connect...";
        networkWaitingForConnection = true;
        networkWaitTimer = 0;

        // Switch to network game mode
        mode = 7;

        // Reset clocks for new game
        whiteClockRunning = false;
        blackClockRunning = false;
        whiteFTime = 300;
        blackFTime = 300;

        System.out.println("Started network game as HOST: " + playerName);
    }

    // NEW: Helper method to start a network game as CLIENT (join existing game)
    private void startNetworkGameAsClient() {
        // Get player name and IP address from the network menu
        String playerName = networkMenu.getPlayerName();
        if (playerName.isEmpty()) {
            playerName = "ClientPlayer"; // Default name
        }

        String ipAddress = networkMenu.getIpAddress();
        if (ipAddress.isEmpty()) {
            ipAddress = "localhost"; // Default to localhost for testing
        }

        // Create the network game as CLIENT
        networkGame = new PlayerVsNetwork(board, false, playerName);

        // Try to connect to the server
        boolean connectedSuccessfully = networkGame.connectToServer(ipAddress, 12345);

        if (connectedSuccessfully) {
            networkStatusText = "Connected! Waiting for host to start...";
            networkWaitingForConnection = false;

            // Switch to network game mode
            mode = 7;

            // Reset clocks for new game
            whiteClockRunning = false;
            blackClockRunning = false;
            whiteFTime = 300;
            blackFTime = 300;

            System.out.println("Connected to network game as CLIENT: " + playerName);
            System.out.println("Connected to server at: " + ipAddress);
        } else {
            networkStatusText = "Failed to connect. Check IP address and try again.";
            System.out.println("Failed to connect to server at: " + ipAddress);
        }
    }

    // NEW: Helper method to draw network game status information
    private void drawNetworkGameStatus() {
        // Update network wait timer if we're waiting
        if (networkWaitingForConnection) {
            networkWaitTimer += Gdx.graphics.getDeltaTime();
        }

        // Draw network connection status
        font.getData().setScale(1f, 1f);

        if (networkGame != null) {
            if (networkGame.isConnected()) {
                // Connected - show game info
                font.setColor(Color.GREEN);
                font.draw(batch, "CONNECTED", 500, 450);
                font.draw(batch, "You: " + networkGame.getPlayerName(), 500, 430);
                font.draw(batch, "Opponent: " + networkGame.getOpponentName(), 500, 410);

                // Show whose turn it is
                font.getData().setScale(1.2f, 1.2f);
                if (networkGame.isWhiteTurn()) {
                    font.setColor(Color.WHITE);
                    font.draw(batch, "WHITE'S TURN", 500, 380);
                } else {
                    font.setColor(Color.BLACK);
                    font.draw(batch, "BLACK'S TURN", 500, 380);
                }

                // Show which color the player is
                font.getData().setScale(1f, 1f);
                font.setColor(Color.LIGHT_GRAY);
                if (networkGame.isWhite()) {
                    font.draw(batch, "You are: WHITE", 500, 360);
                } else {
                    font.draw(batch, "You are: BLACK", 500, 360);
                }

                // Show any status messages from the network game
                String gameStatus = networkGame.getStatusMessage();
                if (gameStatus != null && !gameStatus.isEmpty()) {
                    font.setColor(Color.YELLOW);
                    font.draw(batch, gameStatus, 500, 340);
                }

            } else {
                // Not connected yet - show waiting message
                font.setColor(Color.YELLOW);
                font.draw(batch, "WAITING FOR CONNECTION...", 500, 450);

                // Show how long we've been waiting
                int waitSeconds = (int)networkWaitTimer;
                font.draw(batch, "Waiting: " + waitSeconds + " seconds", 500, 430);

                // If waiting too long, show timeout warning
                if (networkWaitTimer > 30) {
                    font.setColor(Color.RED);
                    font.draw(batch, "Taking too long. Check connection.", 500, 410);
                }

                // Show who is hosting
                if (networkGame != null && networkGame.isHost()) {
                    font.setColor(Color.CYAN);
                    font.draw(batch, "You are HOSTING", 500, 390);
                    font.draw(batch, "Tell opponent your IP address", 500, 370);
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        boardTexture.dispose();
        whitePawnTex.dispose();
        blackPawnTex.dispose();
        whiteRookTex.dispose();
        blackRookTex.dispose();
        whiteKnightTex.dispose();
        blackKnightTex.dispose();
        whiteBishopTex.dispose();
        blackBishopTex.dispose();
        whiteQueenTex.dispose();
        blackQueenTex.dispose();
        whiteKingTex.dispose();
        blackKingTex.dispose();
        menuTexture.dispose();
        // NEW: Dispose network menu texture
        networkMenuTexture.dispose();

        // NEW: Clean up network connections if active
        if (networkGame != null) {
            networkGame.disconnect();
        }
    }
}
// Finished working is control+k then write what I changed then commit and push
// Starting work is control+t then merge then pull
