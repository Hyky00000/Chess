package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ThirdIncrement {

    private Texture thirdIncrementTexture;
    public static float x = 0;
    public static float y = 0f;
    public float width = 480f;
    public float height = 236.7686659f;

    public ThirdIncrement(Texture changeGameTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.thirdIncrementTexture = thirdIncrementTexture;
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

    public Texture getThirdIncrementTexture() {
        return thirdIncrementTexture;
    }

    public void setThirdIncrementTexture(Texture thirdIncrementTexture) {
        this.thirdIncrementTexture = thirdIncrementTexture;
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
        batch.draw(thirdIncrementTexture, x, y, width, height);
    }
}
