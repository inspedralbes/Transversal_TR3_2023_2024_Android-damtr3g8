package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.helpers.BatDeathListener;
import com.mygdx.game.objects.Background;
import com.mygdx.game.objects.FireBat;
import com.mygdx.game.objects.FireBatSpawner;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

public class GameScreen implements Screen, BatDeathListener {
    private Stage stage;
    private Batch batch;
    OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private GlyphLayout textLayout;
    private Knight knight;
    private FireBatSpawner firebatSpawner;
    private float spawnTimer;
    private Background background;
    private Skin skin = AssetManager.skin;
    private Label scoreLabel;
    private int score = 0;
    private boolean doubleLifeEnabled = false;
    private boolean tripleLifeEnabled = false;
    private boolean quintupleLifeEnabled = false;

    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();

    public GameScreen() {
        /*if (musicEnabled) {
            AssetManager.music.setVolume(musicVolume);
            AssetManager.music.play();
        }*/

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();

        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setPosition(20, 900);
        background = new Background(AssetManager.fondo);
        knight = new Knight(Settings.KNIGHT_STARTX, Settings.KNIGHT_STARTY, Settings.KNIGHT_WIDTH, Settings.KNIGHT_HEIGHT);

        stage.addActor(background);
        stage.addActor(knight);
        stage.addActor(scoreLabel);

        firebatSpawner = new FireBatSpawner(knight, this);
        spawnTimer = 0f;
        spawnFirebat();

        knight.setName("Knight");

        //Gdx.input.setInputProcessor(new InputHandler(this));
    }

    @Override
    public void show() {

    }

    private void drawElements() {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0, 0, 1, 1));
        shapeRenderer.rect(knight.getX(), knight.getY(), knight.getWidth(), knight.getHeight());
        shapeRenderer.rect(background.getX(), background.getY(), background.getWidth(), background.getHeight());
        for (FireBat firebat : firebatSpawner.getFirebats()) {
            firebat.drawBounds(shapeRenderer);
        }

        shapeRenderer.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


        firebatSpawner.update(delta);

        if (!knight.isDeath) {
            spawnTimer += delta;
            if (spawnTimer >= Settings.FIREBAT_SPAWNER) {
                spawnFirebat();
                spawnTimer = 0f;
            }
        }
        drawElements();
        handleInput(delta);
    }

    private void spawnFirebat() {
        float startX, startY;
        startX = MathUtils.randomBoolean() ? -50 : Settings.GAME_WIDTH + 50;
        startY = MathUtils.random(0, Settings.GAME_HEIGHT);

        Array<FireBat> fireBats = firebatSpawner.getFirebats();
        firebatSpawner.spawnFirebat(startX, startY);
        for (FireBat firebat : fireBats) {
            stage.addActor(firebat);
        }
        //Gdx.app.log("Spawn", "Nuevo murciélago creado en: " + startX + ", " + startY);
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
    public void onBatDeath() {
        if (score >= 0 && score < 100) {
            score += Settings.FIREBAT_SCORE;
        } else if (score >= 100 && score < 300) {
            score += Settings.FIREBAT_SCORE + Settings.FIREBAT_SCORE_INCREASE_LEVEL1;
            if (!doubleLifeEnabled) {
                doubleLifeEnabled = true;
                firebatSpawner.setDoubleLifeEnabled();
                Gdx.app.log("Double Life", "La vida de los siguientes murciélagos se ha duplicado.");
            }
        } else if (score >= 300 && score < 600) {
            score += Settings.FIREBAT_SCORE + Settings.FIREBAT_SCORE_INCREASE_LEVEL2;
            if (!tripleLifeEnabled) {
                tripleLifeEnabled = true;
                firebatSpawner.setTripleLifeEnabled();
                Gdx.app.log("Triple Life", "La vida de los siguientes murciélagos se ha triplicado.");
            }
        } else if (score >= 600) {
            score += Settings.FIREBAT_SCORE + +Settings.FIREBAT_SCORE_INCREASE_LEVEL3;
            if (!quintupleLifeEnabled) {
                quintupleLifeEnabled = true;
                firebatSpawner.setQuintupleLifeEnabled();
                Gdx.app.log("Quintuple Life", "La vida de los siguientes murciélagos se ha quintuplicado.");
            }
        }
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
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
        AssetManager.dispose();
    }

}
