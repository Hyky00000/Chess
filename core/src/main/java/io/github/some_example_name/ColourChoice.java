package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColourChoice {

    private Texture colourChoiceTexture;
    public static float x = 0;
    public static float y = 74.5f;
    public float width = 481f;
    public float height = 315.8610354f;

    public ColourChoice(Texture colourChoiceTexture/*, float x, float y*/) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colourChoiceTexture = colourChoiceTexture;
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

    public Texture getMenuTexture() {
        return colourChoiceTexture;
    }

    public void setMenuTexture(Texture menuTexture) {
        this.colourChoiceTexture = menuTexture;
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
        batch.draw(colourChoiceTexture, x, y, width, height);
    }
}
