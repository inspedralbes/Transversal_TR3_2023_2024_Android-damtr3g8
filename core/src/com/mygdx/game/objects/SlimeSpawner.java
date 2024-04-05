package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.BatDeathListener;
import com.mygdx.game.utils.Settings;

import java.util.Iterator;

public class SlimeSpawner {
    private Array<Slime> slimes;
    private boolean hopingRight;
    private Knight knight;
    private int score;
    private boolean doubleLifeEnabled,tripleLifeEnabled,quintupleLifeEnabled;
    private BatDeathListener batDeathListener;

    public SlimeSpawner(Knight knight,BatDeathListener batDeathListener) {
        slimes = new Array<>();
        this.knight = knight;
        this.batDeathListener = batDeathListener;
    }

    public void spawnSlime(float startX, float startY) {
        Slime slime = new Slime();
        slime.setPosition(startX, startY);
        slimes.add(slime);
    }

    public void update(float delta) {
        Iterator<Slime> iterator = slimes.iterator();
        while (iterator.hasNext()) {
            Slime slime = iterator.next();

            float knightX = knight.getX();
            float knightY = knight.getY();
            float directionX = knightX - slime.getX();
            float directionY = knightY - slime.getY();

            float distance = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            float speed = Settings.SLIME_SPEED;
            float duration = distance / speed;

            slime.setHopingRight(slime.getX() > knightX);
            slime.setHoping(true);

            boolean isColliding = slime.isColliding(knight);

            slime.clearActions();
            if (!slime.isDeath && !knight.isDeath) {
                slime.addAction(Actions.sequence(
                        Actions.moveTo(knightX, knightY, duration),
                        Actions.run(() -> {
                            if (isColliding) {
                                slime.setAttacking();
                                if(slime.isAttacking){
                                    knight.hurt();
                                };
                            }
                        })
                ));
            }

            if (isColliding && knight.isAttacking) {
                if (knight.stateTime >= knight.attackRightAnimation.getAnimationDuration()/2) {
                    slime.hurt();
                }
                slime.setDelay(0.08f);
            }
            if (slime.isDeath()) {
                slime.decreaseDelay(delta);
                if (slime.getDelay() <= 0) {
                    slime.remove();
                    iterator.remove();
                    batDeathListener.onSlimeDeath(slime.getX(),slime.getY());
                }
            }
            if (!isColliding) {
                slime.setNotAttacking();
            }

            if(knight.isDeath){
                slime.setHoping(false);
            }

            if (doubleLifeEnabled) {
                if (slime.getHealth() == Settings.SLIME_HEALTH) {
                    slime.setHealth(Settings.SLIME_HEALTH * Settings.SLIME_HEALTH_INCREASE_LEVEL1);
                    Gdx.app.log("Double Life", "La vida de un slime se ha duplicado.");
                }
            }
            if (tripleLifeEnabled) {
                if (slime.getHealth() == Settings.SLIME_HEALTH * Settings.SLIME_HEALTH_INCREASE_LEVEL1) {
                    slime.setHealth(Settings.SLIME_HEALTH * Settings.SLIME_HEALTH_INCREASE_LEVEL2);
                    Gdx.app.log("Triple Life", "La vida de un slime se ha triplicado.");
                }
            }

            if (quintupleLifeEnabled) {
                if (slime.getHealth() == Settings.SLIME_HEALTH * Settings.SLIME_HEALTH_INCREASE_LEVEL2) {
                    slime.setHealth(Settings.SLIME_HEALTH * Settings.SLIME_HEALTH_INCREASE_LEVEL3);
                    Gdx.app.log("Quintuple Life", "La vida de un slime se ha quintuplicado.");
                }
            }

            slime.act(delta);
        }

    }

    public Array<Slime> getSlimes() {
        return slimes;
    }

    public boolean isHopingRight() {
        return hopingRight;
    }

    public void setHopingRight(boolean hopingRight) {
        this.hopingRight = hopingRight;
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
