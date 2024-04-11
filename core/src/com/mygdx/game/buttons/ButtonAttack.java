package com.mygdx.game.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.Knight;

public class ButtonAttack extends Button{
    private TextureRegion buttonNormal;
    private TextureRegion buttonPress;
    private boolean isPressed;
    public ButtonAttack(TextureRegion buttonNormal) {
        super(buttonNormal);
        this.buttonNormal = buttonNormal;
        buttonPress = AssetManager.attackButtonpressed;
        this.isPressed = false;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = isPressed ? buttonPress : buttonNormal;
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onClick() {
        isPressed = true;
    }

    @Override
    public void onRelease() {
        isPressed = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
