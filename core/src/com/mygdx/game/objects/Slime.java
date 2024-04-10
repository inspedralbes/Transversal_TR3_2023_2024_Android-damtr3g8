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

public class Slime extends Actor {
    public Animation<TextureRegion> hopanimation, attackanimation, deathanimation, hurtanimation, idleanimation;
    private float stateTime;
    public boolean isAttacking, HopingRight, isHurt, isDeath, isHoping, canMoveAfterDamage;
    float delay;
    private int health = Settings.SLIME_HEALTH;
    private boolean isForceActive = false;
    private float forceDuration = 0f;
    private float originalDamageMultiplier = 1.0f; // Multiplicador de daño original

    public Slime() {
        hopanimation = AssetManager.slimeHopAnimation;
        attackanimation = AssetManager.slimeAttackAnimation;
        hurtanimation = AssetManager.slimeHurtAnimation;
        deathanimation = AssetManager.slimeDeathAnimation;
        idleanimation = AssetManager.slimeIdleAnimation;
        canMoveAfterDamage = true;
        stateTime = 0f;
        setSize(Settings.SLIME_WIDTH, Settings.SLIME_HEIGHT);
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
            } else if (isHoping) {
                currentFrame = hopanimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = idleanimation.getKeyFrame(stateTime, true);
            }
        }
        if (HopingRight) {
            batch.draw(currentFrame, getX() + getWidth(), getY(), -getWidth(), getHeight());
        } else if (!HopingRight) {
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setHopingRight(boolean hopingRight) {
        this.HopingRight = hopingRight;
    }

    public void setAttacking() {
        if (!isAttacking) {
            isAttacking = true;
            System.out.println("Ataque del slime");
        }

    }

    public void hurt() {
        if (!isHurt) {
            isHurt = true;
            isHoping = false;
            canMoveAfterDamage = true;
            stateTime = 0f;
            this.receiveDamage(Settings.KNIGHT_DAMAGE_PER_ATTACK);
            clearActions();
            addAction(Actions.sequence(
                    Actions.delay(2),
                    Actions.run(() -> {
                        isHoping = true;
                        canMoveAfterDamage = false;
                    })
            ));
        }
    }

    public void applyAttack(float duration) {
        isForceActive = true;
        forceDuration = duration;
        // Guardar el multiplicador de daño original
        originalDamageMultiplier = 1.0f;
    }

    public void receiveDamage(int damage) {
        if (isForceActive) {
            // Aplicar reducción de daño
            damage *= 1.8f; // Reducir el daño en un 65%
        }
        health -= damage;
        System.out.println("Slime Health: " + health);
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
        Rectangle slimeBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle knightBounds = new Rectangle(knight.getX(), knight.getY(), knight.getWidth(), knight.getHeight());
        return slimeBounds.overlaps(knightBounds);
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

    public boolean isHoping() {
        return isHoping;
    }

    public void setHoping(boolean hoping) {
        isHoping = hoping;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
