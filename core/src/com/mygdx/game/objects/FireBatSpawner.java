package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public class FireBatSpawner {
    private Array<FireBat> firebats;

    public FireBatSpawner() {
        firebats = new Array<>();
    }

    public void spawnFirebat(float startX, float startY, float endX, float endY, Animation<TextureRegion> animation) {
        FireBat firebat = new FireBat(animation);
        firebat.setPosition(startX, startY);
        firebats.add(firebat);

        // Calcula la velocidad en función de la distancia que el murciélago debe recorrer
        float distanceX = endX - startX;
        float distanceY = endY - startY;
        float speed = 100; // Ajusta la velocidad según sea necesario
        float duration = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY) / speed;

        // Agrega una acción de mover hacia la posición final
        firebat.addAction(Actions.sequence(
                Actions.moveTo(endX, endY, duration),
                Actions.removeActor() // Elimina el actor del stage cuando llega al final
        ));
    }

    public void update(float delta) {
        // Actualiza todos los murciélagos
        for (FireBat firebat : firebats) {
            firebat.act(delta);
        }
    }

    public Array<FireBat> getFirebats() {
        return firebats;
    }
}
