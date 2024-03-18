package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;

public class Knight extends Actor {
    private Vector2 position;
    private int width, height;
    public boolean isRunningRight,isRunningFront,isRunningBack,isAttacking,isFacingRight;
    private boolean isAnimating = false;
    private Rectangle knight;
    public Animation<TextureRegion> idleAnimation, runRightAnimation,attackRightAnimation,runFrontAnimation,runBackAnimation;
    public float stateTime;

    public Knight(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        isFacingRight = true;
        knight = new Rectangle();
        idleAnimation = AssetManager.idlerightanimation;
        runRightAnimation = AssetManager.runrightanimation;
        attackRightAnimation = AssetManager.attackrightanimation;
        runFrontAnimation = AssetManager.runfrontanimation;
        runBackAnimation = AssetManager.runbackanimation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float drawX = isFacingRight ? position.x : position.x + width;
        float drawWidth = isFacingRight ? width : -width;
        TextureRegion currentFrame;
        if (isAttacking) {
            currentFrame = attackRightAnimation.getKeyFrame(stateTime, false);
            if (attackRightAnimation.isAnimationFinished(stateTime)) {
                isAttacking = false;
            }
        }
        else if (isRunningRight) {
            currentFrame = runRightAnimation.getKeyFrame(stateTime, true);
        } else if (isRunningFront) {
            currentFrame = runFrontAnimation.getKeyFrame(stateTime,true);
        } else if (isRunningBack) {
            currentFrame = runBackAnimation.getKeyFrame(stateTime,true);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, drawX, getY(), drawWidth, getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        knight.set(position.x, position.y, width, height);
    }

    public void moverIzquierda(float delta) {
        if(!isAttacking){
        float newX = position.x - 200 * delta;
        if (newX > 0) {
            position.x = newX;
        }
        isFacingRight = false;
        isRunningRight = true;
        }
    }

    public void moverDerecha(float delta) {
        if(!isAttacking){
        float newX = position.x + 200 * delta;
        if (newX + width < Settings.GAME_WIDTH) {
            position.x = newX;
        }
        isFacingRight = true;
        isRunningRight = true;
        }
    }

    public void moverArriba(float delta) {
        if (!isAttacking) {
            float newY = position.y + 200 * delta;
            if (newY + height < Settings.GAME_HEIGHT) {
                position.y = newY;
            }
            isRunningBack = true;
        }
    }
    public void moverAbajo(float delta) {
        if (!isAttacking) {
            float newY = position.y - 200 * delta;
            if (newY > 0) {
                position.y = newY;
            }
            isRunningFront = true;
        }
    }
    public void moverArribaIzquierda(float delta) {
        moverArriba(delta);
        moverIzquierda(delta);
    }

    public void moverArribaDerecha(float delta) {
        moverArriba(delta);
        moverDerecha(delta);
    }

    public void moverAbajoIzquierda(float delta) {
        moverAbajo(delta);
        moverIzquierda(delta);
    }

    public void moverAbajoDerecha(float delta) {
        moverAbajo(delta);
        moverDerecha(delta);
    }


    public void attack() {
        if (!isAttacking) {
            //isAnimating = true;
            isAttacking = true;
            stateTime = 0;
            /*if (soundsEnabled) {
                addAction(Actions.sequence(
                        Actions.delay(attackAnimation.getAnimationDuration() / 2),
                        Actions.run(() -> {
                            Long delayedSound = AssetManager.attackLeafRangerSound.play();
                            AssetManager.attackLeafRangerSound.setVolume(delayedSound, soundsVolume);
                        })
                ));
            }*/
            /*addAction(Actions.sequence(
                    Actions.delay(attackAnimation.getAnimationDuration()),
                    Actions.run(() -> {
                        isAnimating = false;
                        isAttacking = false;
                    })
            ));*/
        }
    }

    public void pararMovimiento() {
        isRunningFront = false;
        isRunningRight = false;
        isRunningBack = false;
    }
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getKnight() {
        return knight;
    }

}
