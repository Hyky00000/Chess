package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Difficulty {

    private Texture difficultyTexture;
    public static float x = 0;
    public static float y = 118.8592575f;
    public static float width = 481f;
    public static float height = 242.2814815f;

    public Difficulty (Texture difficultyTexture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.difficultyTexture = difficultyTexture;
    }

    public Texture getdifficultyTexture() {
        return difficultyTexture;
    }

    public void setdifficultyTexture(Texture difficultyTexture) {
        difficultyTexture = difficultyTexture;
    }

    public static float getX() {
        return x;
    }

    public static void setX(float x) {
        Difficulty.x = x;
    }

    public static float getWidth() {
        return width;
    }

    public static void setWidth(float width) {
        Difficulty.width = width;
    }

    public static float getY() {
        return y;
    }

    public static void setY(float y) {
        Difficulty.y = y;
    }

    public static float getHeight() {
        return height;
    }

    public static void setHeight(float height) {
        Difficulty.height = height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(difficultyTexture, x, y, width, height);
    }

}
