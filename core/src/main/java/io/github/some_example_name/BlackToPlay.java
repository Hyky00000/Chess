package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlackToPlay {
    private Texture blackToPlayTexture;
    public static float x = 480;
    public static float y = 195.25f;
    public float width = 160f;
    public float height = 89.5f;

    public BlackToPlay(Texture blackToPlayTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blackToPlayTexture = blackToPlayTexture;
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

    public Texture getBlackToPlayTexture() {
        return blackToPlayTexture;
    }

    public void setBlackToPlayTexture(Texture blackToPlayTexture) {
        this.blackToPlayTexture = blackToPlayTexture;
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
        batch.draw(blackToPlayTexture, x, y, width, height);
    }
}
