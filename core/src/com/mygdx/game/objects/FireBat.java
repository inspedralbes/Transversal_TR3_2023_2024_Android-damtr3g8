package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
    public Animation<TextureRegion> flyanimation, attackanimation, deathanimation,hurtanimation;
    public Texture sleep;
    private float stateTime;
    public boolean isAttacking, flyingRight,isHurt, isDeath, isFlying,canMoveAfterDamage;
    float delay;
    private int health = Settings.FIREBAT_HEALTH;

    public FireBat() {
        flyanimation = AssetManager.firebatFlyinganimation;
        attackanimation = AssetManager.firebatAttackinganimation;
        hurtanimation = AssetManager.firebatHurtanimation;
        deathanimation = AssetManager.firebatDeathanimation;
        sleep = AssetManager.sleepfirebat;
        canMoveAfterDamage = true;
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
            } else if (isHurt) {
                currentFrame = hurtanimation.getKeyFrame(stateTime, false);
                if (hurtanimation.isAnimationFinished(stateTime)) {
                    isHurt = false;
                }
            } else {
                currentFrame = flyanimation.getKeyFrame(stateTime, true);
            }
        }
        if (flyingRight && isFlying) {
            batch.draw(currentFrame, getX() + getWidth(), getY(), -getWidth(), getHeight());
        } else if (!flyingRight && isFlying) {
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        } else {
            batch.draw(sleep, getX(), getY(), getWidth(), getHeight());
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

    public void hurt() {
        if (!isHurt) {
            isHurt = true;
            isFlying = false;
            canMoveAfterDamage = true;
            stateTime = 0f;
            this.receiveDamage(Settings.KNIGHT_DAMAGE_PER_ATTACK);
            clearActions();
            addAction(Actions.sequence(
                    Actions.delay(2),
                    Actions.run(() -> {
                        isFlying = true;
                        canMoveAfterDamage = false;
                    })
            ));
        }
    }

    public void receiveDamage(int damage) {
        health -= damage;
        System.out.println("Firebat Health: " + health);
        if (health <= 0) {
            setDeath();
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

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
