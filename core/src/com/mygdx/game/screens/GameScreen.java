package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.Background;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

public class GameScreen implements Screen {
    private Stage stage;
    //private ScrollHandler scrollHandler;
    private Batch batch;
    OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private GlyphLayout textLayout;
    private Knight knight;
    private Background background;
    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();

    public GameScreen(){
        /*if (musicEnabled) {
            AssetManager.music.setVolume(musicVolume);
            AssetManager.music.play();
        }*/

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();


        background = new Background(AssetManager.fondo);
        //scrollHandler = new ScrollHandler();
        knight = new Knight(Settings.KNIGHT_STARTX,Settings.KNIGHT_STARTY,Settings.KNIGHT_WIDTH,Settings.KNIGHT_HEIGHT);

        stage.addActor(background);
        stage.addActor(knight);

        knight.setName("Knight");

        //Gdx.input.setInputProcessor(new InputHandler(this));
    }
    @Override
    public void show() {

    }

    private void drawElements() {
        // Recollim les propietats del batch de l'stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(new Color(0, 0, 1, 1));


        shapeRenderer.rect(knight.getX(), knight.getY(), knight.getWidth(), knight.getHeight());
        shapeRenderer.rect(background.getX(),background.getY(),background.getWidth(),background.getHeight());

        shapeRenderer.end();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        drawElements();
        handleInput(delta);
    }
    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            knight.moverIzquierda(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            knight.moverDerecha(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            knight.moverArriba(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            knight.moverAbajo(delta);
        }

        /*if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                knight.moverArribaIzquierda(delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                knight.moverArribaDerecha(delta);
            } else {
                knight.moverArriba(delta);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                knight.moverAbajoIzquierda(delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                knight.moverAbajoDerecha(delta);
            } else {
                knight.moverAbajo(delta);
            }*/
        else {
            knight.pararMovimiento();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            knight.attack();
        }
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
