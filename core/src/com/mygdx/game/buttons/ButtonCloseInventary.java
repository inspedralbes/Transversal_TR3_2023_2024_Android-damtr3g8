package com.mygdx.game.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.helpers.AssetManager;

public class ButtonCloseInventary extends Button {
    private TextureRegion buttonNormal;
    private TextureRegion buttonPress;
    private boolean isPressed;

    public ButtonCloseInventary(TextureRegion buttonNormal) {
        super(buttonNormal);
        this.buttonNormal = buttonNormal;
        buttonPress = AssetManager.closeButtonpressed;
        this.isPressed = false;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Dibuja la región de textura correspondiente según el estado del botón
        TextureRegion currentFrame = isPressed ? buttonPress : buttonNormal;
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
    public void onClick() {
        isPressed = true; // Cambia el estado del botón a presionado cuando se hace clic

    }

    @Override
    public void onRelease() {
        isPressed = false; // Restablece el estado del botón cuando se levanta el clic
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}