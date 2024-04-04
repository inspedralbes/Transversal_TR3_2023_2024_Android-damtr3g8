package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;

public class Knight extends Actor {
    private Vector2 position;
    private Stage stage;
    private int width, height;
    public boolean isRunningRight, isRunningFront, isRunningBack, isAttacking, isFacingRight, isHurting, isDeath;
    private boolean isAnimating = false;
    private Rectangle knight;
    public Animation<TextureRegion> idleAnimation, runRightAnimation, attackRightAnimation, hurtrightAnimation, runFrontAnimation, runBackAnimation, deathAnimation;
    public float stateTime;
    public int health = Settings.KNIGHT_HEALTH;
    Inventory inventory;

    public Knight(float x, float y, int width, int height,Inventory inventory) {
        this.width = width;
        this.height = height;
        this.inventory = inventory;
        position = new Vector2(x, y);
        isFacingRight = true;
        knight = new Rectangle();
        idleAnimation = AssetManager.idlerightanimation;
        runRightAnimation = AssetManager.runrightanimation;
        attackRightAnimation = AssetManager.attackrightanimation;
        hurtrightAnimation = AssetManager.hurtrightanimation;
        deathAnimation = AssetManager.deathanimation;
        runFrontAnimation = AssetManager.runfrontanimation;
        runBackAnimation = AssetManager.runbackanimation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float drawX = isFacingRight ? position.x : position.x + width;
        float drawWidth = isFacingRight ? width : -width;
        TextureRegion currentFrame;

        if (isDeath) {
            currentFrame = deathAnimation.getKeyFrame(stateTime, false);
            if (deathAnimation.isAnimationFinished(stateTime)) {
                remove();
            }
        } else {
            if (isAttacking) {
                currentFrame = attackRightAnimation.getKeyFrame(stateTime, false);
                if (attackRightAnimation.isAnimationFinished(stateTime)) {
                    isAttacking = false;
                }
            } else if (isHurting) {
                currentFrame = hurtrightAnimation.getKeyFrame(stateTime, false);
                if (hurtrightAnimation.isAnimationFinished(stateTime)) {
                    isHurting = false;
                }
            } else if (isRunningRight) {
                currentFrame = runRightAnimation.getKeyFrame(stateTime, true);
            } else if (isRunningFront) {
                currentFrame = runFrontAnimation.getKeyFrame(stateTime, true);
            } else if (isRunningBack) {
                currentFrame = runBackAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
            }
        }


        batch.draw(currentFrame, drawX, getY(), drawWidth, getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        knight.set(position.x, position.y, width, height);
        checkCoinCollision();
        checkPotionCollision();
    }


    public void moverIzquierda(float delta) {
        if (!isAttacking && !isDeath) {
            float newX = position.x - Settings.KNIGHT_SPEED * delta;
            if (newX > 0) {
                position.x = newX;
            }
            isFacingRight = false;
            isRunningRight = true;
        }
    }

    public void moverDerecha(float delta) {
        if (!isAttacking && !isDeath) {
            float newX = position.x + Settings.KNIGHT_SPEED * delta;
            if (newX + width < Settings.GAME_WIDTH) {
                position.x = newX;
            }
            isFacingRight = true;
            isRunningRight = true;
        }
    }

    public void moverArriba(float delta) {
        if (!isAttacking && !isDeath) {
            float newY = position.y + Settings.KNIGHT_SPEED * delta;
            if (newY + height < Settings.GAME_HEIGHT) {
                position.y = newY;
            }
            isRunningBack = true;
        }
    }

    public void moverAbajo(float delta) {
        if (!isAttacking && !isDeath) {
            float newY = position.y - Settings.KNIGHT_SPEED * delta;
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
            addAction(Actions.sequence(
                    Actions.delay(attackRightAnimation.getAnimationDuration()),
                    Actions.run(() -> {
                        //isAnimating = false;
                        isAttacking = false;
                    })
            ));
        }
    }

    public void hurt() {
        if (!isHurting && !isAnimating) {
            isAnimating = true;
            isHurting = true;
            stateTime = 0f;
            this.receiveDamage(Settings.SLIME_DAMAGE_PER_ATTACK);
            addAction(Actions.sequence(
                    Actions.delay(hurtrightAnimation.getAnimationDuration()),
                    Actions.run(() -> {
                        isAnimating = false;
                        isHurting = false;
                    })
            ));
        }
    }

    public void receiveDamage(int damage) {
        health -= damage;
        System.out.println("Knight Health: " + health);
        if (health <= 0) {
            health = 0;
            death();
        }
    }

    public void death() {
        if (!isDeath) {
            isDeath = true;
            stateTime = 0f;
        }
    }

    public void pararMovimiento() {
        isRunningFront = false;
        isRunningRight = false;
        isRunningBack = false;
    }

    private void checkCoinCollision() {
        if (stage == null) return;
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof Coin && this.getBoundingRectangle().overlaps(((Coin) actor).getBoundingRectangle())) {
                Coin coin = (Coin) actor;
                collectCoin(coin);
                coin.collect();
                break;
            }
        }
    }

    private void collectCoin(Coin coin) {
        System.out.println("Moneda Recogida");
        Item coinItem = new Item("Moneda", "Objeto para comprar cosas de la tienda", AssetManager.cointexture, 3);
        if (inventory.contains(coinItem)) {
            Item existingCoinItem = inventory.getItemByName(coinItem.getName());
            existingCoinItem.incrementQuantity(existingCoinItem.getQuantity());
        } else {
            inventory.addItem(coinItem); // Agrega el ítem al inventario si no está presente
        }
        coin.remove();
    }

    private void checkPotionCollision() {
        if (stage == null) return;
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof RedPotion && this.getBoundingRectangle().overlaps(((RedPotion) actor).getBoundingRectangle())) {
                RedPotion potion = (RedPotion) actor;
                collectPotion(potion);
                potion.collect();
                break;
            }
        }
    }

    private void collectPotion(RedPotion potion) {
        System.out.println("Poción Recogida");
        /*int maxHealth = getMaxHealth();
        int currentHealth = getHealth();
        int newHealth = currentHealth + Settings.FULLPOTION_HEALTH;
        if (newHealth > maxHealth) {
            newHealth = maxHealth;
        }
        setHealth(newHealth);*/
        Item potionItem = new Item("Pocion de cura", "Cura 100 de vida", AssetManager.redpotiontexture, 1);
        if (inventory.contains(potionItem)) {
            Item existingPotionItem = inventory.getItemByName(potionItem.getName());
            existingPotionItem.incrementQuantity(existingPotionItem.getQuantity());
        } else {
            inventory.addItem(potionItem); // Agrega el ítem al inventario si no está presente
        }
        potion.remove();
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

    public int getHealth() {
        return health;
    }
    public void setHealth(int newHealth) {
        if (newHealth > 0) {
            health = newHealth;
        } else {
            health = 0;
            death();
        }
    }


    public int getMaxHealth() {
        return Settings.KNIGHT_MAX_HEALTH;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
