package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.buttons.ButtonAttack;
import com.mygdx.game.buttons.ButtonCloseInventary;
import com.mygdx.game.buttons.ButtonOpenInventary;
import com.mygdx.game.buttons.GamepadDown;
import com.mygdx.game.buttons.GamepadLeft;
import com.mygdx.game.buttons.GamepadRight;
import com.mygdx.game.buttons.GamepadUp;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.InventoryScreen;
import com.mygdx.game.utils.Settings;

public class InputHandler implements InputProcessor {
    private Knight knight;
    private ButtonAttack attackButton;
    private ButtonOpenInventary buttonOpenInventary;
    private ButtonCloseInventary buttonCloseInventary;
    private GamepadDown gamepadDown;
    private GamepadLeft gamepadLeft;
    private GamepadRight gamepadRight;
    private GamepadUp gamepadUp;
    private GameScreen screen;
    private InventoryScreen inventoryScreen;
    private Stage stage;
    public boolean isDragging = false;
    public boolean isButtonDownPressed = false;
    public boolean isButtonLeftPressed = false;
    public boolean isButtonrightPressed = false;
    public boolean isButtonupPressed = false;
    public InputHandler(GameScreen screen) {
        this.screen = screen;
        knight = screen.getKnight();
        stage = screen.getStage();
        attackButton = screen.getAttackButton();
        buttonOpenInventary = screen.getButtonOpenInventary();
        gamepadDown = screen.getGamepadDown();
        gamepadLeft =screen.getGamepadLeft();
        gamepadRight = screen.getGamepadRight();
        gamepadUp = screen.getGamepadUp();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convertir las coordenadas de pantalla a coordenadas de juego
        float gameX = (float) (screenX * Settings.GAME_WIDTH) / Gdx.graphics.getWidth();
        float gameY = (float) ((Gdx.graphics.getHeight() - screenY) * Settings.GAME_HEIGHT) / Gdx.graphics.getHeight();

        // Verificar si el toque está dentro del botón de ataque
        if (screen.getAttackButtonBounds().contains(gameX, gameY)) {
            knight.attack();
            attackButton.onClick();
            return true;
        }

        if (screen.getButtonOpenInventaryBounds().contains(gameX, gameY)) {
            screen.openInventary();
            buttonOpenInventary.onClick();
            return true;
        }

        if (screen.getGamepadDownBounds().contains(gameX, gameY)) {
            setButtonDownPressed(true);
            gamepadDown.onClick();
            return true;
        }

        if (screen.getGamepadLeftBounds().contains(gameX, gameY)) {
            setButtonLeftPressed(true);
            gamepadLeft.onClick();
            return true;
        }
        if (screen.getGamepadRightBounds().contains(gameX, gameY)) {
            setButtonRightPressed(true);
            gamepadRight.onClick();
            return true;
        }

        if (screen.getGamepadUpBounds().contains(gameX, gameY)) {
            setButtonUpPressed(true);
            gamepadUp.onClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragging = false;
        knight.pararMovimiento();
        attackButton.onRelease();
        buttonOpenInventary.onRelease();
        gamepadDown.onRelease();
        setButtonDownPressed(false);
        gamepadLeft.onRelease();
        setButtonLeftPressed(false);
        gamepadRight.onRelease();
        setButtonRightPressed(false);
        gamepadUp.onRelease();
        setButtonUpPressed(false);
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setButtonDownPressed(boolean pressed) {
        isButtonDownPressed = pressed;
    }
    public void setButtonLeftPressed(boolean pressed) {
        isButtonLeftPressed = pressed;
    }
    public void setButtonRightPressed(boolean pressed) {
        isButtonrightPressed = pressed;
    }
    public void setButtonUpPressed(boolean pressed) {
        isButtonupPressed = pressed;
    }
}
