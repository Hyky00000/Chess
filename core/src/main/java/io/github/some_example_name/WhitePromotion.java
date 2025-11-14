package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WhitePromotion {

    private Texture whitePromotionTexture;
    public static float x = 22.5f;
    public static float y = 240f;
    //public float width = 35970/611f;
    public float width = 58.87070376f;
    public float height = 218f;

    public WhitePromotion(Texture whitePromotionTexture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.whitePromotionTexture = whitePromotionTexture;
    }

    public Texture getWhitePromotionTexture() {
        return whitePromotionTexture;
    }

    public void setBlackPromotionTexture(Texture whitePromotionTexture) {
        this.whitePromotionTexture = whitePromotionTexture;
    }

    public static float getX() {
        return x;
    }

    public static void setX(float x) {
        BlackPromotion.x = x;
    }

    public static float getY() {
        return y;
    }

    public static void setY(float y) {
        BlackPromotion.y = y;
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

    public void draw(SpriteBatch batch){
        batch.draw(whitePromotionTexture, x, y, width, height);
    }

}
