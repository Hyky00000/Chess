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
    private ChangeGame changeGame;
    private ChangeAI changeAI;
    private FirstIncrement firstIncrement;
    private SecondIncrement secondIncrement;
    private ThirdIncrement thirdIncrement;
    private FourthIncrement fourthIncrement;
    private FifthIncrement fifthIncrement;
    private SixthIncrement sixthIncrement;
    private Back back;
    private Times times;
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
    private Texture changeGameTexture;
    private Texture changeAITexture;
    private Texture firstIncrementTexture;
    private Texture secondIncrementTexture;
    private Texture thirdIncrementTexture;
    private Texture fourthIncrementTexture;
    private Texture fifthIncrementTexture;
    private Texture sixthIncrementTexture;
    private Texture backTexture;
    private Texture timesTexture;

    private PlayerVsPlayer pvpGame;
    private PlayerVsComputer pvcGame;
    private Practice practice;
    int aiDifficulty;
    boolean playerIsWhite;

    private Piece promotingPawn = null;
    private boolean isWhitePromotion = false;
    private int previousMode = 0;
    private int aiOrPractice = -1;

    private float selectedTimeSeconds = 300;
    private int selectedTimeIndex = 4;
    private int selectedIncrementType = 0;
    private int selectedIncrementValue = 0;

    private int moveCount = 0;
    private boolean sixthIncrementApplied = false;

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
    private boolean isGameStarted = false;
    private boolean networkWaitingForConnection = false;
    private float networkWaitTimer = 0;

    private int gameTypeAfterTimeSelection = 0;

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
        changeGameTexture = new Texture("ChangeGame.png");
        changeAITexture = new Texture("changeAI.png");
        firstIncrementTexture = new Texture("FirstIncrement.png");
        secondIncrementTexture = new Texture("SecondIncrement.png");
        thirdIncrementTexture = new Texture("ThirdIncrement.png");
        fourthIncrementTexture = new Texture("FourthIncrement.png");
        fifthIncrementTexture = new Texture("FifthIncrement.png");
        sixthIncrementTexture = new Texture("SixthIncrement.png");
        timesTexture = new Texture("Times.png");
        backTexture = new Texture("Back.png");

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
        changeGame = new ChangeGame(changeGameTexture);
        changeAI = new ChangeAI(changeAITexture);
        times = new Times(timesTexture);
        firstIncrement = new FirstIncrement(firstIncrementTexture);
        secondIncrement = new SecondIncrement(secondIncrementTexture);
        thirdIncrement = new ThirdIncrement(thirdIncrementTexture);
        fourthIncrement = new FourthIncrement(fourthIncrementTexture);
        fifthIncrement = new FifthIncrement(fifthIncrementTexture);
        sixthIncrement = new SixthIncrement(sixthIncrementTexture);
        back = new Back(backTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1);
        batch.begin();


        // 0 is start, 1 is choose colour, 2 is difficulty, 3 is vs ai, 4 is pvp, 5 is pawn promotion,
        // 6 is network menu, 7 is network game, 8 is times selection, 9-14 are increment selections, 15 is practice

        if (mode >= 1 && mode <= 16) {
            back.draw(batch);
            changeGame.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (x > changeGame.getX() && x < changeGame.getX() + changeGame.getWidth() && y > changeGame.getY() && y < changeGame.getY() + changeGame.getHeight()) {
                    mode = 0;
                    isGameStarted = false;
                    pvpGame = null;
                    pvcGame = null;
                    networkGame = null;
                    practice = null;
                    resetGameState();
                    board.ResetGame();
                }
                else if(x > back.getX() && x < back.getX() + back.getWidth() && y > back.getY() && y < back.getY() + back.getHeight()){
                    if (mode == 1 || mode == 8){
                        mode = 0;
                    }
                    if (mode == 2){
                        if (aiOrPractice == 1){
                            mode = 0;
                        }
                        else if (aiOrPractice == 0) {
                            mode = 1;
                        }
                    }
                    else if (mode == 3){
                        isGameStarted = false;
                        pvpGame = null;
                        pvcGame = null;
                        networkGame = null;
                        resetGameState();
                        practice = null;
                        board.ResetGame();
                        mode = 2;
                    }
                    else if (mode == 4){
                        isGameStarted = false;
                        pvpGame = null;
                        pvcGame = null;
                        networkGame = null;

                        resetGameState();
                        board.ResetGame();
                        mode = previousMode;
                    }
                    else if (mode >= 9 && mode <= 14){
                        mode = 8;
                    }
                    else if (mode == 15){
                        isGameStarted = false;
                        pvpGame = null;
                        pvcGame = null;
                        networkGame = null;
                        resetGameState();
                        board.ResetGame();
                        mode = 2;
                    }
                }
            }
        }

        if (mode == 3) {
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (x > changeAI.getX() && x < changeAI.getX() + changeAI.getWidth() && y > changeAI.getY() && y < (changeAI.getY() + (changeAI.getHeight() / 3))) {
                    mode = 1;
                    isGameStarted = false;
                    pvcGame = null;
                } else if (x > changeAI.getX() && x < changeAI.getX() + changeAI.getWidth() && y > (changeAI.getY() + changeAI.getHeight() / 3) && y < (changeAI.getY() + (changeAI.getHeight() - (changeAI.getHeight() / 3)))) {
                    mode = 2;
                    isGameStarted = false;
                    pvcGame = null;
                }
            }
        }

        if (mode == 0) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            menu.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + menu.getHeight()) && (y > menu.getY() + menu.getHeight() - menuChoiceHeight)) {
                    gameTypeAfterTimeSelection = 0;
                    mode = 8;
                    resetGameState();
                } else if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + ((3 * menuChoiceHeight) + (2 * menuGapHeight))) &&
                    (y > menu.getY() + ((2 * menuChoiceHeight) + (2 * menuGapHeight)))) {
                    mode = 1;
                    aiOrPractice = 0;
                    resetGameState();
                } else if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + ((2 * menuChoiceHeight) + (1 * menuGapHeight))) &&
                    (y > menu.getY() + ((1 * menuChoiceHeight) + (1 * menuGapHeight)))) {
                    mode = 6;
                    networkMenu.reset();
                    resetGameState();
                } else if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + (1 * menuChoiceHeight)) &&
                    (y > menu.getY())) {
                    aiOrPractice = 1;
                    previousMode = 15;
                    mode = 2;
                    resetGameState();
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
                    playerIsWhite = clickY < colourChoice.getHeight();
                    previousMode = 1;
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

                float boxHeight = difficultyChoiceHeight;

                if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (3 * boxHeight)) && (y > difficulty.getY() + (2 * boxHeight))) {
                    if (aiOrPractice == 0) {
                        aiDifficulty = 3;
                        gameTypeAfterTimeSelection = 1;
                        previousMode = 2;
                        mode = 8;
                        resetGameState();
                    } else {
                        practice = new Practice(board, 3,
                            whiteKingTex, whiteQueenTex, whiteRookTex, whiteKnightTex, whiteBishopTex, whitePawnTex,
                            blackKingTex, blackQueenTex, blackRookTex, blackKnightTex, blackBishopTex, blackPawnTex);
                        previousMode = 2;
                        mode = 15;
                    }
                } else if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (2 * boxHeight)) && (y > difficulty.getY() + (1 * boxHeight))) {
                    if (aiOrPractice == 0) {
                        aiDifficulty = 2;
                        gameTypeAfterTimeSelection = 1;
                        previousMode = 2;
                        mode = 8;
                        resetGameState();
                    } else {
                        practice = new Practice(board, 2,
                            whiteKingTex, whiteQueenTex, whiteRookTex, whiteKnightTex, whiteBishopTex, whitePawnTex,
                            blackKingTex, blackQueenTex, blackRookTex, blackKnightTex, blackBishopTex, blackPawnTex);
                        previousMode = 2;
                        mode = 15;
                    }
                } else if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (1 * boxHeight)) && (y > difficulty.getY())) {
                    if (aiOrPractice == 0) {
                        aiDifficulty = 1;
                        gameTypeAfterTimeSelection = 1;
                        previousMode = 2;
                        mode = 8;
                        resetGameState();
                    } else {
                        practice = new Practice(board, 1,
                            whiteKingTex, whiteQueenTex, whiteRookTex, whiteKnightTex, whiteBishopTex, whitePawnTex,
                            blackKingTex, blackQueenTex, blackRookTex, blackKnightTex, blackBishopTex, blackPawnTex);
                        previousMode = 2;
                        mode = 15;
                    }
                }
            }
        }
        else if (mode == 15) {
            if (practice == null) {
                mode = 2;
            } else {
                if (Gdx.input.justTouched()) {
                    float x = Gdx.input.getX();
                    float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                    System.out.println(x);
                    if (practice.isMoveMade()) {
                        practice.reset();
                    } else {
                        practice.click(x, y);
                    }
                }
                practice.draw(batch);
            }
        }
        else if (mode == 8) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            times.draw(batch);

            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= times.getX() && x <= times.getX() + times.getWidth() &&
                    y >= times.getY() && y <= times.getY() + times.getHeight()) {

                    float boxHeight = times.getHeight() / 13f;
                    int boxIndex = -1;

                    if (y < times.getY() + times.getHeight() - boxHeight && y > times.getY() + times.getHeight() - (boxHeight * 2)) {
                        boxIndex = 1;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 2) && y > times.getY() + times.getHeight() - (boxHeight * 3)) {
                        boxIndex = 2;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 3) && y > times.getY() + times.getHeight() - (boxHeight * 4)) {
                        boxIndex = 3;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 4) && y > times.getY() + times.getHeight() - (boxHeight * 5)) {
                        boxIndex = 4;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 5) && y > times.getY() + times.getHeight() - (boxHeight * 6)) {
                        boxIndex = 5;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 6) && y > times.getY() + times.getHeight() - (boxHeight * 7)) {
                        boxIndex = 6;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 7) && y > times.getY() + times.getHeight() - (boxHeight * 8)) {
                        boxIndex = 7;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 8) && y > times.getY() + times.getHeight() - (boxHeight * 9)) {
                        boxIndex = 8;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 9) && y > times.getY() + times.getHeight() - (boxHeight * 10)) {
                        boxIndex = 9;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 10) && y > times.getY() + times.getHeight() - (boxHeight * 11)) {
                        boxIndex = 10;
                    } else if (y < times.getY() + times.getHeight() - (boxHeight * 11) && y > times.getY() + times.getHeight() - (boxHeight * 12)) {
                        boxIndex = 11;
                    }

                    if (boxIndex >= 1 && boxIndex <= 11) {
                        selectedTimeIndex = boxIndex - 1;

                        switch (selectedTimeIndex) {
                            case 0:
                                selectedTimeSeconds = 30;
                                break;
                            case 1:
                                selectedTimeSeconds = 60;
                                break;
                            case 2:
                                selectedTimeSeconds = 120;
                                break;
                            case 3:
                                selectedTimeSeconds = 180;
                                break;
                            case 4:
                                selectedTimeSeconds = 300;
                                break;
                            case 5:
                                selectedTimeSeconds = 600;
                                break;
                            case 6:
                                selectedTimeSeconds = 900;
                                break;
                            case 7:
                                selectedTimeSeconds = 1800;
                                break;
                            case 8:
                                selectedTimeSeconds = 3600;
                                break;
                            case 9:
                                selectedTimeSeconds = 5400;
                                break;
                            case 10:
                                selectedTimeSeconds = Float.MAX_VALUE;
                                break;
                        }

                        if (selectedTimeIndex >= 0 && selectedTimeIndex <= 2) {
                            mode = 9;
                            previousMode = 9;
                        } else if (selectedTimeIndex == 3) {
                            mode = 10;
                            previousMode = 10;
                        } else if (selectedTimeIndex == 4) {
                            mode = 11;
                            previousMode = 11;
                        } else if (selectedTimeIndex == 5) {
                            mode = 12;
                            previousMode = 12;
                        } else if (selectedTimeIndex == 6 || selectedTimeIndex == 7) {
                            mode = 13;
                            previousMode = 13;
                        } else if (selectedTimeIndex >= 8 && selectedTimeIndex <= 9) {
                            mode = 14;
                            previousMode = 14;
                        } else if (selectedTimeIndex == 10) {
                            startGameWithSelectedTime();
                        }
                    }
                }
            }
        } else if (mode == 9) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            firstIncrement.draw(batch);

            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= firstIncrement.getX() && x <= firstIncrement.getX() + firstIncrement.getWidth() &&
                    y >= firstIncrement.getY() && y <= firstIncrement.getY() + firstIncrement.getHeight()) {

                    if (y > firstIncrement.getY() + (firstIncrement.getHeight() / 2) && y < firstIncrement.getY() + firstIncrement.getHeight()) {
                        selectedIncrementType = 1;
                        selectedIncrementValue = 0;
                    } else {
                        selectedIncrementType = 1;
                        selectedIncrementValue = 1;
                    }

                    startGameWithSelectedTime();
                }
            }
        } else if (mode == 10) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            secondIncrement.draw(batch);

            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= secondIncrement.getX() && x <= secondIncrement.getX() + secondIncrement.getWidth() &&
                    y >= secondIncrement.getY() && y <= secondIncrement.getY() + secondIncrement.getHeight()) {

                    if (y > secondIncrement.getY() + (secondIncrement.getHeight() / 2) && y < secondIncrement.getY() + secondIncrement.getHeight()) {
                        selectedIncrementType = 2;
                        selectedIncrementValue = 0;
                    } else {
                        selectedIncrementType = 2;
                        selectedIncrementValue = 2;
                    }

                    startGameWithSelectedTime();
                }
            }
        } else if (mode == 11) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            thirdIncrement.draw(batch);

            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= thirdIncrement.getX() && x <= thirdIncrement.getX() + thirdIncrement.getWidth() &&
                    y >= thirdIncrement.getY() && y <= thirdIncrement.getY() + thirdIncrement.getHeight()) {

                    float thirdHeight = thirdIncrement.getHeight() / 3f;

                    if (y > thirdIncrement.getY() + (2 * thirdHeight) && y < thirdIncrement.getY() + (3 * thirdHeight)) {
                        selectedIncrementType = 3;
                        selectedIncrementValue = 3;
                    } else if (y > thirdIncrement.getY() + thirdHeight && y < thirdIncrement.getY() + (2 * thirdHeight)) {
                        selectedIncrementType = 3;
                        selectedIncrementValue = 2;
                    } else {
                        selectedIncrementType = 3;
                        selectedIncrementValue = 0;
                    }
                    startGameWithSelectedTime();
                }
            }
        } else if (mode == 12) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            fourthIncrement.draw(batch);

            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= fourthIncrement.getX() && x <= fourthIncrement.getX() + fourthIncrement.getWidth() &&
                    y >= fourthIncrement.getY() && y <= fourthIncrement.getY() + fourthIncrement.getHeight()) {

                    if (y > fourthIncrement.getY() + (fourthIncrement.getHeight() / 2) && y < fourthIncrement.getY() + fourthIncrement.getHeight()) {
                        selectedIncrementType = 4;
                        selectedIncrementValue = 300;
                    } else {
                        selectedIncrementType = 4;
                        selectedIncrementValue = 0;
                    }

                    startGameWithSelectedTime();
                }
            }
        } else if (mode == 13) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            fifthIncrement.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (x >= fifthIncrement.getX() && x <= fifthIncrement.getX() + fifthIncrement.getWidth() &&
                    y >= fifthIncrement.getY() && y <= fifthIncrement.getY() + fifthIncrement.getHeight()) {
                    float thirdHeight = fifthIncrement.getHeight() / 3;
                    if (y > fifthIncrement.getY() + (2 * thirdHeight) && y < fifthIncrement.getY() + (3 * thirdHeight)) {
                        selectedIncrementType = 5;
                        selectedIncrementValue = 10;
                    } else if (y > fifthIncrement.getY() + thirdHeight && y < fifthIncrement.getY() + (2 * thirdHeight)) {
                        selectedIncrementType = 5;
                        selectedIncrementValue = 5;
                    } else {
                        selectedIncrementType = 5;
                        selectedIncrementValue = 0;
                    }
                    startGameWithSelectedTime();
                }
            }
        } else if (mode == 14) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            sixthIncrement.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                float fifthHeight = sixthIncrement.getHeight() / 5f;
                if (x >= sixthIncrement.getX() && x <= sixthIncrement.getX() + sixthIncrement.getWidth() &&
                    y >= sixthIncrement.getY() && y <= sixthIncrement.getY() + sixthIncrement.getHeight() - fifthHeight) {
                    if (y > sixthIncrement.getY() + (fifthHeight * 3) && y < sixthIncrement.getY() + (4 * fifthHeight)) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 0;
                        System.out.println("1");
                    } else if (y > sixthIncrement.getY() + (2 * fifthHeight) && y < sixthIncrement.getY() + (3 * fifthHeight)) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 1;
                        System.out.println("2");
                    } else if (y > sixthIncrement.getY() + (1 * fifthHeight) && y < sixthIncrement.getY() + (2 * fifthHeight)) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 2;
                        System.out.println("3");
                    } else if (y > sixthIncrement.getY() && y < sixthIncrement.getY() + fifthHeight) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 3;
                        System.out.println("4");
                    }
                    startGameWithSelectedTime();
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
                            applyIncrement(false);
                            moveCount++;

                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                            applyIncrement(true);
                            moveCount++;
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
                            applyIncrement(false);
                            moveCount++;
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                            applyIncrement(true);
                            moveCount++;
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
                            applyIncrement(false);
                            moveCount++;
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                            applyIncrement(true);
                            moveCount++;
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
            int pawnCol = (int) ((pawnX - board.boardX - board.borderOffsetX) / board.squareSize);
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
                    int choice = (int) (relativeY / sectionHeight);
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
                        mode = 7;
                        if (networkGame != null) {
                            networkGame.sendPromotionChoice(choice);
                        }
                    }
                    promotingPawn = null;
                    board.promotingPawn = null;
                }
            }
        } else if (mode == 6) {
            board.draw(batch);
            board.drawCapturedPieces(batch);
            networkMenu.draw(batch);

            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= networkMenu.getX() && x <= networkMenu.getX() + networkMenu.getWidth() &&
                    y >= networkMenu.getY() && y <= networkMenu.getY() + networkMenu.getHeight()) {

                    networkMenu.handleClick(x, y);

                    if (networkMenu.wantsToHost() && networkMenu.isReady()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                startNetworkGameAsHost();
                            }
                        }).start();
                        mode = 7;
                    } else if (networkMenu.wantsToJoin() && networkMenu.isReady()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                startNetworkGameAsClient();
                            }
                        }).start();
                        mode = 7;
                    }

                } else {
                    networkMenu.stopTyping();
                }
            }

            if (networkMenu.isTyping()) {
                if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.BACKSPACE)) {
                    networkMenu.removeLastCharacter();
                }
            }
        } else if (mode == 7) {
            if (networkGame == null) {
                board.draw(batch);
                board.drawCapturedPieces(batch);

                if (networkWaitingForConnection) {
                    networkWaitTimer += Gdx.graphics.getDeltaTime();
                    if ((int) networkWaitTimer % 5 == 0 && (int) networkWaitTimer > 0) {
                        System.out.println("Waiting for opponent... " + (int) networkWaitTimer + " seconds");
                    }
                }
            } else {
                networkGame.update();

                if (board.promotingPawn != null) {
                    promotingPawn = board.promotingPawn;
                    isWhitePromotion = (promotingPawn.getColour() == PieceColour.WHITE);
                    previousMode = 7;
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
                        boolean moveMade = networkGame.click(x, y);
                        boolean currentWhiteTurn = networkGame.isWhiteTurn();
                        if (moveMade && currentWhiteTurn != lastWhiteTurn) {
                            if (currentWhiteTurn) {
                                blackClockRunning = false;
                                whiteClockRunning = true;
                                applyIncrement(false);
                                moveCount++;
                            } else {
                                whiteClockRunning = false;
                                blackClockRunning = true;
                                applyIncrement(true);
                                moveCount++;
                            }
                            lastWhiteTurn = currentWhiteTurn;
                        }
                    }

                    networkGame.draw(batch);
                }
            }
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean gameOver = board.gameOver;
        if ((mode == 3 || mode == 4 || mode == 5 || mode == 7) && isGameStarted) {
            if (!gameOver) {
                if (mode == 3 || mode == 4 || mode == 7) {
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
                    if (isWhitePromotion) {
                        if (whiteClockRunning && whiteFTime > 0) {
                            whiteFTime = whiteFTime - deltaTime;
                            if (whiteFTime <= 0) {
                                whiteFTime = 0;
                            }
                        }
                    } else {
                        if (blackClockRunning && blackFTime > 0) {
                            blackFTime = blackFTime - deltaTime;
                            if (blackFTime <= 0) {
                                blackFTime = 0;
                            }
                        }
                    }
                }
            }

            if (selectedTimeIndex == 10 && selectedTimeSeconds == Float.MAX_VALUE) {
                font.draw(batch, " ", 490, 280); //Used to be infinite
                font.draw(batch, " ", 485, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            } else {
                if (blackFTime > 3599) {
                    int hours = (int) (blackFTime / 3600);
                    int minutes = (int) ((blackFTime % 3600) / 60);
                    int seconds = (int) (blackFTime % 60);
                    String minutesStr = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
                    String secondsStr = (seconds < 10) ? "0" + Integer.toString(seconds) : Integer.toString(seconds);
                    font.draw(batch, Integer.toString(hours) + ":" + minutesStr + ":" + secondsStr, 490, 280);
                } else if (blackFTime > 299) {
                    blackBNumber = (int) (blackFTime / 60);
                    String blackSRoundedS = "00";
                    font.draw(batch,Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else if (blackFTime <= 299 && blackFTime > 240) {
                    blackBNumber = 4;
                    blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                    blackSRounded = (int) Math.ceil(blackSNumber);
                    if (blackSRounded >= 1 && blackSRounded <= 9) {
                        String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                    } else {
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                    }
                } else if (blackFTime > 239 && blackFTime <= 240) {
                    blackBNumber = 4;
                    String blackSRoundedS = "00";
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else if (blackFTime <= 239 && blackFTime > 180) {
                    blackBNumber = 3;
                    blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                    blackSRounded = (int) Math.ceil(blackSNumber);
                    if (blackSRounded >= 1 && blackSRounded <= 9) {
                        String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                    } else {
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                    }
                } else if (blackFTime > 179 && blackFTime <= 180) {
                    blackBNumber = 3;
                    String blackSRoundedS = "00";
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else if (blackFTime <= 179 && blackFTime > 120) {
                    blackBNumber = 2;
                    blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                    blackSRounded = (int) Math.ceil(blackSNumber);
                    if (blackSRounded >= 1 && blackSRounded <= 9) {
                        String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                    } else {
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                    }
                } else if (blackFTime > 119 && blackFTime <= 120) {
                    blackBNumber = 2;
                    String blackSRoundedS = "00";
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else if (blackFTime <= 119 && blackFTime > 60) {
                    blackBNumber = 1;
                    blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                    blackSRounded = (int) Math.ceil(blackSNumber);
                    if (blackSRounded >= 1 && blackSRounded <= 9) {
                        String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                    } else {
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                    }
                } else if (blackFTime > 59 && blackFTime <= 60) {
                    blackBNumber = 1;
                    String blackSRoundedS = "00";
                    font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                } else if (blackFTime <= 59 && blackFTime > 0) {
                    blackBNumber = 0;
                    blackSNumber = ((blackFTime / 60) - blackBNumber) * 60;
                    blackSRounded = (int) Math.ceil(blackSNumber);
                    if (blackSRounded >= 1 && blackSRounded <= 9) {
                        String blackSRoundedS = "0" + Integer.toString(blackSRounded);
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + blackSRoundedS, 490, 280);
                    } else {
                        font.draw(batch, "Black: " + Integer.toString(blackBNumber) + ":" + Integer.toString(blackSRounded), 490, 280);
                    }
                }

                if (whiteFTime > 3599) {
                    int hours = (int) (whiteFTime / 3600);
                    int minutes = (int) ((whiteFTime % 3600) / 60);
                    int seconds = (int) (whiteFTime % 60);
                    String minutesStr = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
                    String secondsStr = (seconds < 10) ? "0" + Integer.toString(seconds) : Integer.toString(seconds);
                    font.draw(batch, Integer.toString(hours) + ":" + minutesStr + ":" + secondsStr, 490, 225);
                } else if (whiteFTime > 299) {
                    whiteBNumber = (int) (whiteFTime / 60);
                    String whiteSRoundedS = "00";
                    font.draw(batch,Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 485, 225);
                } else if (whiteFTime <= 299 && whiteFTime > 240) {
                    whiteBNumber = 4;
                    whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                    whiteSRounded = (int) Math.ceil(whiteSNumber);
                    if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                        String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 485, 225);
                    } else {
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 485, 225);
                    }
                } else if (whiteFTime > 239 && whiteFTime <= 240) {
                    whiteBNumber = 4;
                    String whiteSRoundedS = "00";
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 485, 225);
                } else if (whiteFTime <= 239 && whiteFTime > 180) {
                    whiteBNumber = 3;
                    whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                    whiteSRounded = (int) Math.ceil(whiteSNumber);
                    if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                        String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                    } else {
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                    }
                } else if (whiteFTime > 179 && whiteFTime <= 180) {
                    whiteBNumber = 3;
                    String whiteSRoundedS = "00";
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else if (whiteFTime <= 179 && whiteFTime > 120) {
                    whiteBNumber = 2;
                    whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                    whiteSRounded = (int) Math.ceil(whiteSNumber);
                    if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                        String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                    } else {
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                    }
                } else if (whiteFTime > 119 && whiteFTime <= 120) {
                    whiteBNumber = 2;
                    String whiteSRoundedS = "00";
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else if (whiteFTime <= 119 && whiteFTime > 60) {
                    whiteBNumber = 1;
                    whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                    whiteSRounded = (int) Math.ceil(whiteSNumber);
                    if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                        String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                    } else {
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                    }
                } else if (whiteFTime > 59 && whiteFTime <= 60) {
                    whiteBNumber = 1;
                    String whiteSRoundedS = "00";
                    font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                } else if (whiteFTime <= 59 && whiteFTime > 0) {
                    whiteBNumber = 0;
                    whiteSNumber = ((whiteFTime / 60) - whiteBNumber) * 60;
                    whiteSRounded = (int) Math.ceil(whiteSNumber);
                    if (whiteSRounded >= 1 && whiteSRounded <= 9) {
                        String whiteSRoundedS = "0" + Integer.toString(whiteSRounded);
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + whiteSRoundedS, 490, 225);
                    } else {
                        font.draw(batch, "White: " + Integer.toString(whiteBNumber) + ":" + Integer.toString(whiteSRounded), 490, 225);
                    }
                }
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            }
        }
        batch.end();
    }

    private void applyIncrement(boolean isWhite) {
        if (selectedIncrementType != 6) {
            if (selectedIncrementType != 0 && selectedIncrementValue > 0) {
                if (isWhite) {
                    whiteFTime += selectedIncrementValue;
                } else {
                    blackFTime += selectedIncrementValue;
                }
            }
        }
        else if (selectedIncrementType == 6) {
            if (selectedIncrementValue == 1) {
                if (isWhite) {
                    whiteFTime += 30;
                } else {
                    blackFTime += 30;
                }
            }
            else if (selectedIncrementValue == 2) {
                if (!sixthIncrementApplied) {
                    if (isWhite && moveCount == 77) {
                        whiteFTime += 1800;
                        sixthIncrementApplied = true;
                    } else if (!isWhite && moveCount == 78) {
                        blackFTime += 1800;
                        sixthIncrementApplied = true;
                    }
                }
            }
            else if (selectedIncrementValue == 3) {
                if (moveCount >= 80) {
                    if (!sixthIncrementApplied) {
                        if (isWhite && moveCount == 79) {
                            whiteFTime += 1800;
                        } else if (!isWhite && moveCount == 80) {
                            blackFTime += 1800;
                        }
                        sixthIncrementApplied = true;
                    }
                    if (isWhite) {
                        whiteFTime += 30;
                    } else {
                        blackFTime += 30;
                    }
                }
            }
        }
    }

    private void startGameWithSelectedTime() {
        whiteFTime = selectedTimeSeconds;
        blackFTime = selectedTimeSeconds;
        moveCount = 0;
        sixthIncrementApplied = false;
        isGameStarted = true;

        whiteClockRunning = false;
        blackClockRunning = false;
        lastWhiteTurn = true;

        if (gameTypeAfterTimeSelection == 0) {
            pvpGame = new PlayerVsPlayer(board);
            mode = 4;
        } else {
            pvcGame = new PlayerVsComputer(board, playerIsWhite, aiDifficulty);
            mode = 3;
        }
    }

    private void resetGameState() {
        whiteFTime = selectedTimeSeconds;
        blackFTime = selectedTimeSeconds;
        moveCount = 0;
        sixthIncrementApplied = false;
        whiteClockRunning = false;
        blackClockRunning = false;
        lastWhiteTurn = true;
        isGameStarted = false;
    }

    private void startNetworkGameAsHost() {
        String playerName = networkMenu.getPlayerName();
        if (playerName.isEmpty()) {
            playerName = "HostPlayer";
        }

        networkGame = new PlayerVsNetwork(board, true, playerName);
        networkWaitingForConnection = true;
        networkWaitTimer = 0;
        resetGameState();
        System.out.println("Started network game as HOST: " + playerName);
    }

    private void startNetworkGameAsClient() {
        String playerName = networkMenu.getPlayerName();
        if (playerName.isEmpty()) {
            playerName = "ClientPlayer";
        }

        String ipAddress = networkMenu.getIpAddress();
        if (ipAddress.isEmpty()) {
            ipAddress = "localhost";
        }

        networkGame = new PlayerVsNetwork(board, false, playerName);
        boolean connectedSuccessfully = networkGame.connectToServer(ipAddress, 12345);

        if (connectedSuccessfully) {
            networkWaitingForConnection = false;
            resetGameState();
            System.out.println("Connected to network game as CLIENT: " + playerName);
        } else {
            System.out.println("Failed to connect to server at: " + ipAddress);
            mode = 6;
            networkGame = null;
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
        networkMenuTexture.dispose();
        if (networkGame != null) {
            networkGame.disconnect();
        }
    }
}
// Finished working is control+k then write what I changed then commit and push
// Starting work is control+t then merge then pull
// 640 x 480
// PC IPv4: 192.168.137.1
// laptop IPv4: 192.168.0.68.
// fix the ai
// dont forget about change AI photo png
