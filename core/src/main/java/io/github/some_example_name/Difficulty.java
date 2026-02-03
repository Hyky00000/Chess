package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Difficulty {

    private Texture difficultyTexture;
    public float x = 0;
    public float y = 118.8592575f;
    public float width = 481f;
    public float height = 242.2814815f;

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
        this.difficultyTexture = difficultyTexture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(difficultyTexture, x, y, width, height);
    }
}
