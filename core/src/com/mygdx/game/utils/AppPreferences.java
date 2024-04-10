package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "music";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREF_COINS_COLLECTED = "coins.collected";
    private static final String PREF_FULLREDPOTIONS_COLLECTED = "fullredpotions.collected";
    private static final String PREF_HALFREDPOTIONS_COLLECTED = "halfredpotions.collected";
    private static final String PREF_QUARTERREDPOTIONS_COLLECTED = "quarterredpotions.collected";
    private static final String PREF_PURPLEPOTIONS_COLLECTED = "purplepotions.collected";
    private static final String PREF_GREENPOTIONS_COLLECTED = "greenpotions.collected";
    private static final String PREF_YELLOWPOTIONS_COLLECTED = "yellowpotions.collected";
    private static final String PREFS_NAME = "Videojoc";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    /**************SoundEffect***********/
    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }
    /**************Music***********/
    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }
    /**************Volume***********/
    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }
    /**************COINS***********/
    public void setCoinsCollected(int coinsCollected) {
        getPrefs().putInteger(PREF_COINS_COLLECTED, coinsCollected);
        getPrefs().flush();
    }
    public int getCoinsCollected() {
        return getPrefs().getInteger(PREF_COINS_COLLECTED, 0);
    }
    /**************FullRedPotion***********/
    public void setFullPotionsCollected(int potionsCollected) {
        getPrefs().putInteger(PREF_FULLREDPOTIONS_COLLECTED, potionsCollected);
        getPrefs().flush();
    }
    public int getFullPotionsCollected() {
        return getPrefs().getInteger(PREF_FULLREDPOTIONS_COLLECTED, 0);
    }

    public void decreaseFullPotionsCollected() {
        int currentCount = getFullPotionsCollected();
        getPrefs().putInteger(PREF_FULLREDPOTIONS_COLLECTED, Math.max(currentCount - 1, 0));
        getPrefs().flush();
    }
    /**************HalfRedPotion***********/
    public void setHalfPotionsCollected(int potionsCollected) {
        getPrefs().putInteger(PREF_HALFREDPOTIONS_COLLECTED, potionsCollected);
        getPrefs().flush();
    }
    public int getHalfPotionsCollected() {
        return getPrefs().getInteger(PREF_HALFREDPOTIONS_COLLECTED, 0);
    }

    public void decreaseHalfPotionsCollected() {
        int currentCount = getHalfPotionsCollected();
        getPrefs().putInteger(PREF_HALFREDPOTIONS_COLLECTED, Math.max(currentCount - 1, 0));
        getPrefs().flush();
    }
    /**************QuarterRedPotion***********/
    public void setQuarterPotionsCollected(int potionsCollected) {
        getPrefs().putInteger(PREF_QUARTERREDPOTIONS_COLLECTED, potionsCollected);
        getPrefs().flush();
    }
    public int getQuarterPotionsCollected() {
        return getPrefs().getInteger(PREF_QUARTERREDPOTIONS_COLLECTED, 0);
    }
    public void decreaseQuarterPotionsCollected() {
        int currentCount = getQuarterPotionsCollected();
        getPrefs().putInteger(PREF_QUARTERREDPOTIONS_COLLECTED, Math.max(currentCount - 1, 0));
        getPrefs().flush();
    }
    /**************PurplePotion***********/
    public void setForcePotionsCollected(int potionsCollected) {
        getPrefs().putInteger(PREF_PURPLEPOTIONS_COLLECTED, potionsCollected);
        getPrefs().flush();
    }
    public int getForcePotionsCollected() {
        return getPrefs().getInteger(PREF_PURPLEPOTIONS_COLLECTED, 0);
    }
    public void decreaseForcePotionsCollected() {
        int currentCount = getForcePotionsCollected();
        getPrefs().putInteger(PREF_PURPLEPOTIONS_COLLECTED, Math.max(currentCount - 1, 0));
        getPrefs().flush();
    }
    /**************GreenPotion***********/
    public void setEndurancePotionsCollected(int potionsCollected) {
        getPrefs().putInteger(PREF_GREENPOTIONS_COLLECTED, potionsCollected);
        getPrefs().flush();
    }
    public int getEndurancePotionsCollected() {
        return getPrefs().getInteger(PREF_GREENPOTIONS_COLLECTED, 0);
    }
    public void decreaseEndurancePotionsCollected() {
        int currentCount = getEndurancePotionsCollected();
        getPrefs().putInteger(PREF_GREENPOTIONS_COLLECTED, Math.max(currentCount - 1, 0));
        getPrefs().flush();
    }
    /**************YelowPotion***********/
    public void setVelocityPotionsCollected(int potionsCollected) {
        getPrefs().putInteger(PREF_YELLOWPOTIONS_COLLECTED, potionsCollected);
        getPrefs().flush();
    }
    public int getVelocityPotionsCollected() {
        return getPrefs().getInteger(PREF_YELLOWPOTIONS_COLLECTED, 0);
    }
    public void decreaseVelocityPotionsCollected() {
        int currentCount = getVelocityPotionsCollected();
        getPrefs().putInteger(PREF_YELLOWPOTIONS_COLLECTED, Math.max(currentCount - 1, 0));
        getPrefs().flush();
    }
    /**************ItemCount***********/
    public void setItemCount(String itemName, int itemCount) {
        if(itemName.equals("Pocion de cura lleno")){
            setFullPotionsCollected(itemCount);
        } else if (itemName.equals("Pocion de cura medio")){
            setHalfPotionsCollected(itemCount);
        } else if (itemName.equals("Pocion de cura cuarto")) {
            setQuarterPotionsCollected(itemCount);
        } else if (itemName.equals("Pocion de fuerza")) {
            setForcePotionsCollected(itemCount);
        } else if (itemName.equals("Pocion de resistencia")) {
            setEndurancePotionsCollected(itemCount);
        } else if (itemName.equals("Pocion de velocidad")) {
            setVelocityPotionsCollected(itemCount);
        }
    }
}
