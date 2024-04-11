package com.mygdx.game.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Button extends Image {
    public Button(TextureRegion region) {
        super(region);
        setBounds(0, 0, region.getRegionWidth(), region.getRegionHeight());
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick();
            }
        });
    }

    protected void onClick() {
    }
    protected void onRelease(){
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        super.draw(batch, parentAlpha);
    }
}