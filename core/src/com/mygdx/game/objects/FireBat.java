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
    private Animation<TextureRegion> flyanimation, attackanimation, deathanimation;
    private float stateTime;
    private boolean flyingRight;
    private boolean isAttacking;
    boolean isDeath;

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
                //remove();
            }
        } else {
            if(isAttacking){
                currentFrame =  attackanimation.getKeyFrame(stateTime, true) ;
            }
            else{
                currentFrame =  flyanimation.getKeyFrame(stateTime, true);
            }
        }
        /*if (flyingRight) {
            batch.draw(currentFrame, getX() + getWidth(), getY(), -getWidth(), getHeight());
        } else {*/
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        //}
    }

    public void setFlyingRight(boolean flyingRight) {
        this.flyingRight = flyingRight;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public void setDeath(){
        isDeath = true;
    }

    public void drawBounds(ShapeRenderer shapeRenderer) {
        // Establece el color de los bordes
        shapeRenderer.setColor(new Color(1, 2, 1, 1));
        // Dibuja el borde del actor (murciélago)
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    public boolean isColliding(Knight knight) {
        // Define los puntos de colisión del caballero
        Rectangle knightBounds = new Rectangle(knight.getX(), knight.getY(), knight.getWidth(), knight.getHeight());
        float knightCenterX = knightBounds.x + knightBounds.width / 2;
        float knightCenterY = knightBounds.y + knightBounds.height / 2;

        // Verifica la colisión en cada sub-rectángulo del caballero
        Rectangle batBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        boolean collisionDetected = false;
        if (batBounds.overlaps(knightBounds)) {
            collisionDetected = true; // Colisión con el rectángulo del caballero
        } else if (batBounds.contains(knightBounds.x, knightBounds.y)) {
            collisionDetected = true; // Colisión con la parte superior izquierda del caballero
        } else if (batBounds.contains(knightBounds.x + knightBounds.width, knightBounds.y)) {
            collisionDetected = true; // Colisión con la parte superior derecha del caballero
        } else if (batBounds.contains(knightBounds.x, knightBounds.y + knightBounds.height)) {
            collisionDetected = true; // Colisión con la parte inferior izquierda del caballero
        } else if (batBounds.contains(knightBounds.x + knightBounds.width, knightBounds.y + knightBounds.height)) {
            collisionDetected = true; // Colisión con la parte inferior derecha del caballero
        }

        return collisionDetected;
    }
}
