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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
                //box.setTouchable(Touchable.disabled);
                window.add(box).size(128, 128).pad(5); // Add box to the window with padding
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
                    Image icon = new Image(item.getIcon());
                    Label quantityLabel = new Label(String.valueOf(item.getQuantity()), skin); // Create label for item quantity
                    Table table1 = new Table(); // Create table to hold icon and quantity label
                    table1.add(icon).size(64, 64).padLeft(10); // Add icon to the table
                    table1.add(quantityLabel).padTop(40); // Add quantity label to the table with padding
                    box.add(table1).size(80, 80); // Add table to the button with padding
                    added = true;
                    break;
                }
            }
            if (!added) {
                Gdx.app.log("InventoryScreen", "No available box to add item: " + item.getName());
            }

            System.out.println(item.getName()+","+item.getDescription()+","+item.getQuantity());
        }

        Window descriptionWindow = new Window("Descripcion", skin);
        descriptionWindow.setSize(200, 200);
        table.row();
        table.add(descriptionWindow).padTop(20);


        // Add label for item description
        Label descriptionLabel = new Label("", skin);
        descriptionWindow.add(descriptionLabel).pad(10);

        // Listener to update description label when item button is hovered
        for (int i = 0; i < inventory.getItems().size(); i++) {
            Item item = inventory.getItems().get(i);
            TextButton box = boxes.get(i); // Obtener el botón de artículo correspondiente
            box.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (box.getChildren().size > 0) {
                        descriptionLabel.setText(item.getName()+", "+item.getDescription()+", cantidad: "+item.getQuantity());
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    descriptionLabel.setText("");
                }
            });
        }

        for (int i = 0; i < inventory.getItems().size(); i++) {
            Item item = inventory.getItems().get(i);
            TextButton box = boxes.get(i); // Get the corresponding item button
            box.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (box.getChildren().size > 0) {
                        openItemUsageWindow(item);
                    }
                }
            });
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

    // Method to open the item usage window
    private void openItemUsageWindow(Item item) {
        Window itemUsageWindow = new Window(item.getName() + " Usage", skin); // Create a new window for item usage
        itemUsageWindow.setSize(600, 400);
        itemUsageWindow.setPosition((Gdx.graphics.getWidth() - itemUsageWindow.getWidth()) / 2, (Gdx.graphics.getHeight() - itemUsageWindow.getHeight()) / 2);

        // Add buttons for item usage options
        TextButton useButton = new TextButton("Use", skin);
        TextButton dropButton = new TextButton("Drop", skin);

        // Add listeners to the buttons
        useButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Implement item usage logic here
                // For example, you can call a method in the GameScreen to use the item
                gameScreen.useItem(item);
                item.decreaseQuantity(1); // Decrease the quantity of the item after use
                if(item.getQuantity()<1){
                    inventory.removeItem(item);
                }
                closeItemUsageWindow(itemUsageWindow); // Close the item usage window
            }
        });

        Slider dropSlider = new Slider(0, item.getQuantity(), 1, false, skin);
        Label dropLabel = new Label("Number to drop: ", skin);
        Label dropValueLabel = new Label("", skin);
        dropValueLabel.setText(String.valueOf((int)dropSlider.getValue()));

        // Listener to update the label text when slider value changes
        dropSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dropValueLabel.setText(String.valueOf((int)dropSlider.getValue()));
            }
        });

        dropButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Implement item drop logic here
                // For example, you can remove the item from the inventory and update the UI
                int dropValue = Integer.parseInt(dropValueLabel.getText().toString());
                item.decreaseQuantity(dropValue);
                if(item.getQuantity()<1){
                    inventory.removeItem(item);
                }
                closeItemUsageWindow(itemUsageWindow); // Close the item usage window
            }
        });


        // Add buttons to the window
        itemUsageWindow.add(useButton).pad(10).row();
        itemUsageWindow.add(dropButton).pad(10).row();

        // Add slider and label to the window
        itemUsageWindow.add(dropLabel);
        itemUsageWindow.add(dropValueLabel).padRight(10).row();
        itemUsageWindow.add(dropSlider).padBottom(20).row();

        // Add the item usage window to the stage
        stage.addActor(itemUsageWindow);
    }

    // Method to close the item usage window
    private void closeItemUsageWindow(Window itemUsageWindow) {
        itemUsageWindow.remove(); // Remove the window from the stage
        closeInventory();
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
