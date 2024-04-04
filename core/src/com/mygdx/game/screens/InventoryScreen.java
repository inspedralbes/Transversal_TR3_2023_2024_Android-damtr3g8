package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.Inventory;
import com.mygdx.game.objects.Item;

import javax.swing.Box;

public class InventoryScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Inventory inventory;
    private Videojoc game;
    private GameScreen gameScreen;

    public InventoryScreen(Videojoc game, Inventory inventory,GameScreen gameScreen) {
        this.game = game;
        this.inventory = inventory;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        gameScreen.pauseGame();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = AssetManager.skin;

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Window window = new Window("Inventory", skin); // Use window style from skin
        window.setSize(840, 380); // Set window size to 320x800 pixels
        table.add(window);

        Array<TextButton> boxes = new Array<>(); // Array to keep track of created boxes

        // Create and add boxes to the window
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 10; col++) {
                TextButton box = new TextButton("", skin); // Create box button
                box.setTouchable(Touchable.disabled);
                window.add(box).size(80, 80).pad(5); // Add box to the window with padding
                boxes.add(box); // Add the box to the tracking array
            }
            window.row(); // Move to the next row after completing a row
        }

        // Render inventory items
        for (Item item : inventory.getItems()) {
            boolean added = false;
            // Search for an available box
            for (TextButton box : boxes) {
                if (box.getChildren().size == 1) {
                    box.add(new Image(item.getIcon())).size(64, 64); // Add icon to the button
                    added = true;
                    break;
                }
            }
            if (!added) {
                Gdx.app.log("InventoryScreen", "No available box to add item: " + item.getName());
            }
        }


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.I) {
                    closeInventory();
                    return true;
                }
                return false;
            }
        });
    }

    private void closeInventory() {
        // Dispose the stage and set the game screen
        stage.dispose();
        game.setScreen(gameScreen);
        gameScreen.resumeGame();
    }
    @Override
    public void render(float delta) {
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

    }

}
