package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChangeAI {

    private Texture changeAITexture;
    public static float x = 480;
    public static float y = 422.1860885f;
    public float width = 160f;
    public float height = 57.81391147f;

    public ChangeAI(Texture changeAITexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.changeAITexture = changeAITexture;
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

    public Texture getChangeAITexture() {
        return changeAITexture;
    }

    public void setChangeAITexture(Texture changeAITexture) {
        this.changeAITexture = changeAITexture;
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
        batch.draw(changeAITexture, x, y, width, height);
    }
}
