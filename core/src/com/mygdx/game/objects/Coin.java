package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.helpers.AssetManager;

public class Coin extends Actor {
    public Animation<TextureRegion> coinAnimation;
    public float stateTime;
    private float despawnTimer;
    private boolean collected;

    public Coin() {
        coinAnimation = AssetManager.coinAnimation;
        setSize(32, 32);
        despawnTimer = 0f;
        collected = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(coinAnimation.getKeyFrame(stateTime,true), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        despawnTimer += delta;
        if (despawnTimer >= 10 && !collected) {
            System.out.println("Moneda no recogida y despawneada");
            remove();
        }
    }

    public void collect() {
        collected = true;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
