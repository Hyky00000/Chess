package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SixthIncrement {

    private Texture sixthIncrementTexture;
    public static float x = 0;
    public static float y = 0f;
    public float width = 480f;
    public float height = 219.7275204f;

    public SixthIncrement(Texture sixthIncrementTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sixthIncrementTexture = sixthIncrementTexture;
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

    public Texture getSixthIncrementTexture() {
        return sixthIncrementTexture;
    }

    public void setSixthIncrementTexture(Texture sixthIncrementTexture) {
        this.sixthIncrementTexture = sixthIncrementTexture;
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
        batch.draw(sixthIncrementTexture, x, y, width, height);
    }
}
