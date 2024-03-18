package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.Settings;

import java.util.Iterator;

public class FireBatSpawner {
    private Array<FireBat> firebats;
    private boolean flyingRight;
    private Knight knight;

    public FireBatSpawner(Knight knight) {
        firebats = new Array<>();
        this.knight = knight;
    }

    public void spawnFirebat(float startX, float startY) {
        FireBat firebat = new FireBat();
        firebat.setPosition(startX, startY);
        firebats.add(firebat);
    }

    public void update(float delta) {
        Iterator<FireBat> iterator = firebats.iterator();
        while (iterator.hasNext()) {
            FireBat firebat = iterator.next();

            float knightX = knight.getX();
            float knightY = knight.getY();
            float directionX = knightX - firebat.getX();
            float directionY = knightY - firebat.getY();

            float distance = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            float speed = Settings.FIREBAT_SPEED;
            float duration = distance / speed;

            firebat.setFlyingRight(firebat.getX() > knightX);
            firebat.setFlying(true);

            boolean isColliding = firebat.isColliding(knight);

            firebat.clearActions();
            if (!firebat.isDeath && !knight.isDeath) {
                firebat.addAction(Actions.sequence(
                        Actions.moveTo(knightX, knightY, duration),
                        Actions.run(() -> {
                            if (isColliding) {
                                firebat.setAttacking();
                                knight.hurt();
                            }
                        })
                ));
            }
            if (firebat.isColliding(knight) && knight.isAttacking) {
                if (knight.stateTime >= knight.attackRightAnimation.getAnimationDuration() / 4) {
                    firebat.setDeath();
                }
                firebat.setDelay(0.08f);
            }
            if (firebat.isDeath()) {
                firebat.decreaseDelay(delta);
                if (firebat.getDelay() <= 0) {
                    firebat.remove();
                    iterator.remove();
                }
            }
            if (!isColliding) {
                firebat.setNotAttacking();
            }

            if(knight.isDeath){
                firebat.setFlying(false);
            }

            firebat.act(delta);
        }


    }

    public Array<FireBat> getFirebats() {
        return firebats;
    }

    public boolean isFlyingRight() {
        return flyingRight;
    }

    public void setFlyingRight(boolean flyingRight) {
        this.flyingRight = flyingRight;
    }
}
