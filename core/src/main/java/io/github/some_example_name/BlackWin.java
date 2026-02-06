package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlackWin {

    private Texture blackWinTexture;
    public static float x = 150;
    public static float y = 160;
    public float width = 340f;
    public float height = 160f;

    public BlackWin(Texture blackWinTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blackWinTexture = blackWinTexture;
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

    public Texture getBlackWinTexture() {
        return blackWinTexture;
    }

    public void setBlackWinTexture(Texture blackWinTexture) {
        this.blackWinTexture = blackWinTexture;
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
        batch.draw(blackWinTexture, x, y, width, height);
    }
}
