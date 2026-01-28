package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FifthIncrement {

    private Texture fifthIncrementTexture;
    public static float x = 0;
    public static float y = 130.1362398f;
    public float width = 480f;
    public float height = 219.7275204f;

    public FifthIncrement(Texture fifthIncrementTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fifthIncrementTexture = fifthIncrementTexture;
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

    public Texture getFifthIncrementTexture() {
        return fifthIncrementTexture;
    }

    public void setFifthIncrementTexture(Texture fifthIncrementTexture) {
        this.fifthIncrementTexture = fifthIncrementTexture;
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
        batch.draw(fifthIncrementTexture, x, y, width, height);
    }
}
