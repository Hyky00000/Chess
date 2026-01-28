package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SecondIncrement {

    private Texture secondIncrementTexture;
    public static float x = 0;
    public static float y = 161.5667075f;
    public float width = 480f;
    public float height = 156.8665851f;

    public SecondIncrement(Texture secondIncrementTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.secondIncrementTexture = secondIncrementTexture;
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

    public Texture getSecondIncrementTexture() {
        return secondIncrementTexture;
    }

    public void setSecondIncrementTexture(Texture secondIncrementTexture) {
        this.secondIncrementTexture = secondIncrementTexture;
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
        batch.draw(secondIncrementTexture, x, y, width, height);
    }
}
