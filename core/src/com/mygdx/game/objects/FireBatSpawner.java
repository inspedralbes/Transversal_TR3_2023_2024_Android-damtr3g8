package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.Settings;

public class FireBatSpawner {
    private Array<FireBat> firebats;
    private boolean flyingRight;
    private boolean isAttacking;
    private Knight knight;

    public FireBatSpawner(Knight knight) {
        firebats = new Array<>();
        this.knight = knight;
    }

    public void spawnFirebat(float startX, float startY) {
        FireBat firebat = new FireBat();
        firebat.setPosition(startX, startY);
        firebats.add(firebat);

        // Establece la dirección de vuelo del murciélago
        float knightX = knight.getX();
        float knightY = knight.getY();
        flyingRight = startX > knightX; // Determina la dirección de vuelo basándose en la posición del caballero
        firebat.setFlyingRight(flyingRight);

        // Calcula la velocidad en función de la distancia que el murciélago debe recorrer
        float directionX = knightX - startX;
        float directionY = knightY - startY;
        float distance = (float) Math.sqrt(directionX * directionX + directionY * directionY);

        // Normaliza la dirección
        float speed = Settings.FIREBAT_SPEED; // Ajusta la velocidad según sea necesario

        // Calcula la duración basada en la distancia
        float duration = distance / speed;

        // Agrega una acción de mover hacia la posición del caballero
        firebat.addAction(Actions.sequence(
                Actions.moveTo(knightX, knightY, duration),
                Actions.run(() -> {
                    // Mensaje de registro cuando el murciélago llega a su destino
                    Gdx.app.log("Destino", "Murciélago ha llegado a: " + knightX + ", " + knightY);
                }),
                Actions.removeActor() // Elimina el actor del escenario cuando llega al caballero
        ));
    }


    public void update(float delta) {
        // Actualiza todos los murciélagos
        for (FireBat firebat : firebats) {
            // Calcula la nueva dirección de vuelo hacia el caballero
            float knightX = knight.getX();
            float knightY = knight.getY();
            float directionX = knightX - firebat.getX();
            float directionY = knightY - firebat.getY();

            // Normaliza la dirección
            float distance = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            float speed = Settings.FIREBAT_SPEED;
            float duration = distance / speed;

            // Establece la nueva dirección de vuelo
            firebat.setFlyingRight(firebat.getX() > knightX);


            float offsetX = MathUtils.random(-Settings.KNIGHT_WIDTH/2, Settings.KNIGHT_WIDTH/2);
            float offsetY = MathUtils.random(-Settings.KNIGHT_HEIGHT/2, Settings.KNIGHT_HEIGHT/2);
            float destinationX = knightX + offsetX;
            float destinationY = knightY + offsetY;

            // Actualiza la acción de mover hacia el caballero
            firebat.clearActions();
            firebat.addAction(Actions.sequence(
                    Actions.moveTo(destinationX, destinationY, duration),
                    Actions.run(() -> {
                        if (firebat.isColliding(knight)) {
                            Gdx.app.log("Colisión", "Murciélago ha colisionado con el caballero!");
                            //setAttacking();
                            //firebat.setAttacking(isAttacking);
                            firebat.setDeath();
                        }
                    }),
                    Actions.removeActor()
            ));

            firebat.act(delta); // Actualiza el murciélago
        }
    }

    public void setAttacking(){
        if(!isAttacking){
            isAttacking = true;
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

