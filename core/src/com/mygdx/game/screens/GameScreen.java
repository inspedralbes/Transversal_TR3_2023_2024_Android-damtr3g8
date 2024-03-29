package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.helpers.BatDeathListener;
import com.mygdx.game.objects.Background;
import com.mygdx.game.objects.Coin;
import com.mygdx.game.objects.FireBat;
import com.mygdx.game.objects.FireBatSpawner;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.objects.Slime;
import com.mygdx.game.objects.SlimeSpawner;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

public class GameScreen implements Screen, BatDeathListener {
    private Videojoc game;
    private Stage stage;
    private Batch batch;
    OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private GlyphLayout textLayoutOver, textLayoutFinalScore;
    private Knight knight;
    private FireBatSpawner firebatSpawner;
    private SlimeSpawner slimeSpawner;
    private float spawnFirebatTimer,spawnSlimeTimer;
    private Background background;
    private Skin skin = AssetManager.skin;
    BitmapFont fontTitulo = skin.getFont("title");
    BitmapFont fontSubtitulo = skin.getFont("subtitle");
    private Label scoreLabel;
    private TextButton backButton;
    private int score = Settings.GAME_INITIAL_SCORE;
    private boolean doubleLifeEnabledFireBat = false,tripleLifeEnabledFireBat = false,quintupleLifeEnabledFireBat = false;
    private boolean doubleLifeEnabledSlime = false,tripleLifeEnabledSlime = false,quintupleLifeEnabledSlime = false;
    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();

