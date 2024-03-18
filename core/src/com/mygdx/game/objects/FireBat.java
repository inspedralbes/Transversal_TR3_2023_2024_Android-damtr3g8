package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;

public class FireBat extends Actor {
    public Animation<TextureRegion> flyanimation, attackanimation, deathanimation;
    private float stateTime;
    public boolean isAttacking, flyingRight, isDeath;
    float delay;

    public FireBat() {
        flyanimation = AssetManager.firebatFlyinganimation;
        attackanimation = AssetManager.firebatAttackinganimation;
        deathanimation = AssetManager.firebatDeathanimation;
        stateTime = 0f;
        setSize(Settings.FIREBAT_WIDTH, Settings.FIREBAT_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion currentFrame;
        if (isDeath) {
            currentFrame = deathanimation.getKeyFrame(stateTime, false);
            if (deathanimation.isAnimationFinished(stateTime)) {
                remove();
            }
        } else {
            if (isAttacking) {
                currentFrame = attackanimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = flyanimation.getKeyFrame(stateTime, true);
            }
        }
        if (flyingRight) {
            batch.draw(currentFrame, getX() + getWidth(), getY(), -getWidth(), getHeight());
        } else {
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setFlyingRight(boolean flyingRight) {
        this.flyingRight = flyingRight;
    }

    public void setAttacking() {
        if (!isAttacking) {
            isAttacking = true;
            System.out.println("Ataque del murcielago");
        }

    }

    public void setDeath() {
        if (!isDeath) {
            isDeath = true;
            stateTime = 0f;
        }
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void drawBounds(ShapeRenderer shapeRenderer) {
            shapeRenderer.setColor(new Color(1, 2, 1, 1));
            shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    }


    public boolean isColliding(Knight knight) {
        Rectangle batBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle knightBounds = new Rectangle(knight.getX(), knight.getY(), knight.getWidth(), knight.getHeight());
        return batBounds.overlaps(knightBounds);
    }

    public void setNotAttacking() {
        isAttacking = false;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public float getDelay() {
        return delay;
    }

    public void decreaseDelay(float delta) {
        delay -= delta;
    }

}
