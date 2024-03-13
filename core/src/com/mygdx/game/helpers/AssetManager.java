package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
public class AssetManager {
    public static Skin skin;
    public static void load(){
        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
    }
    public static void dispose(){

    }
}
