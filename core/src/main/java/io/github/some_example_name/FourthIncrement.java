package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FourthIncrement {

    private Texture fourthIncrementTexture;
    public static float x = 0;
    public static float y = 161.5667075f;
    public float width = 480f;
    public float height = 156.8665851f;

    public FourthIncrement(Texture fourthIncrementTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fourthIncrementTexture = fourthIncrementTexture;
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

    public Texture getFourthIncrementTexture() {
        return fourthIncrementTexture;
    }

    public void setFourthIncrementTexture(Texture fourthIncrementTexture) {
        this.fourthIncrementTexture = fourthIncrementTexture;
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
        batch.draw(fourthIncrementTexture, x, y, width, height);
    }
}
