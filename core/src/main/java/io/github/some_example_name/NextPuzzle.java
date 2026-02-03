package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NextPuzzle {

    private Texture nextpuzzleTexture;
    public static float x = 142;
    public static float y = 200;
    public float width = 200f;
    public float height = 139.4039735f;

    public NextPuzzle(Texture nextpuzzleTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.nextpuzzleTexture = nextpuzzleTexture;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Texture getNextPuzzleTexture() {
        return nextpuzzleTexture;
    }

    public void setNextPuzzleTexture(Texture nextpuzzleTexture) {
        this.nextpuzzleTexture = nextpuzzleTexture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(nextpuzzleTexture, x, y, width, height);
    }
}
