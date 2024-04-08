package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Videojoc;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Settings;

public class ShopScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Videojoc game;
    private Table skillsTable;
    private Table objectsTable;
    private Table upgradesTable;

    public ShopScreen(Videojoc game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = AssetManager.skin;

        skillsTable = new Table();
        skillsTable.setFillParent(true);
        skillsTable.top().padTop(20);
        Label titleLabel1 = new Label("Buy Skills", skin);
        TextButton skillsButton1 = new TextButton("Buy Skills", skin);
        TextButton objectsButton1 = new TextButton("Buy Objects", skin);
        TextButton upgradesButton1 = new TextButton("Buy Upgrades", skin);
        skillsTable.add(titleLabel1).colspan(3).center().padBottom(20).row();
        skillsTable.add(skillsButton1).pad(10);
        skillsTable.add(objectsButton1).pad(10);
        skillsTable.add(upgradesButton1).pad(10).row();
        skillsButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(true);
                objectsTable.setVisible(false);
                upgradesTable.setVisible(false);
            }
        });
        objectsButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(false);
                objectsTable.setVisible(true);
                upgradesTable.setVisible(false);
            }
        });
        upgradesButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(false);
                objectsTable.setVisible(false);
                upgradesTable.setVisible(true);
            }
        });

        objectsTable = new Table();
        objectsTable.setFillParent(true);
        objectsTable.top().padTop(20);
        Label titleLabel2 = new Label("Buy Objects", skin);
        TextButton skillsButton2 = new TextButton("Buy Skills", skin);
        TextButton objectsButton2 = new TextButton("Buy Objects", skin);
        TextButton upgradesButton2 = new TextButton("Buy Upgrades", skin);
        objectsTable.add(titleLabel2).colspan(3).center().padBottom(20).row();
        objectsTable.add(skillsButton2).pad(10);
        objectsTable.add(objectsButton2).pad(10);
        objectsTable.add(upgradesButton2).pad(10).row();
        skillsButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(true);
                objectsTable.setVisible(false);
                upgradesTable.setVisible(false);
            }
        });
        objectsButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(false);
                objectsTable.setVisible(true);
                upgradesTable.setVisible(false);
            }
        });
        upgradesButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(false);
                objectsTable.setVisible(false);
                upgradesTable.setVisible(true);
            }
        });


        upgradesTable = new Table();
        upgradesTable.setFillParent(true);
        upgradesTable.top().padTop(20);
        Label titleLabel3 = new Label("Buy Upgrades", skin);
        TextButton skillsButton3 = new TextButton("Buy Skills", skin);
        TextButton objectsButton3 = new TextButton("Buy Objects", skin);
        TextButton upgradesButton3 = new TextButton("Buy Upgrades", skin);
        upgradesTable.add(titleLabel3).colspan(3).center().padBottom(20).row();
        upgradesTable.add(skillsButton3).pad(10);
        upgradesTable.add(objectsButton3).pad(10);
        upgradesTable.add(upgradesButton3).pad(10).row();
        skillsButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(true);
                objectsTable.setVisible(false);
                upgradesTable.setVisible(false);
            }
        });
        objectsButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(false);
                objectsTable.setVisible(true);
                upgradesTable.setVisible(false);
            }
        });
        upgradesButton3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skillsTable.setVisible(false);
                objectsTable.setVisible(false);
                upgradesTable.setVisible(true);
            }
        });

        // Set the initial visibility of the tables
        skillsTable.setVisible(true);
        objectsTable.setVisible(false);
        upgradesTable.setVisible(false);

        stage.addActor(skillsTable);
        stage.addActor(objectsTable);
        stage.addActor(upgradesTable);
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
