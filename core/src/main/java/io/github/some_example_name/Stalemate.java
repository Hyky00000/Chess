package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stalemate {

    private Texture stalemateTexture;
    public static float x = 150;
    public static float y = 160;
    public float width = 340f;
    public float height = 160f;

    public Stalemate(Texture stalemateTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stalemateTexture = stalemateTexture;
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

    public Texture getStalemateTexture() {
        return stalemateTexture;
    }

    public void setStalemateTexture(Texture stalemateTexture) {
        this.stalemateTexture = stalemateTexture;
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
        batch.draw(stalemateTexture, x, y, width, height);
    }
}
