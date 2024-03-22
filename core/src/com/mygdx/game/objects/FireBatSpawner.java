package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.BatDeathListener;
import com.mygdx.game.utils.Settings;

import java.util.Iterator;

public class FireBatSpawner {
    private Array<FireBat> firebats;
    private boolean flyingRight;
    private Knight knight;
    private int score;
    private boolean doubleLifeEnabled,tripleLifeEnabled,quintupleLifeEnabled;
    private BatDeathListener batDeathListener;

    public FireBatSpawner(Knight knight,BatDeathListener batDeathListener) {
        firebats = new Array<>();
        this.knight = knight;
        this.batDeathListener = batDeathListener;
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
                                if(firebat.isAttacking){
                                    knight.hurt();
                                };
                            }
                        })
                ));
            }

            if (isColliding && knight.isAttacking) {
                if (knight.stateTime >= knight.attackRightAnimation.getAnimationDuration()/2) {
                    firebat.hurt();
                }
                firebat.setDelay(0.08f);
            }
            if (firebat.isDeath()) {
                firebat.decreaseDelay(delta);
                if (firebat.getDelay() <= 0) {
                    firebat.remove();
                    iterator.remove();
                    batDeathListener.onBatDeath(firebat.getX(),firebat.getY());
                }
            }
            if (!isColliding) {
                firebat.setNotAttacking();
            }

            if(knight.isDeath){
                firebat.setFlying(false);
            }

            if (doubleLifeEnabled) {
                    if (firebat.getHealth() == Settings.FIREBAT_HEALTH) {
                        firebat.setHealth(Settings.FIREBAT_HEALTH * Settings.FIREBAT_HEALTH_INCREASE_LEVEL1);
                        Gdx.app.log("Double Life", "La vida de un murciélago se ha duplicado.");
                }
            }
            if (tripleLifeEnabled) {
                    if (firebat.getHealth() == Settings.FIREBAT_HEALTH * Settings.FIREBAT_HEALTH_INCREASE_LEVEL1) {
                        firebat.setHealth(Settings.FIREBAT_HEALTH * Settings.FIREBAT_HEALTH_INCREASE_LEVEL2);
                        Gdx.app.log("Triple Life", "La vida de un murciélago se ha triplicado.");
                }
            }

            if (quintupleLifeEnabled) {
                    if (firebat.getHealth() == Settings.FIREBAT_HEALTH * Settings.FIREBAT_HEALTH_INCREASE_LEVEL2) {
                        firebat.setHealth(Settings.FIREBAT_HEALTH * Settings.FIREBAT_HEALTH_INCREASE_LEVEL3);
                        Gdx.app.log("Quintuple Life", "La vida de un murciélago se ha quintuplicado.");
                    }
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
    public void setDoubleLifeEnabled() {
        this.doubleLifeEnabled = true;
    }
    public void setTripleLifeEnabled() {
        this.tripleLifeEnabled = true;
    }

    public void setQuintupleLifeEnabled() {
        this.quintupleLifeEnabled = true;
    }
}
