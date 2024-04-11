package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;

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
                    Json json = new Json();
                    json.setOutputType(JsonWriter.OutputType.json);
                    LoginData loginData = new LoginData(username, password);
                    String jsonData = json.toJson(loginData);
                    System.out.println(jsonData);
                    loginUser(jsonData);
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
    static class LoginData {
        private String user;
        private String contrasenya;

        public LoginData(String username, String passwd) {
            this.user = username;
            this.contrasenya = passwd;
        }
    }
    public void loginUser(String jsonData){
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest()
                .method(Net.HttpMethods.POST)
                .url("http://192.168.206.177:3000/loginUser")
                .header("Content-Type", "application/json") // Establece el encabezado Content-Type como aplicación/json
                .content(jsonData) // Establece el cuerpo de la solicitud como el JSON que has creado
                .build();
        MyHttpResponseListener httpResponseListener = new MyHttpResponseListener();
        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
    }

    private class MyHttpResponseListener implements Net.HttpResponseListener{

        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            System.out.println(httpResponse.getResultAsString());
        }

        @Override
        public void failed(Throwable t) {
            System.out.println("falle");
        }

        @Override
        public void cancelled() {
            System.out.println("cancele");
        }
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
