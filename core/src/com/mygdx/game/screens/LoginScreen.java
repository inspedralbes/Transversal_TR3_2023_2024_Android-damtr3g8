package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LoginScreen implements Screen {

    final Videojoc game;
    private Stage stage;
    private Batch batch;
    Skin skin = AssetManager.skin;
    Table table;
    Label usernameLabel,passwordLabel;
    TextButton loginButton,registerButton;
    public LoginScreen(Videojoc game) {
        this.game = game;
    }

    @Override
    public void show() {
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        stage = new Stage(viewport);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.center();

        usernameLabel = new Label("Username:", skin);
        final TextField usernameField = new TextField("", skin);
        passwordLabel = new Label("Password:", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        loginButton = new TextButton("Login", skin);
        registerButton = new TextButton("Register", skin);

        table.add(usernameLabel).padBottom(10).align(Align.center);
        table.add(usernameField).width(200).padBottom(20);
        table.row();
        table.add(passwordLabel).padBottom(10).align(Align.center);
        table.add(passwordField).width(200).padBottom(20);
        table.row();
        table.add(loginButton).width(250).padBottom(10).padRight(10);
        table.add(registerButton).width(250).padBottom(10);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (!username.isEmpty() && !password.isEmpty()) {

                } else {
                    // Mostrar un mensaje de error si algún campo está vacío
                    // Por ejemplo, puedes usar una ventana emergente o una etiqueta de error
                    // Aquí simplemente imprimiré un mensaje en la consola
                    System.out.println("Por favor, complete todos los campos.");
                }
            }
        });

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

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
