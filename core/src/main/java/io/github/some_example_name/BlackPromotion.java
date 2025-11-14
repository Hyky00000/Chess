package io.github.some_example_name;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlackPromotion {

    private Texture blackPromotionTexture;
    public static float x = 81464/205f;
    public static float y = 22f;
    //public float width = 12426/205f;
    public float width = 61.61463415f;
    public float height = 218f;

    public BlackPromotion(Texture blackPromotionTexture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blackPromotionTexture = blackPromotionTexture;
    }

    public Texture getBlackPromotionTexture() {
        return blackPromotionTexture;
    }

    public void setBlackPromotionTexture(Texture blackPromotionTexture) {
        this.blackPromotionTexture = blackPromotionTexture;
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
        batch.draw(blackPromotionTexture, x, y, width, height);
    }

}
