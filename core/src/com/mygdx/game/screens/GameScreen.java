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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.buttons.ButtonAttack;
import com.mygdx.game.buttons.ButtonOpenInventary;
import com.mygdx.game.buttons.GamepadDown;
import com.mygdx.game.buttons.GamepadLeft;
import com.mygdx.game.buttons.GamepadRight;
import com.mygdx.game.buttons.GamepadUp;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.helpers.BatDeathListener;
import com.mygdx.game.helpers.InputHandler;
import com.mygdx.game.objects.Background;
import com.mygdx.game.objects.Coin;
import com.mygdx.game.objects.FireBat;
import com.mygdx.game.objects.FireBatSpawner;
import com.mygdx.game.objects.Inventory;
import com.mygdx.game.objects.Item;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.objects.RedPotion;
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
    private InputHandler inputHandler;
    private GlyphLayout textLayoutOver, textLayoutFinalScore;
    private Knight knight;
    private FireBatSpawner firebatSpawner;
    private SlimeSpawner slimeSpawner;
    private Inventory inventory;
    private ButtonAttack attackButton;
    private ButtonOpenInventary buttonOpenInventary;
    private GamepadDown gamepadDown;
    private GamepadLeft gamepadLeft;
    private GamepadRight gamepadRight;
    private GamepadUp gamepadUp;
    private float spawnFirebatTimer, spawnSlimeTimer;
    private Background background;
    private Skin skin = AssetManager.skin;
    BitmapFont fontTitulo = skin.getFont("title");
    BitmapFont fontSubtitulo = skin.getFont("subtitle");
    private Label scoreLabel;
    private TextButton backButton;
    private int score = Settings.GAME_INITIAL_SCORE;
    private boolean doubleLifeEnabledFireBat = false, tripleLifeEnabledFireBat = false, quintupleLifeEnabledFireBat = false;
    private boolean doubleLifeEnabledSlime = false, tripleLifeEnabledSlime = false, quintupleLifeEnabledSlime = false;
    AppPreferences preferences = new AppPreferences();
    boolean musicEnabled = preferences.isMusicEnabled();
    float musicVolume = preferences.getMusicVolume();

    public GameScreen(Videojoc game, Inventory inventory) {
        if (musicEnabled) {
            AssetManager.music.setVolume(musicVolume);
            AssetManager.music.play();
        }
        this.game = game;
        this.inventory = inventory;

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();

        scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setPosition(20, 900);

        background = new Background(AssetManager.fondo);
        knight = new Knight(Settings.KNIGHT_STARTX, Settings.KNIGHT_STARTY, Settings.KNIGHT_WIDTH, Settings.KNIGHT_HEIGHT, inventory);
        knight.setName("Knight");

        attackButton = new ButtonAttack(AssetManager.attackButtonnormal);
        buttonOpenInventary = new ButtonOpenInventary(AssetManager.openButtonnormal);
        gamepadDown = new GamepadDown(AssetManager.downButtonnormal);
        gamepadLeft = new GamepadLeft(AssetManager.leftButtonnormal);
        gamepadRight = new GamepadRight(AssetManager.rightButtonnormal);
        gamepadUp = new GamepadUp(AssetManager.upButtonnormal);
        attackButton.setBounds(Settings.BUTTON_ATTACKX, Settings.BUTTON_ATTACKY, Settings.BUTTON_ATTACK_WIDTH, Settings.BUTTON_ATTACK_HEIGHT);
        buttonOpenInventary.setBounds(Settings.BUTTON_OPENX, Settings.BUTTON_OPENY, Settings.BUTTON_OPEN_WIDTH, Settings.BUTTON_OPEN_HEIGHT);
        gamepadDown.setBounds(Settings.BUTTON_DOWNX,Settings.BUTTON_DOWNY,Settings.BUTTON_DOWN_WIDTH,Settings.BUTTON_DOWN_HEIGHT);
        gamepadLeft.setBounds(Settings.BUTTON_LEFTX,Settings.BUTTON_LEFTY,Settings.BUTTON_LEFT_WIDTH,Settings.BUTTON_LEFT_HEIGHT);
        gamepadRight.setBounds(Settings.BUTTON_RIGHTX,Settings.BUTTON_RIGHTY,Settings.BUTTON_RIGHT_WIDTH,Settings.BUTTON_RIGHT_HEIGHT);
        gamepadUp.setBounds(Settings.BUTTON_UPX,Settings.BUTTON_UPY,Settings.BUTTON_UP_WIDTH,Settings.BUTTON_UP_HEIGHT);

        stage.addActor(background);
        stage.addActor(knight);
        stage.addActor(scoreLabel);
        stage.addActor(attackButton);
        stage.addActor(buttonOpenInventary);
        stage.addActor(gamepadDown);
        stage.addActor(gamepadLeft);
        stage.addActor(gamepadRight);
        stage.addActor(gamepadUp);

        firebatSpawner = new FireBatSpawner(knight, this);
        spawnFirebatTimer = 0f;
        spawnFirebat();

        slimeSpawner = new SlimeSpawner(knight, this);
        spawnSlimeTimer = 0f;
        spawnSlime();

        Item coinsItem = new Item("Moneda", "Objeto para comprar cosas de la tienda", AssetManager.cointexture, getMonedas());
        Item curaLLena = new Item("Pocion de cura lleno", "Cura 100 de vida", AssetManager.fullredpotiontexture, getFullRedPotions());
        Item curaMedia = new Item("Pocion de cura medio", "Cura 50 de vida", AssetManager.halfredpotiontexture, getHalfRedPotions());
        Item curaCuarto = new Item("Pocion de cura cuarto", "Cura 25 de vida", AssetManager.quarterredpotiontexture, getQuarterRedPotions());
        Item fuerza = new Item("Pocion de fuerza", "Augmenta un 80% la fuerza durante 5 segundos", AssetManager.purplepotiontexture, getPurplePotions());
        Item resistencia = new Item("Pocion de resistencia", "Augmenta un 65% la resistencia durante 10 segundos", AssetManager.greenpotiontexture, getGreenPotions());
        Item velocidad = new Item("Pocion de velocidad", "Augmenta un 50% la velocidad durante 13 segundos", AssetManager.yellowpotiontexture, getYellowPotions());
        inventory.addItem(coinsItem);
        inventory.addItem(curaLLena);
        inventory.addItem(curaMedia);
        inventory.addItem(curaCuarto);
        inventory.addItem(fuerza);
        inventory.addItem(resistencia);
        inventory.addItem(velocidad);

        //Gdx.input.setInputProcessor(stage);
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

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
            float adjustedSpawnTimeFirebat = calculateAdjustedSpawnTimeFirebat(score);
            spawnFirebatTimer += delta;
            if (spawnFirebatTimer >= adjustedSpawnTimeFirebat) {
                spawnFirebat();
                spawnFirebatTimer = 0f;
            }
            float adjustedSpawnTimeSlime = calculateAdjustedSpawnTimeSlime(score);
            spawnSlimeTimer += delta;
            if (spawnSlimeTimer >= adjustedSpawnTimeSlime) {
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

    private float calculateAdjustedSpawnTimeFirebat(int score) {
        // Calcula el tiempo de spawn ajustado en función de la puntuación
        float adjustedSpawnTime = Settings.FIREBAT_SPAWNER; // Tiempo de spawn predeterminado

        // Ajusta el tiempo de spawn en función de la puntuación
        if (score >= 0 && score < 100) {
            adjustedSpawnTime -= 0f; // Ejemplo de reducción lineal del tiempo de spawn
        } else if (score >= 100 && score < 300) {
            adjustedSpawnTime -= 1f;
        } else if (score >= 300 && score < 600) {
            adjustedSpawnTime -= 2f;
        } else if (score >= 600) {
            adjustedSpawnTime -= 3.5f;
        }

        // Asegura que el tiempo de spawn ajustado no sea menor que un valor mínimo
        adjustedSpawnTime = Math.max(adjustedSpawnTime, 1f);

        return adjustedSpawnTime;
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

    private float calculateAdjustedSpawnTimeSlime(int score) {
        // Calcula el tiempo de spawn ajustado en función de la puntuación
        float adjustedSpawnTime = Settings.SLIME_SPAWNER; // Tiempo de spawn predeterminado

        // Ajusta el tiempo de spawn en función de la puntuación
        if (score >= 0 && score < 100) {
            adjustedSpawnTime -= 0f; // Ejemplo de reducción lineal del tiempo de spawn
        } else if (score >= 100 && score < 300) {
            adjustedSpawnTime -= 0.5f;
        } else if (score >= 300 && score < 600) {
            adjustedSpawnTime -= 1f;
        } else if (score >= 600) {
            adjustedSpawnTime -= 1.75f;
        }

        // Asegura que el tiempo de spawn ajustado no sea menor que un valor mínimo
        adjustedSpawnTime = Math.max(adjustedSpawnTime, 1f);

        return adjustedSpawnTime;
    }


    private void handleInput(float delta) {
        if (inputHandler.isButtonLeftPressed) {
            knight.moverIzquierda(delta);
        } else if (inputHandler.isButtonrightPressed) {
            knight.moverDerecha(delta);
        } else if (inputHandler.isButtonupPressed) {
            knight.moverArriba(delta);
        } else if (inputHandler.isButtonDownPressed) {
            knight.moverAbajo(delta);
        }else {
            knight.pararMovimiento();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            knight.attack();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            openInventary();
        }
    }

    @Override
    public void onBatDeath(float x, float y) {
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
        handleRedPotionDrop(x, y);
    }

    @Override
    public void onSlimeDeath(float x, float y) {
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
        handleRedPotionDrop(x, y);
    }


    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void handleCoinDrop(float x, float y) {
        float probability = Settings.COIN_SPAWN;
        float randomValue = MathUtils.random();
        if (randomValue <= probability) {
            spawnCoin(x, y);
        }
    }

    private void spawnCoin(float x, float y) {
        Coin coin = new Coin();
        coin.setPosition(x, y);
        stage.addActor(coin);
    }

    private void handleRedPotionDrop(float x, float y) {
        float probability = Settings.FULLPOTION_SPAWN;
        float randomValue = MathUtils.random();
        if (randomValue <= probability) {
            spawnPotion(x, y);
        }
    }

    private void spawnPotion(float x, float y) {
        RedPotion potion = new RedPotion();
        potion.setPosition(x, y);
        stage.addActor(potion);
    }

    public void openInventary() {
        game.setScreen(new InventoryScreen(game, inventory, this));
    }

    public void useItem(Item item) {
        if (item.getName().equals("Pocion de cura lleno")) {
            int maxHealth = knight.getMaxHealth();
            int currentHealth = knight.getHealth();
            int newHealth = currentHealth + Settings.FULLPOTION_HEALTH;
            if (newHealth > maxHealth) {
                newHealth = maxHealth;
            }
            knight.setHealth(newHealth);
            preferences.decreaseFullPotionsCollected();
        } else if (item.getName().equals("Pocion de cura medio")) {
            int maxHealth = knight.getMaxHealth();
            int currentHealth = knight.getHealth();
            int newHealth = currentHealth + Settings.HALFPOTION_HEALTH;
            if (newHealth > maxHealth) {
                newHealth = maxHealth;
            }
            knight.setHealth(newHealth);
            preferences.decreaseHalfPotionsCollected();
        } else if (item.getName().equals("Pocion de cura cuarto")) {
            int maxHealth = knight.getMaxHealth();
            int currentHealth = knight.getHealth();
            int newHealth = currentHealth + Settings.QUARTERPOTION_HEALTH;
            if (newHealth > maxHealth) {
                newHealth = maxHealth;
            }
            knight.setHealth(newHealth);
            preferences.decreaseQuarterPotionsCollected();
        } else if (item.getName().equals("Pocion de velocidad")) {
            knight.applySpeedBoost(13f);
            preferences.decreaseVelocityPotionsCollected();
        } else if (item.getName().equals("Pocion de resistencia")) {
            knight.applyResistance(10f);
            preferences.decreaseEndurancePotionsCollected();
        } else if (item.getName().equals("Pocion de fuerza")) {
            firebatSpawner.applyAttack(5f);
            slimeSpawner.applyAttack(5f);
            preferences.decreaseForcePotionsCollected();
        }
    }

    public void pauseGame() {
        Gdx.input.setInputProcessor(null);
    }

    public void resumeGame() {
        Gdx.input.setInputProcessor(inputHandler);
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

    public int getMonedas() {
        return preferences.getCoinsCollected();
    }

    public int getFullRedPotions() {
        return preferences.getFullPotionsCollected();
    }

    public int getHalfRedPotions() {
        return preferences.getHalfPotionsCollected();
    }

    public int getQuarterRedPotions() {
        return preferences.getQuarterPotionsCollected();
    }

    public int getPurplePotions() {
        return preferences.getForcePotionsCollected();
    }

    public int getGreenPotions() {
        return preferences.getEndurancePotionsCollected();
    }

    public int getYellowPotions() {
        return preferences.getVelocityPotionsCollected();
    }

    public Knight getKnight() {
        return knight;
    }

    public Stage getStage() {
        return stage;
    }

    public ButtonAttack getAttackButton() {
        return attackButton;
    }

    public Rectangle getAttackButtonBounds() {
        return new Rectangle(attackButton.getX(), attackButton.getY(), attackButton.getWidth(), attackButton.getHeight());
    }

    public ButtonOpenInventary getButtonOpenInventary() {
        return buttonOpenInventary;
    }

    public Rectangle getButtonOpenInventaryBounds() {
        return new Rectangle(buttonOpenInventary.getX(), buttonOpenInventary.getY(), buttonOpenInventary.getWidth(), buttonOpenInventary.getHeight());
    }

    public GamepadDown getGamepadDown() {
        return gamepadDown;
    }

    public Rectangle getGamepadDownBounds() {
        return new Rectangle(gamepadDown.getX(), gamepadDown.getY(), gamepadDown.getWidth(), gamepadDown.getHeight());
    }

    public GamepadLeft getGamepadLeft() {
        return gamepadLeft;
    }

    public Rectangle getGamepadLeftBounds() {
        return new Rectangle(gamepadLeft.getX(), gamepadLeft.getY(), gamepadLeft.getWidth(), gamepadLeft.getHeight());
    }

    public GamepadRight getGamepadRight() {
        return gamepadRight;
    }

    public Rectangle getGamepadRightBounds() {
        return new Rectangle(gamepadRight.getX(), gamepadRight.getY(), gamepadRight.getWidth(), gamepadRight.getHeight());
    }

    public GamepadUp getGamepadUp() {
        return gamepadUp;
    }

    public Rectangle getGamepadUpBounds() {
        return new Rectangle(gamepadUp.getX(), gamepadUp.getY(), gamepadUp.getWidth(), gamepadUp.getHeight());
    }
}