    public GameScreen(Videojoc game) {
        /*if (musicEnabled) {
            AssetManager.music.setVolume(musicVolume);
            AssetManager.music.play();
        }*/
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();

        scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setPosition(20, 900);

        background = new Background(AssetManager.fondo);
        knight = new Knight(Settings.KNIGHT_STARTX, Settings.KNIGHT_STARTY, Settings.KNIGHT_WIDTH, Settings.KNIGHT_HEIGHT);
        knight.setName("Knight");

        stage.addActor(background);
        stage.addActor(knight);
        stage.addActor(scoreLabel);

        firebatSpawner = new FireBatSpawner(knight, this);
        spawnFirebatTimer = 0f;
        spawnFirebat();

        slimeSpawner = new SlimeSpawner(knight,this);
        spawnSlimeTimer = 0f;
        spawnSlime();

        Gdx.input.setInputProcessor(stage);
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
        for (Slime slime : slimeSpawner.getSlimes()) {
            slime.drawBounds(shapeRenderer);
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
        slimeSpawner.update(delta);

        if (!knight.isDeath) {
            spawnFirebatTimer += delta;
            if (spawnFirebatTimer >= Settings.FIREBAT_SPAWNER) {
                spawnFirebat();
                spawnFirebatTimer = 0f;
            }

            spawnSlimeTimer += delta;
            if (spawnSlimeTimer >= Settings.SLIME_SPAWNER) {
                spawnSlime();
                spawnSlimeTimer = 0f;
            }
        } else {
            textLayoutOver = new GlyphLayout();
            textLayoutOver.setText(fontTitulo, "Game Over");
            textLayoutFinalScore = new GlyphLayout();
            textLayoutFinalScore.setText(fontSubtitulo, "Puntuacion Total: " + score);
            batch.begin();
            fontTitulo.draw(batch, textLayoutOver, (Settings.GAME_WIDTH - textLayoutOver.width) / 2, (float) (Settings.GAME_HEIGHT + 200) / 2);
            fontSubtitulo.draw(batch, textLayoutFinalScore, (Settings.GAME_WIDTH - textLayoutFinalScore.width) / 2, (float) (Settings.GAME_HEIGHT) / 2);
            batch.end();

            backButton = new TextButton("Volver al Menu", skin);
            backButton.setPosition((Settings.GAME_WIDTH - backButton.getWidth()) / 2, 100);
            stage.addActor(backButton);
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MainMenuScreen(game));
                }
            });

        }
        //drawElements();
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
    }

    private void spawnSlime() {
        float startX, startY;
        startX = MathUtils.randomBoolean() ? -50 : Settings.GAME_WIDTH + 50;
        startY = MathUtils.random(0, Settings.GAME_HEIGHT);

        Array<Slime> slimes = slimeSpawner.getSlimes();
        slimeSpawner.spawnSlime(startX, startY);
        for (Slime slime : slimes) {
            stage.addActor(slime);
        }
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
    public void onBatDeath(float x,float y) {
        if (score >= 0 && score < 100) {
            score += Settings.FIREBAT_SCORE;
        } else if (score >= 100 && score < 300) {
            if (!doubleLifeEnabledFireBat) {
                doubleLifeEnabledFireBat = true;
                firebatSpawner.setDoubleLifeEnabled();
                Gdx.app.log("Double Life", "La vida de los siguientes murciélagos se ha duplicado.");
            }
            score += Settings.FIREBAT_SCORE + Settings.FIREBAT_SCORE_INCREASE_LEVEL1;
        } else if (score >= 300 && score < 600) {
            if (!tripleLifeEnabledFireBat) {
                tripleLifeEnabledFireBat = true;
                firebatSpawner.setTripleLifeEnabled();
                Gdx.app.log("Triple Life", "La vida de los siguientes murciélagos se ha triplicado.");
            }
            score += Settings.FIREBAT_SCORE + Settings.FIREBAT_SCORE_INCREASE_LEVEL2;
        } else if (score >= 600) {
            if (!quintupleLifeEnabledFireBat) {
                quintupleLifeEnabledFireBat = true;
                firebatSpawner.setQuintupleLifeEnabled();
                Gdx.app.log("Quintuple Life", "La vida de los siguientes murciélagos se ha quintuplicado.");
            }
            score += Settings.FIREBAT_SCORE + Settings.FIREBAT_SCORE_INCREASE_LEVEL3;
        }
        updateScoreLabel();

        handleCoinDrop(x, y);
    }

    @Override
    public void onSlimeDeath(float x,float y) {
        if (score >= 0 && score < 100) {
            score += Settings.SLIME_SCORE;
        } else if (score >= 100 && score < 300) {
            if (!doubleLifeEnabledSlime) {
                doubleLifeEnabledSlime = true;
                slimeSpawner.setDoubleLifeEnabled();
                Gdx.app.log("Double Life", "La vida de los siguientes slimes se ha duplicado.");
            }
            score += Settings.SLIME_SCORE + Settings.SLIME_SCORE_INCREASE_LEVEL1;
        } else if (score >= 300 && score < 600) {
            if (!tripleLifeEnabledSlime) {
                tripleLifeEnabledSlime = true;
                slimeSpawner.setTripleLifeEnabled();
                Gdx.app.log("Triple Life", "La vida de los siguientes slimes se ha triplicado.");
            }
            score += Settings.SLIME_SCORE + Settings.SLIME_SCORE_INCREASE_LEVEL2;
        } else if (score >= 600) {
            if (!quintupleLifeEnabledSlime) {
                quintupleLifeEnabledSlime = true;
                slimeSpawner.setQuintupleLifeEnabled();
                Gdx.app.log("Quintuple Life", "La vida de los siguientes slimes se ha quintuplicado.");
            }
            score += Settings.SLIME_SCORE + Settings.SLIME_SCORE_INCREASE_LEVEL3;
        }
        updateScoreLabel();

        handleCoinDrop(x, y);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void handleCoinDrop(float x, float y) {
        float probability = 0.2f;
        float randomValue = MathUtils.random();
        if (randomValue <= probability) {
            spawnCoin(x, y);
        }
    }

    private void spawnCoin(float x, float y) {
        Coin coin = new Coin();
        coin.setPosition(x, y);
        System.out.println("Moneda spawneada");
        stage.addActor(coin);
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
