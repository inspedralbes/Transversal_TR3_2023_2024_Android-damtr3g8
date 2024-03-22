package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
public class AssetManager {
    public static Texture fondo,coinSheet;
    public static Texture sheetidleright,sheetrunright,sheetattackright,sheethurtright,sheetdeath;
    public static Texture sheetrunfront;
    public static Texture sheetrunback;
    public static Texture slimeHopSheet,slimeAttackSheet,slimeHurtDeathSheet,slimeIdleSheet;
    public static TextureAtlas flyfirebatAtlas,attackfirebatAtlas,deathfirebatAtlas,hurtfirebatAtlas;
    public static Texture sleepfirebat;
    public static TextureRegion[] firebatFlyingFrames,firebatAttackingFrames,firebatDeathFrames,firebatHurtFrames;
    public static TextureRegion[] idleright,runright,attackright,hurtright,death;
    public static TextureRegion[] runfront;
    public static TextureRegion[] runback;
    public static TextureRegion[] slimeHop,slimeAttack,slimeHurt,slimeDeath,slimeIdle;
    public static TextureRegion[] coinRegion;
    public static Animation<TextureRegion> idlerightanimation,runrightanimation,attackrightanimation,hurtrightanimation,deathanimation;
    public static Animation<TextureRegion> runfrontanimation;
    public static Animation<TextureRegion> runbackanimation;
    public static Animation<TextureRegion> firebatFlyinganimation,firebatAttackinganimation,firebatDeathanimation,firebatHurtanimation;
    public static Animation<TextureRegion> slimeHopAnimation,slimeAttackAnimation,slimeHurtAnimation,slimeDeathAnimation,slimeIdleAnimation;
    public static Animation<TextureRegion> coinAnimation;
    public static Skin skin;
    public static void load(){
        sheets();
        knight();
        firebat();
        slime();
        coins();
        skin = new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
    }



    static void sheets(){
        fondo = new Texture(Gdx.files.internal("Fondo/fondo.png"));
        coinSheet = new Texture(Gdx.files.internal("Coins/coins.png"));

        sheetidleright = new Texture(Gdx.files.internal("Knight/Right/idleright.png"));
        sheetrunright = new Texture(Gdx.files.internal("Knight/Right/runright.png"));
        sheetattackright = new Texture(Gdx.files.internal("Knight/Right/attackright.png"));
        sheethurtright = new Texture(Gdx.files.internal("Knight/Right/hurtright.png"));
        sheetdeath =  new Texture(Gdx.files.internal("Knight/Right/death.png"));
        sheetrunfront = new Texture(Gdx.files.internal("Knight/Front/runfront.png"));
        sheetrunback = new Texture(Gdx.files.internal("Knight/Back/runback.png"));

        flyfirebatAtlas = new TextureAtlas(Gdx.files.internal("Bat/Fire/Fly/firebatfly.txt"));
        attackfirebatAtlas = new TextureAtlas(Gdx.files.internal("Bat/Fire/Attack/firebatattack.txt"));
        hurtfirebatAtlas = new TextureAtlas(Gdx.files.internal("Bat/Fire/Hurt/firebathurt.txt"));
        deathfirebatAtlas = new TextureAtlas(Gdx.files.internal("Bat/Fire/Death/firebatdeath.txt"));
        sleepfirebat = new Texture(Gdx.files.internal("Bat/Fire/Sleep/firebatsleep.png"));

        slimeHopSheet = new Texture(Gdx.files.internal("Slime/Hop/hop.png"));
        slimeAttackSheet = new Texture(Gdx.files.internal("Slime/Attack/attack.png"));
        slimeHurtDeathSheet = new Texture(Gdx.files.internal("Slime/DeathHurt/deathhurt.png"));
        slimeIdleSheet = new Texture(Gdx.files.internal("Slime/Idle/idle.png"));
    }
    static void knight(){
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

        hurtright = new TextureRegion[9];
        hurtright[0] = new TextureRegion(sheethurtright, 0, 0, 16, 17);
        hurtright[1] = new TextureRegion(sheethurtright, 16, 0, 16, 15);
        hurtright[2] = new TextureRegion(sheethurtright, 32, 0, 16, 18);
        hurtright[3] = new TextureRegion(sheethurtright, 48, 0, 16, 18);
        hurtright[4] = new TextureRegion(sheethurtright, 64, 0, 16, 18);
        hurtright[5] = new TextureRegion(sheethurtright, 80, 0, 16, 18);
        hurtright[6] = new TextureRegion(sheethurtright, 96, 0, 16, 18);
        hurtright[7] = new TextureRegion(sheethurtright, 112, 0, 16, 18);
        hurtright[8] = new TextureRegion(sheethurtright, 128, 0, 16, 18);
        hurtrightanimation = new Animation<>(0.08f,hurtright);

        death = new TextureRegion[8];
        death[0] = new TextureRegion(sheetdeath, 0, 0, 18, 17);
        death[1] = new TextureRegion(sheetdeath, 18, 0, 16, 17);
        death[2] = new TextureRegion(sheetdeath, 34, 0, 16, 17);
        death[3] = new TextureRegion(sheetdeath, 50, 0, 15, 16);
        death[4] = new TextureRegion(sheetdeath, 65, 0, 15, 16);
        death[5] = new TextureRegion(sheetdeath, 80, 0, 18, 15);
        death[6] = new TextureRegion(sheetdeath, 98, 0, 20, 14);
        death[7] = new TextureRegion(sheetdeath, 118, 0, 21, 13);
        deathanimation = new Animation<>(0.08f, death);

        runfront = new TextureRegion[8];
        runfront[0] = new TextureRegion(sheetrunfront, 0, 0, 17, 17);
        runfront[1] = new TextureRegion(sheetrunfront, 17, 0, 17, 16);
        runfront[2] = new TextureRegion(sheetrunfront, 34, 0, 17, 16);
        runfront[3] = new TextureRegion(sheetrunfront, 51, 0, 17, 17);
        runfront[4] = new TextureRegion(sheetrunfront, 68, 0, 17, 17);
        runfront[5] = new TextureRegion(sheetrunfront, 85, 0, 17, 16);
        runfront[6] = new TextureRegion(sheetrunfront, 102, 0, 16, 16);
        runfront[7] = new TextureRegion(sheetrunfront, 118, 0, 16, 16);
        runfrontanimation = new Animation<>(0.08f, runfront);

        runback = new TextureRegion[8];
        runback[0] = new TextureRegion(sheetrunback, 0, 0, 17, 17);
        runback[1] = new TextureRegion(sheetrunback, 17, 0, 17, 16);
        runback[2] = new TextureRegion(sheetrunback, 34, 0, 17, 16);
        runback[3] = new TextureRegion(sheetrunback, 51, 0, 17, 17);
        runback[4] = new TextureRegion(sheetrunback, 68, 0, 17, 17);
        runback[5] = new TextureRegion(sheetrunback, 85, 0, 17, 16);
        runback[6] = new TextureRegion(sheetrunback, 102, 0, 18, 16);
        runback[7] = new TextureRegion(sheetrunback, 120, 0, 18, 17);
        runbackanimation = new Animation<>(0.08f, runback);
    }
    static void firebat(){
        firebatFlyingFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            firebatFlyingFrames[i] = flyfirebatAtlas.findRegion("BatFire_Flying-" + i);
        }
        firebatFlyinganimation = new Animation<>(0.08f,firebatFlyingFrames);

        firebatAttackingFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            firebatAttackingFrames[i] = attackfirebatAtlas.findRegion("BatFire_Attack-" + i);
        }
        firebatAttackinganimation = new Animation<>(0.08f,firebatAttackingFrames);

        firebatDeathFrames = new TextureRegion[7];
        for (int i = 0; i < 7; i++) {
            firebatDeathFrames[i] = deathfirebatAtlas.findRegion("BatFire_Death-" + i);
        }
        firebatDeathanimation = new Animation<>(0.08f,firebatDeathFrames);

        firebatHurtFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            firebatHurtFrames[i] = hurtfirebatAtlas.findRegion("BatFire_Hurt-" + i);
        }
        firebatHurtanimation = new Animation<>(0.12f,firebatHurtFrames);
    }
    static void slime(){
        slimeHop = new TextureRegion[13];
        for (int i = 0; i < slimeHop.length; i++) {
            slimeHop[i] = new TextureRegion(slimeHopSheet, i * 64, 0, 64, 64);
        }
        slimeHopAnimation = new Animation<>(0.08f,slimeHop);

        slimeAttack = new TextureRegion[8];
        for (int i = 0; i < slimeAttack.length; i++) {
            slimeAttack[i] = new TextureRegion(slimeAttackSheet, i * 64, 0, 64, 64);
        }
        slimeAttackAnimation = new Animation<>(0.08f,slimeAttack);

        slimeHurt = new TextureRegion[3];
        for (int i = 0; i < slimeHurt.length; i++) {
            slimeHurt[i] = new TextureRegion(slimeHurtDeathSheet, i * 64, 0, 64, 64);
        }
        slimeHurtAnimation = new Animation<>(0.28f,slimeHurt);

        slimeDeath = new TextureRegion[9];
        for (int i = 0; i < slimeDeath.length; i++) {
            slimeDeath[i] = new TextureRegion(slimeHurtDeathSheet, i * 64, 0, 64, 64);
        }
        slimeDeathAnimation = new Animation<>(0.08f,slimeDeath);

        slimeIdle = new TextureRegion[6];
        for (int i = 0; i < slimeIdle.length; i++) {
            slimeIdle[i] = new TextureRegion(slimeIdleSheet, i * 64, 0, 64, 64);
        }
        slimeIdleAnimation = new Animation<>(0.08f,slimeIdle);


    }
    private static void coins() {
        coinRegion = new TextureRegion[6];
        for (int i = 0; i < coinRegion.length; i++) {
            coinRegion[i] = new TextureRegion(coinSheet, i * 16, 0, 16, 16);
        }
        coinAnimation = new Animation<>(0.08f,coinRegion);
    }

    public static void dispose(){
        fondo.dispose();
        sheetidleright.dispose();
        sheetrunright.dispose();
        sheetrunfront.dispose();
        sheetrunback.dispose();
        sheetattackright.dispose();
        sheethurtright.dispose();
        sheetdeath.dispose();
        flyfirebatAtlas.dispose();
        attackfirebatAtlas.dispose();
        deathfirebatAtlas.dispose();
        sleepfirebat.dispose();
        slimeHopSheet.dispose();
        slimeAttackSheet.dispose();
        slimeHurtDeathSheet.dispose();
        slimeIdleSheet.dispose();
        coinSheet.dispose();
        skin.dispose();
    }
}
