package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

public class MainMenuScreen implements Screen {
    final Videojoc game;
    private Stage stage;
    private Batch batch;
    Skin skin = AssetManager.skin;
    Table options, window;

    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();
    TextButton startGameButton, optionsButton, loginButton, salirButton;
    Slider volumeMusicSlider, soundMusicSlider;
    CheckBox musicCheckbox, soundCheckbox;
    Label titleLabel, volumeMusicLabel, volumeSoundLabel, musicOnOffLabel, soundOnOffLabel, tituloGame;

    public MainMenuScreen(Videojoc game) {
        this.game = game;
    }

    @Override
    public void show() {
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        stage = new Stage(viewport);
        window = new Table();
        window.setFillParent(true);
        window.center();

        startGameButton = new TextButton("Comenzar juego", skin);
        optionsButton = new TextButton("Opciones", skin);
        loginButton = new TextButton("Login", skin);

        window.add(startGameButton).width(450).padBottom(20).row();
        window.add(optionsButton).width(450).padBottom(20).row();
        window.add(loginButton).width(450).padBottom(20).row();

        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setVisible(false);
                options.setVisible(true);
            }
        });
        options = new Table();
        options.setSize(640, 480);
        options.setPosition((Settings.GAME_WIDTH / 2) - (options.getWidth() / 2), (Settings.GAME_HEIGHT / 2) - (options.getHeight() / 2));
        options.setVisible(false);

        titleLabel = new Label("Opciones", skin);
        titleLabel.setFontScale(4f);
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeMusicLabel.setFontScale(2f);
        volumeSoundLabel = new Label("Music", skin);
        volumeSoundLabel.setFontScale(2f);
        musicOnOffLabel = new Label("Sound Volume", skin);
        musicOnOffLabel.setFontScale(2f);
        soundOnOffLabel = new Label("Sound Effect", skin);
        soundOnOffLabel.setFontScale(2f);
        salirButton = new TextButton("Salir", skin);

        volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(this.game.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                float volume = volumeMusicSlider.getValue();
                game.getPreferences().setMusicVolume(volume);
                // Establecer el volumen de la música
                //AssetManager.music.setVolume(volume);
                return false;
            }
        });

        musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                if (enabled) {
                    // Reproducir la música si está habilitada
                    //AssetManager.music.play();
                } else {
                    // Pausar la música si está deshabilitada
                    //AssetManager.music.pause();
                }
                return false;
            }
        });

        soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(this.game.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                float volume = soundMusicSlider.getValue();
                game.getPreferences().setSoundVolume(volume);
                return false;
            }
        });

        soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        salirButton = new TextButton("Salir", skin);
        salirButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                window.setVisible(true);
                options.setVisible(false);
            }
        });

        options.add(titleLabel).colspan(2).row();
        options.add(volumeMusicLabel);
        options.add(volumeMusicSlider).width(400).row();
        options.add(volumeSoundLabel);
        options.add(musicCheckbox).row();
        options.add(musicOnOffLabel);
        options.add(soundMusicSlider).width(400).row();
        options.add(soundOnOffLabel);
        options.add(soundCheckbox).row();
        options.add(salirButton).colspan(2).padBottom(5).row();

        loginButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(new LoginScreen(game));
            }
        });
        stage.addActor(window);
        stage.addActor(options);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
