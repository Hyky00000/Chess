package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstIncrement {

    private Texture firstIncrementTexture;
    public static float x = 0;
    public static float y = 161.4652015f;
    public float width = 480f;
    public float height = 157.0695971f;

    public FirstIncrement(Texture firstIncrementTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.firstIncrementTexture = firstIncrementTexture;
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

    public Texture getFirstIncrementTexture() {
        return firstIncrementTexture;
    }

    public void setFirstIncrementTexture(Texture firstIncrementTexture) {
        this.firstIncrementTexture = firstIncrementTexture;
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
        batch.draw(firstIncrementTexture, x, y, width, height);
    }
}
