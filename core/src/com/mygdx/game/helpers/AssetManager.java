package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
public class AssetManager {
    public static Texture sheetidleright,sheetrunright,sheetattackright;
    public static TextureRegion[] idleright,runright,attackright;
    public static Animation<TextureRegion> idlerightanimation,runrightanimation,attackrightanimation;
    public static Skin skin;
    public static void load(){
        sheetidleright = new Texture(Gdx.files.internal("Knight/Right/idleright.png"));
        sheetrunright = new Texture(Gdx.files.internal("Knight/Right/runright.png"));
        sheetattackright = new Texture(Gdx.files.internal("Knight/Right/attackright.png"));

        TextureRegion[][] tmpIdle = TextureRegion.split(sheetidleright, 16, 17);
        idleright = new TextureRegion[4];
        idleright[0] = tmpIdle[0][0];
        idleright[1] = tmpIdle[0][1];
        idleright[2] = tmpIdle[0][2];
        idleright[3] = tmpIdle[0][3];
        idlerightanimation = new Animation<>(0.08f, idleright);

        runright = new TextureRegion[8];
        runright[0] = new TextureRegion(sheetrunright, 0, 0, 18, 17);
        runright[1] = new TextureRegion(sheetrunright, 18, 0, 16, 16);
        runright[2] = new TextureRegion(sheetrunright, 34, 0, 15, 16);
        runright[3] = new TextureRegion(sheetrunright, 49, 0, 16, 17);
        runright[4] = new TextureRegion(sheetrunright, 65, 0, 15, 17);
        runright[5] = new TextureRegion(sheetrunright, 80, 0, 17, 16);
        runright[6] = new TextureRegion(sheetrunright, 97, 0, 18, 16);
        runright[7] = new TextureRegion(sheetrunright, 115, 0, 19, 17);
        runrightanimation = new Animation<>(0.08f, runright);


        attackright = new TextureRegion[6];
        attackright[0] = new TextureRegion(sheetattackright, 0, 0, 26, 16);
        attackright[1] = new TextureRegion(sheetattackright, 26, 0, 22, 19);
        attackright[2] = new TextureRegion(sheetattackright, 48, 0, 21, 20);
        attackright[3] = new TextureRegion(sheetattackright, 69, 0, 21, 17);
        attackright[4] = new TextureRegion(sheetattackright, 90, 0, 22, 17);
        attackright[5] = new TextureRegion(sheetattackright, 112, 0, 18, 17);
        attackrightanimation = new Animation<>(0.08f,attackright);

        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
    }
    public static void dispose(){
        sheetidleright.dispose();
        skin.dispose();
    }
}
