package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.Inventory;
import com.mygdx.game.objects.Item;
import com.mygdx.game.utils.AppPreferences;
import com.mygdx.game.utils.Settings;

public class ShopScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Videojoc game;
    Inventory inventory;
    private Table objectsTable;
    int costFullRedPotion = 50;
    int fullredpotions,halfredpotions,quarterredpotions,purplepotions,greenpotions,yellowpotions;
    private AppPreferences preferences = new AppPreferences();

    public ShopScreen(Videojoc game, Inventory inventory) {
        this.game = game;
        this.inventory = inventory;
        fullredpotions = preferences.getFullPotionsCollected();
        halfredpotions = preferences.getHalfPotionsCollected();
        quarterredpotions = preferences.getQuarterPotionsCollected();
        purplepotions = preferences.getForcePotionsCollected();
        greenpotions = preferences.getEndurancePotionsCollected();
        yellowpotions = preferences.getVelocityPotionsCollected();
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = AssetManager.skin;

        // Crear la tabla para la compra de objetos
        objectsTable = new Table();
        objectsTable.top().padTop(20).center();

        TextButton exitButton = new TextButton("Salir", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Cambiar a la pantalla del menú principal
                game.setScreen(new MainMenuScreen(game));
            }
        });
        objectsTable.add(exitButton).left().padBottom(20);
        Label titleLabel = new Label("Shop", skin);
        objectsTable.add(titleLabel).left().padBottom(20).row();

        // Crear el ScrollPane con la tabla de objetos
        ScrollPane scrollPane = new ScrollPane(objectsTable, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);
        objectsTable.add(createObjectWindow("Pocion de cura lleno",Settings.COSTFULLREDPOTION,AssetManager.fullredpotiontexture,fullredpotions)).size(520, 270).padRight(30);
        objectsTable.add(createObjectWindow("Pocion de cura medio",Settings.COSTHALFREDPOTION,AssetManager.halfredpotiontexture,halfredpotions)).size(520, 270).padRight(30).row();
        objectsTable.add(createObjectWindow("Pocion de cura cuarto",Settings.COSTQUARTERREDPOTION,AssetManager.quarterredpotiontexture,quarterredpotions)).size(520, 270).padRight(30);
        objectsTable.add(createObjectWindow("Pocion de fuerza",Settings.COSTPURPLEPOTION,AssetManager.purplepotiontexture,purplepotions)).size(520, 270).padRight(30).row();
        objectsTable.add(createObjectWindow("Pocion de resistencia",Settings.COSTGREENPOTION,AssetManager.greenpotiontexture,greenpotions)).size(520, 270).padRight(30);
        objectsTable.add(createObjectWindow("Pocion de velocidad",Settings.COSTYELLOWPOTION,AssetManager.yellowpotiontexture,yellowpotions)).size(520, 270).padRight(30).row();
        stage.addActor(scrollPane);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }


    public Window createObjectWindow(String objectName, int costObject, Texture imageObject,int itemCount) {
        Window window = new Window("", skin);
        window.pad(10);

        // Añadir el nombre del objeto
        Label nameLabel = new Label(objectName, skin);
        window.add(nameLabel).colspan(3).center().padBottom(10);

        // Añadir imagen
        Image image = new Image(imageObject);
        window.add(image).pad(10).size(80,80).colspan(3).center().row();

        // Añadir cantidad con botones de incremento y decremento
        TextButton minusButton = new TextButton("-", skin);
        TextButton plusButton = new TextButton("+", skin);
        Label quantityLabel = new Label("1", skin);

        window.add(minusButton).left();
        window.add(quantityLabel).pad(10).center();
        window.add(plusButton).right().padRight(30);

        TextButton buyButton = new TextButton("Buy\n" +costObject+ " coins", skin);
        buyButton.pad(10).center();
        window.add(buyButton).colspan(3).center();

        minusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Disminuir la cantidad
                int quantity = Integer.parseInt(quantityLabel.getText().toString());
                if (quantity > 1) {
                    quantityLabel.setText(String.valueOf(quantity - 1));
                    updateCost(quantity-1, costObject, buyButton);
                }
            }
        });

        plusButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Aumentar la cantidad
                int quantity = Integer.parseInt(quantityLabel.getText().toString());
                quantityLabel.setText(String.valueOf(quantity + 1));
                updateCost(quantity+1, costObject, buyButton);
            }
        });

        buyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int currentCoins = preferences.getCoinsCollected();
                int totalCost = costObject * Integer.parseInt(quantityLabel.getText().toString());
                if (currentCoins >= totalCost) {
                    currentCoins -= totalCost;
                    preferences.setCoinsCollected(currentCoins);
                    // Aquí puedes agregar cualquier otra lógica relacionada con la compra,
                    // como agregar el objeto comprado al inventario del jugador, etc.
                    inventory.addItem(new Item(objectName,Integer.parseInt(quantityLabel.getText().toString())));
                    int newCount = itemCount+Integer.parseInt(quantityLabel.getText().toString());
                    preferences.setItemCount(objectName, newCount);
                    return true;
                } else {
                    System.out.println("No tienes suficientes monedas para comprar este objeto.");
                    return false;
                }
            }
        });

        return window;
    }

    public void updateCost(int quantity, int itemCost, TextButton buyButton) {
        int totalCost = itemCost * quantity;
        buyButton.setText("Buy\n" + totalCost + " coins");
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
