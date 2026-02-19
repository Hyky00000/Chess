package io.github.some_example_name;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Board board;
    private Menu menu;
    private ColourChoice colourChoice;
    private WhitePromotion whitePromotion;
    private BlackPromotion blackPromotion;
    Difficulty difficulty;
    private ChangeGame changeGame;

    private FirstIncrement firstIncrement;
    private SecondIncrement secondIncrement;
    private ThirdIncrement thirdIncrement;
    private FourthIncrement fourthIncrement;
    private FifthIncrement fifthIncrement;
    private SixthIncrement sixthIncrement;
    private NextPuzzle nextPuzzle;
    private Back back;
    private Times times;
    private int mode = 0;
    float difficultyChoiceHeight = 60.570370370370370370f;

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
    private Texture firstIncrementTexture;
    private Texture secondIncrementTexture;
    private Texture thirdIncrementTexture;
    private Texture fourthIncrementTexture;
    private Texture fifthIncrementTexture;
    private Texture sixthIncrementTexture;
    private Texture nextPuzzleTexture;
    private Texture backTexture;
    private Texture timesTexture;
    private BlackToPlay blackToPlay;
    private WhiteToPlay whiteToPlay;
    private Texture blackToPlayTexture;
    private Texture whiteToPlayTexture;

    private PlayerVsPlayer pvpGame;
    private PlayerVsComputer pvcGame;
    private Practice practice;
    int aiDifficulty;
    boolean playerIsWhite;
    private boolean inPromotion = false;

    private Piece promotingPawn = null;
    private boolean isWhitePromotion = false;
    private int previousMode = 0;
    private int aiOrPractice = -1;

    private float selectedTimeSeconds = 300;
    private int selectedTimeIndex = 4;
    private int selectedIncrementType = 0;
    private int selectedIncrementValue = 0;

    private int moveCount = 0;

    private float blackFTime;
    private float whiteFTime;
    private boolean whiteClockRunning = false;
    private boolean blackClockRunning = false;
    private BitmapFont font;

    private boolean lastWhiteTurn = true;
    private boolean isGameStarted = false;
    private boolean checkPrinted = false;

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
        changeGameTexture = new Texture("ChangeGame.png");
        firstIncrementTexture = new Texture("FirstIncrement.png");
        secondIncrementTexture = new Texture("SecondIncrement.png");
        thirdIncrementTexture = new Texture("ThirdIncrement.png");
        fourthIncrementTexture = new Texture("FourthIncrement.png");
        fifthIncrementTexture = new Texture("FifthIncrement.png");
        sixthIncrementTexture = new Texture("SixthIncrement.png");
        nextPuzzleTexture = new Texture("NextPuzzle.png");
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

        blackToPlayTexture = new Texture("BlackToPlay.png");
        whiteToPlayTexture = new Texture("WhiteToPlay.png");
        blackToPlay = new BlackToPlay(blackToPlayTexture);
        whiteToPlay = new WhiteToPlay(whiteToPlayTexture);

        board = new Board(boardTexture, whitePawnTex, blackPawnTex, whiteRookTex, blackRookTex, whiteKnightTex, blackKnightTex, whiteBishopTex, blackBishopTex, whiteQueenTex, blackQueenTex, whiteKingTex, blackKingTex);
        menu = new Menu(menuTexture);
        colourChoice = new ColourChoice(colourChoiceTexture);
        difficulty = new Difficulty(difficultyTexture);
        whitePromotion = new WhitePromotion(whitePromotionTexture);
        blackPromotion = new BlackPromotion(blackPromotionTexture);
        changeGame = new ChangeGame(changeGameTexture);
        times = new Times(timesTexture);
        firstIncrement = new FirstIncrement(firstIncrementTexture);
        secondIncrement = new SecondIncrement(secondIncrementTexture);
        thirdIncrement = new ThirdIncrement(thirdIncrementTexture);
        fourthIncrement = new FourthIncrement(fourthIncrementTexture);
        fifthIncrement = new FifthIncrement(fifthIncrementTexture);
        sixthIncrement = new SixthIncrement(sixthIncrementTexture);
        nextPuzzle = new NextPuzzle(nextPuzzleTexture);
        back = new Back(backTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1);
        batch.begin();

        // 0 is start, 1 is choose colour, 2 is difficulty, 3 is vs ai, 4 is pvp, 5 is pawn promotion,
        // 8 is times selection, 9-14 are increment selections, 15 is practice

        if (mode != previousMode) {
            System.out.println("Mode = " + mode);
            previousMode = mode;
        }

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
                    practice = null;
                    resetGameState();
                    board.ResetGame();
                }
                else if(x > back.getX() && x < back.getX() + back.getWidth() && y > back.getY() && y < back.getY() + back.getHeight()){
                    if (mode == 1){
                        mode = 0;
                    }
                    else if (mode == 2){
                        if (aiOrPractice == 1){
                            mode = 0;
                        }
                        else if (aiOrPractice == 0) {
                            mode = 1;
                        }
                    }
                    else if (mode == 8){
                        if (gameTypeAfterTimeSelection == 1) {
                            mode = 2;
                        } else {
                            mode = 0;
                        }
                    }
                    else if (mode == 3){
                        isGameStarted = false;
                        pvpGame = null;
                        pvcGame = null;
                        resetGameState();
                        board.ResetGame();
                        practice = null;
                        if (selectedTimeIndex >= 0 && selectedTimeIndex <= 2) {
                            mode = 9;
                        } else if (selectedTimeIndex == 3) {
                            mode = 10;
                        } else if (selectedTimeIndex == 4) {
                            mode = 11;
                        } else if (selectedTimeIndex == 5) {
                            mode = 12;
                        } else if (selectedTimeIndex == 6 || selectedTimeIndex == 7) {
                            mode = 13;
                        } else if (selectedTimeIndex >= 8 && selectedTimeIndex <= 9) {
                            mode = 14;
                        } else if (selectedTimeIndex == 10) {
                            mode = 8;
                        }
                    }
                    else if (mode == 4){
                        isGameStarted = false;
                        pvpGame = null;
                        pvcGame = null;
                        resetGameState();
                        board.ResetGame();
                        mode = 8;
                    }
                    else if (mode >= 9 && mode <= 14){
                        mode = 8;
                    }
                    else if (mode == 15){
                        isGameStarted = false;
                        pvpGame = null;
                        pvcGame = null;
                        resetGameState();
                        board.ResetGame();
                        practice = null;
                        mode = 2;
                    }
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
                    (y > menu.getY()) && (y < menu.getY() + menu.getHeight())) {

                    if (y > menu.getY() && y < menu.getY() + (menu.getHeight() / 4)) {
                        aiOrPractice = 1;
                        previousMode = 15;
                        mode = 2;
                        resetGameState();
                    }
                    else if (y > menu.getY() + (menu.getHeight() / 4) && y < menu.getY() + (2 * (menu.getHeight() / 4))) {
                        mode = 1;
                        aiOrPractice = 0;
                        resetGameState();
                    }
                    else if (y > menu.getY() + (2 * (menu.getHeight() / 4)) && y < menu.getY() + (3 * (menu.getHeight() / 4))) {
                        gameTypeAfterTimeSelection = 0;
                        mode = 8;
                        resetGameState();
                    }
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
                    if (playerIsWhite){
                        System.out.println("White");
                    }
                    else {
                        System.out.println("black");
                    }
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
                        aiDifficulty = 1;
                        System.out.println("Easy");
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
                        System.out.println("Easy");
                    }
                } else if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (2 * boxHeight)) && (y > difficulty.getY() + (1 * boxHeight))) {
                    if (aiOrPractice == 0) {
                        aiDifficulty = 2;
                        System.out.println("medium");
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
                        System.out.println("medium");
                    }
                } else if ((x > difficulty.getX()) && (x < difficulty.getX() + difficulty.getWidth()) &&
                    (y < difficulty.getY() + (1 * boxHeight)) && (y > difficulty.getY())) {
                    if (aiOrPractice == 0) {
                        aiDifficulty = 3;
                        System.out.println("Hard");
                        gameTypeAfterTimeSelection = 1;
                        previousMode = 2;
                        mode = 8;
                        resetGameState();
                    } else {
                        practice = new Practice(board, 3,
                            whiteKingTex, whiteQueenTex, whiteRookTex, whiteKnightTex, whiteBishopTex, whitePawnTex,
                            blackKingTex, blackQueenTex, blackRookTex, blackKnightTex, blackBishopTex, blackPawnTex);
                        previousMode = 2;
                        System.out.println("Hard");
                        mode = 15;
                    }
                }
            }
        }
        else if (mode == 15) {
            if (practice == null) {
                mode = 2;
            } else {
                if (practice.isMoveMade()) {
                    practice.draw(batch);
                    nextPuzzle.draw(batch);
                    if (Gdx.input.justTouched()) {
                        float x = Gdx.input.getX();
                        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                        if ((x > nextPuzzle.getX()) && (x < nextPuzzle.getX() + nextPuzzle.getWidth()) &&
                            (y > nextPuzzle.getY()) && (y < (nextPuzzle.getY() + ((nextPuzzle.getHeight()/4)*3)))) {
                            if (practice.difficulty == 1) {
                                practice.easyPositionCounter = practice.easyPositionCounter + 1;
                                if (practice.easyPositionCounter > 3) {
                                    practice.easyPositionCounter = 1;
                                }
                            } else if (practice.difficulty == 2) {
                                practice.mediumPositionCounter = practice.mediumPositionCounter + 1;
                                if (practice.mediumPositionCounter > 3) {
                                    practice.mediumPositionCounter = 1;
                                }
                            } else if (practice.difficulty == 3) {
                                practice.hardPositionCounter = practice.hardPositionCounter + 1;
                                if (practice.hardPositionCounter > 3) {
                                    practice.hardPositionCounter = 1;
                                }
                            }
                            practice.reset();
                        }
                    }
                } else {
                    if (Gdx.input.justTouched()) {
                        float x = Gdx.input.getX();
                        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                        practice.click(x, y);
                    }
                    practice.draw(batch);
                    if (practice.puzzleForWhite) {
                        whiteToPlay.draw(batch);
                    } else {
                        blackToPlay.draw(batch);
                    }
                }
            }
        } else if (mode == 8) {
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
                        selectedIncrementValue = 0;
                    } else if (y > thirdIncrement.getY() + thirdHeight && y < thirdIncrement.getY() + (2 * thirdHeight)) {
                        selectedIncrementType = 3;
                        selectedIncrementValue = 2;
                    } else {
                        selectedIncrementType = 3;
                        selectedIncrementValue = 3;
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
                        selectedIncrementValue = 0;
                    } else {
                        selectedIncrementType = 4;
                        selectedIncrementValue = 5;
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
                        selectedIncrementValue = 0;
                    } else if (y > fifthIncrement.getY() + thirdHeight && y < fifthIncrement.getY() + (2 * thirdHeight)) {
                        selectedIncrementType = 5;
                        selectedIncrementValue = 5;
                    } else {
                        selectedIncrementType = 5;
                        selectedIncrementValue = 10;
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
                    } else if (y > sixthIncrement.getY() + (2 * fifthHeight) && y < sixthIncrement.getY() + (3 * fifthHeight)) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 1;
                    } else if (y > sixthIncrement.getY() + (1 * fifthHeight) && y < sixthIncrement.getY() + (2 * fifthHeight)) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 2;
                    } else if (y > sixthIncrement.getY() && y < sixthIncrement.getY() + fifthHeight) {
                        selectedIncrementType = 6;
                        selectedIncrementValue = 3;
                    }
                    startGameWithSelectedTime();
                }
            }
        } else if (mode == 3) {
            if (pvcGame == null) {
                pvcGame = new PlayerVsComputer(board, playerIsWhite, aiDifficulty);
            }
            if (board.promotingPawn != null && !inPromotion) {
                inPromotion = true;
                promotingPawn = board.promotingPawn;
                isWhitePromotion = (promotingPawn.getColour() == PieceColour.WHITE);
                if (isWhitePromotion) {
                    whiteClockRunning = true;
                    blackClockRunning = false;
                } else {
                    whiteClockRunning = false;
                    blackClockRunning = true;
                }
            }

            if (inPromotion) {
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
                    float menuWidth = isWhitePromotion ? whitePromotion.getWidth() : blackPromotion.getWidth();
                    float menuHeight = isWhitePromotion ? whitePromotion.getHeight() : blackPromotion.getHeight();

                    if (x >= menuX && x <= menuX + menuWidth && y >= menuY && y <= menuY + menuHeight) {
                        float sectionHeight = menuHeight / 4;
                        int choice;
                        if (y > menuY + 3 * sectionHeight) {
                            choice = 0;
                        } else if (y > menuY + 2 * sectionHeight) {
                            choice = 1;
                        } else if (y > menuY + sectionHeight) {
                            choice = 2;
                        } else {
                            choice = 3;
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

                        inPromotion = false;
                        promotingPawn = null;
                    }
                }
            } else {
                boolean shouldAIMove = (playerIsWhite && !pvcGame.isWhiteTurn()) || (!playerIsWhite && pvcGame.isWhiteTurn());
                if (shouldAIMove && !board.gameOver) {
                    pvcGame.makeAIMove();
                    boolean currentWhiteTurn = pvcGame.isWhiteTurn();
                    if (currentWhiteTurn != lastWhiteTurn) {
                        if (currentWhiteTurn) {
                            blackClockRunning = false;
                            whiteClockRunning = true;
                            applyIncrement(false);
                            moveCount++;
                            float halfMove = moveCount / 2.0f;
                            System.out.println("Move: " + halfMove);
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                            applyIncrement(true);
                            moveCount++;
                            float halfMove = moveCount / 2.0f;
                            System.out.println("Move: " + halfMove);
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
                            float halfMove = moveCount / 2.0f;
                            System.out.println("Move: " + halfMove);
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                            applyIncrement(true);
                            moveCount++;
                            float halfMove = moveCount / 2.0f;
                            System.out.println("Move: " + halfMove);
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
            if (board.promotingPawn != null && !inPromotion) {
                inPromotion = true;
                promotingPawn = board.promotingPawn;
                isWhitePromotion = (promotingPawn.getColour() == PieceColour.WHITE);
                if (isWhitePromotion) {
                    whiteClockRunning = true;
                    blackClockRunning = false;
                } else {
                    whiteClockRunning = false;
                    blackClockRunning = true;
                }
            }

            if (inPromotion) {
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
                    float menuWidth = isWhitePromotion ? whitePromotion.getWidth() : blackPromotion.getWidth();
                    float menuHeight = isWhitePromotion ? whitePromotion.getHeight() : blackPromotion.getHeight();

                    if (x >= menuX && x <= menuX + menuWidth && y >= menuY && y <= menuY + menuHeight) {
                        float sectionHeight = menuHeight / 4;
                        int choice;
                        if (y > menuY + 3 * sectionHeight) {
                            choice = 0;
                        } else if (y > menuY + 2 * sectionHeight) {
                            choice = 1;
                        } else if (y > menuY + sectionHeight) {
                            choice = 2;
                        } else {
                            choice = 3;
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

                        boolean currentWhiteTurn = pvpGame.isWhiteTurn();
                        if (currentWhiteTurn) {
                            blackClockRunning = false;
                            whiteClockRunning = true;
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                        }
                        lastWhiteTurn = currentWhiteTurn;

                        inPromotion = false;
                        promotingPawn = null;
                    }
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
                            float halfMove = moveCount / 2.0f;
                            System.out.println("Move: " + halfMove);
                        } else {
                            whiteClockRunning = false;
                            blackClockRunning = true;
                            applyIncrement(true);
                            moveCount++;
                            float halfMove = moveCount / 2.0f;
                            System.out.println("Move: " + halfMove);
                        }
                        lastWhiteTurn = currentWhiteTurn;
                    }
                }
                pvpGame.draw(batch);
            }
        }
        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean gameOver = board.gameOver;
        if ((mode == 3 || mode == 4 || mode == 5) && isGameStarted) {
            if (!gameOver) {
                if (mode == 3 || mode == 4) {
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
                font.draw(batch, " ", 490, 280);
                font.draw(batch, " ", 485, 225);
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
            } else {
                font.getData().setScale(2, 2);
                font.setColor(Color.WHITE);
                String blackTimeString = formatTime(blackFTime);
                font.draw(batch, blackTimeString, 490, 280);
                String whiteTimeString = formatTime(whiteFTime);
                font.draw(batch, whiteTimeString, 490, 225);
            }
        }

        if (mode == 3) {
            boolean currentTurn = pvcGame.isWhiteTurn();
            if (currentTurn) {
                if (board.isKingInCheck(PieceColour.WHITE) && !checkPrinted) {
                    System.out.println("check");
                    checkPrinted = true;
                }
            } else {
                if (board.isKingInCheck(PieceColour.BLACK) && !checkPrinted) {
                    System.out.println("check");
                    checkPrinted = true;
                }
            }
            if (pvcGame.isWhiteTurn() != lastWhiteTurn) {
                checkPrinted = false;
            }
        }

        if (mode == 4) {
            boolean currentTurn = pvpGame.isWhiteTurn();
            if (currentTurn) {
                if (board.isKingInCheck(PieceColour.WHITE) && !checkPrinted) {
                    System.out.println("check");
                    checkPrinted = true;
                }
            } else {
                if (board.isKingInCheck(PieceColour.BLACK) && !checkPrinted) {
                    System.out.println("check");
                    checkPrinted = true;
                }
            }
            if (pvpGame.isWhiteTurn() != lastWhiteTurn) {
                checkPrinted = false;
            }
        }

        batch.end();
    }

    private String formatTime(float timeInSeconds) {
        if (timeInSeconds <= 0) {
            return "0:00";
        }
        if (timeInSeconds > 3599) {
            int hours = (int) (timeInSeconds / 3600);
            float remainingAfterHours = timeInSeconds - (hours * 3600);
            int minutes = (int) (remainingAfterHours / 60);
            int seconds = (int) (remainingAfterHours - (minutes * 60));

            String minutesStr = "";
            if (minutes < 10) {
                minutesStr = "0" + minutes;
            } else {
                minutesStr = "" + minutes;
            }

            String secondsStr = "";
            if (seconds < 10) {
                secondsStr = "0" + seconds;
            } else {
                secondsStr = "" + seconds;
            }

            return hours + ":" + minutesStr + ":" + secondsStr;
        } else if (timeInSeconds > 60) {
            int minutes = (int) (timeInSeconds / 60);
            int seconds = (int) (timeInSeconds - (minutes * 60));

            String secondsStr = "";
            if (seconds < 10) {
                secondsStr = "0" + seconds;
            } else {
                secondsStr = "" + seconds;
            }

            return minutes + ":" + secondsStr;
        } else {
            int seconds = (int) timeInSeconds;
            return "" + seconds;
        }
    }

    private void applyIncrement(boolean isWhite) {
        if (selectedIncrementType != 6) {
            if (selectedIncrementType != 0)
                if (isWhite) {
                    whiteFTime += selectedIncrementValue;
                } else {
                    blackFTime += selectedIncrementValue;
                }
            return;
        }

        if (selectedIncrementValue == 1) {
            if (moveCount >= 80) {
                if (isWhite) {
                    whiteFTime += 30;
                } else {
                    blackFTime += 30;
                }
            }
        } else if (selectedIncrementValue == 2) {
            if (/*!sixthIncrementApplied && */  moveCount == 78) {
                whiteFTime += 1800;
            } if (/*!sixthIncrementApplied && */moveCount == 79) {
                blackFTime += 1800;
            }
            if (moveCount >= 80) {
                if (isWhite) {
                    whiteFTime += 30;
                } else {
                    blackFTime += 30;
                }
            }
        } else if (selectedIncrementValue == 3) {
            if (/*!sixthIncrementApplied && */moveCount == 78) {
                whiteFTime += 1800;
            } if (/*!sixthIncrementApplied && */  moveCount == 79) {
                blackFTime += 1800;
            }
            if (moveCount >= 80) {
                if (isWhite) {
                    whiteFTime += 30;
                } else {
                    blackFTime += 30;
                }
            }
        }
    }



    private void startGameWithSelectedTime() {
        whiteFTime = selectedTimeSeconds;
        blackFTime = selectedTimeSeconds;
        moveCount = 0;
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
        whiteClockRunning = false;
        blackClockRunning = false;
        lastWhiteTurn = true;
        isGameStarted = false;
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
        blackToPlayTexture.dispose();
        whiteToPlayTexture.dispose();
    }
}

// Finished working is control+k then write what I changed then commit and push
// Starting work is control+t then merge then pull
// 640 x 480 display

