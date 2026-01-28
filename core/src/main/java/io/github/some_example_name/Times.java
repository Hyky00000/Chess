package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Times {

    private Texture timesTexture;
    public static float x = 59.04761905f;
    public static float y = 0f;
    public float width = 361.9047619f;
    public float height = 480f;

    public Times(Texture timesTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.timesTexture = timesTexture;
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

    public Texture getTimesTexture() {
        return timesTexture;
    }

    public void setTimesTexture(Texture timesTexture) {
        this.timesTexture = timesTexture;
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
        batch.draw(timesTexture, x, y, width, height);
    }
}
