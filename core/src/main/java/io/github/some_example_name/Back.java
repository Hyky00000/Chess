package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Back {

    private Texture backTexture;
    public static float x = 480;
    public static float y = 370.765343f;
    public float width = 160f;
    public float height = 35.23465704f;

    public Back(Texture backTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backTexture = backTexture;
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

    public Texture getBackTexture() {
        return backTexture;
    }

    public void setBackTexture(Texture backTexture) {
        this.backTexture = backTexture;
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
        batch.draw(backTexture, x, y, width, height);
    }
}
