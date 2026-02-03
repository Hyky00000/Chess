package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WhiteToPlay {
    private Texture whiteToPlayTexture;
    public static float x = 480;
    public static float y = 195.4080221f;
    public float width = 160f;
    public float height = 89.18395574f;

    public WhiteToPlay(Texture whiteToPlayTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.whiteToPlayTexture = whiteToPlayTexture;
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

    public Texture getWhiteToPlayTexture() {
        return whiteToPlayTexture;
    }

    public void setWhiteToPlayTexture(Texture whiteToPlayTexture) {
        this.whiteToPlayTexture = whiteToPlayTexture;
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
        batch.draw(whiteToPlayTexture, x, y, width, height);
    }
}
