package io.github.some_example_name;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Board board;
    private Menu menu;
    private ColourChoice colourChoice;
    private int mode = 0;
    float menuChoiceHeight = 55.65861f;
    float menuGapHeight = 14.78852f;
    private boolean playerIsWhite;

    private Texture colourChoiceTexture;
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
    private int aiDifficulty = 1;

    @Override
    public void create() {
        batch = new SpriteBatch();

        colourChoiceTexture = new Texture("ColourChoice.png");
        boardTexture = new Texture("ChessBoard.png");
        menuTexture = new Texture("Menu.png");

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
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1);
        batch.begin();

        //0 is start, 1 is choose colour, 2 is pvp, 3 is vsai

        if (mode == 0) {
            board.draw(batch);
            menu.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + menu.getHeight()) && (y > menu.getY() + menu.getHeight() - menuChoiceHeight)) {
                    mode = 2;
                    pvpGame = new PlayerVsPlayer(board);
                } else if ((x > menu.getX()) && (x < menu.getX() + menu.getWidth()) &&
                    (y < menu.getY() + ((3 * menuChoiceHeight) + (2 * menuGapHeight))) &&
                    (y > menu.getY() + ((2 * menuChoiceHeight) + (2 * menuGapHeight)))) {
                    mode = 1;
                }
            }
        } else if (mode == 1) {
            board.draw(batch);
            colourChoice.draw(batch);
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (x >= colourChoice.getX() && x <= colourChoice.getX() + colourChoice.getWidth() &&
                    y >= colourChoice.getY() && y <= colourChoice.getY() + colourChoice.getHeight()) {

                    float clickY = y - colourChoice.getY();

                    boolean playerIsWhite = clickY < colourChoice.getHeight() / 2;
                    pvcGame = new PlayerVsComputer(board, playerIsWhite, aiDifficulty);
                    mode = 3;
                }
            }
        } else if (mode == 2) {
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                pvpGame.click(x, y);
            }
            pvpGame.draw(batch);
        } else if (mode == 3) {
            if (Gdx.input.justTouched()) {
                float x = Gdx.input.getX();
                float y = Gdx.graphics.getHeight() - Gdx.input.getY();
                pvcGame.click(x, y);
            }
            pvcGame.draw(batch);
        }


        batch.end();
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
    }
}
// Finished working is control+k then write what I changed then commit and push
// Starting work is control+t then merge then pull
