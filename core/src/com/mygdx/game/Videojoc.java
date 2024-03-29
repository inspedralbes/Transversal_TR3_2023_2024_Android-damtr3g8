package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.screens.LoginScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.utils.AppPreferences;

public class Videojoc  extends Game {
	public AppPreferences preferences;
	@Override
	public void create() {
		preferences = new AppPreferences();
		AssetManager.load();
		setScreen(new LoginScreen(this));
	}


	@Override
	public void dispose() {
		super.dispose();
		AssetManager.dispose();
	}

	public AppPreferences getPreferences() {
		return preferences;
	}
}
