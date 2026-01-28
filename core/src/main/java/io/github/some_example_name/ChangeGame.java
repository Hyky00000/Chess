package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChangeGame {

    private Texture changeGameTexture;
    public static float x = 480;
    public static float y = 410; //422.1860885f
    public float width = 160f;
    public float height = 70; //57.81391147f

    public ChangeGame(Texture changeGameTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.changeGameTexture = changeGameTexture;
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

    public Texture getChangeGameTexture() {
        return changeGameTexture;
    }

    public void setChangeGameTexture(Texture changeGameTexture) {
        this.changeGameTexture = changeGameTexture;
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
        batch.draw(changeGameTexture, x, y, width, height);
    }
}
