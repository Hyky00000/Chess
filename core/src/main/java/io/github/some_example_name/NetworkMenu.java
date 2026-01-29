package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NetworkMenu {

    private Texture networkMenuTexture;
    public static float x = 0;
    public static float y = 138.55f;
    public float width = 480f;
    public float height = 202.9f;

    // Just track what the user selected
    private int selectedOption = 0; // 0 nothing, 1 host, 2 join
    private String typedText = "";
    private boolean isTyping = false;

    public NetworkMenu(Texture networkMenuTexture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.networkMenuTexture = networkMenuTexture;
        this.selectedOption = 0;
        this.typedText = "";
        this.isTyping = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(networkMenuTexture, x, y, width, height);
    }

    public void handleClick(float clickX, float clickY) {
        if (clickX < x || clickX > x + width || clickY < y || clickY > y + height) {
            return;
        }

        float sectionHeight = height / 4;
        float clickPositionInMenu = clickY - y;

        // Section 1: Top section (Host Game)
        if (clickPositionInMenu > height - sectionHeight) {
            selectedOption = 1;
            isTyping = false;
            System.out.println("Host Game selected");
        }
        // Section 2: Second from top (Join Game)
        else if (clickPositionInMenu > height - (2 * sectionHeight)) {
            selectedOption = 2;
            isTyping = false;
            System.out.println("Join Game selected");
        }
        // Section 3: Third from top (Enter IP)
        else if (clickPositionInMenu > height - (3 * sectionHeight)) {
            if (selectedOption == 2) { // Only if Join Game was selected
                isTyping = true;
                typedText = "";
                System.out.println("Ready to type IP address");
            }
        }
        // Section 4: Bottom section (Enter Name)
        else {
            isTyping = true;
            typedText = "";
            System.out.println("Ready to type player name");
        }
    }

    // Very simple text typing
    public void addTypedCharacter(char character) {
        if (!isTyping) {
            return;
        }

        // Only add if it's a normal character
        if (character >= ' ' && character <= '~') { // Normal keyboard characters
            typedText = typedText + character;
            System.out.println("Typed: " + typedText);
        }
    }

    // Remove last character (backspace)
    public void removeLastCharacter() {
        if (!isTyping || typedText.length() == 0) {
            return;
        }

        typedText = typedText.substring(0, typedText.length() - 1);
        System.out.println("Removed character. Now: " + typedText);
    }

    // Get what the user wants to do
    public boolean wantsToHost() {
        return selectedOption == 1;
    }

    public boolean wantsToJoin() {
        return selectedOption == 2;
    }

    // Get the typed text
    public String getTypedText() {
        return typedText;
    }

    // Simple getters (like your other menu classes)
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
        return networkMenuTexture;
    }

    public void setMenuTexture(Texture menuTexture) {
        this.networkMenuTexture = menuTexture;
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

    // For IP address - simple default
    public String getIpAddress() {
        if (typedText.length() == 0) {
            return "localhost"; // Simple default for testing
        }
        return typedText;
    }

    // For player name - simple default
    public String getPlayerName() {
        if (typedText.length() == 0) {
            return "Player"; // Simple default
        }
        return typedText;
    }

    // Simple check if ready
    public boolean isReady() {
        if (wantsToHost()) {
            return true; // Host just needs to click
        } else if (wantsToJoin()) {
            return typedText.length() > 0; // Join needs IP typed
        }
        return false;
    }

    // Stop typing
    public void stopTyping() {
        isTyping = false;
    }

    // Check if currently typing
    public boolean isTyping() {
        return isTyping;
    }

    // Reset everything
    public void reset() {
        selectedOption = 0;
        typedText = "";
        isTyping = false;
    }
}
