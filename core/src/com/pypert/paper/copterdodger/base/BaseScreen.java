package com.pypert.paper.copterdodger.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pypert.paper.copterdodger.CopterDodger;

public abstract class BaseScreen implements Screen, InputProcessor {
    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;
    private Color backgroundColor;

    public BaseScreen() {
        backgroundColor = Color.BLACK;
        mainStage = new Stage(new FitViewport(CopterDodger.GAME_WORLD_WIDTH, CopterDodger.GAME_WORLD_HEIGHT));
        uiStage = new Stage(new FitViewport(CopterDodger.GAME_WORLD_WIDTH, CopterDodger.GAME_WORLD_HEIGHT));
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        initialize();
    }

    public abstract void initialize();

    public abstract void update(float dt);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    public void render(float dt) {
        // limit amount of time that can pass while window is being dragged
        dt = Math.min(dt, 1 / 30f);

        // act methods
        uiStage.act(dt);
        mainStage.act(dt);

        // defined by user
        update(dt);

        // clear the screen
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw the graphics
        mainStage.draw();
        uiStage.draw();
    }

    // methods required by Screen interface
    @Override
    public void resize(int width, int height) {
        this.uiStage.getViewport().update(width, height);
        this.mainStage.getViewport().update(width, height);
        this.mainStage.getCamera().update();
        this.uiStage.getCamera().update();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        for (Actor actors : mainStage.getActors()) {
            actors.remove();
            actors.clear();
        }
        mainStage.dispose();
        for (Actor actors : uiStage.getActors()) {
            actors.remove();
            actors.clear();
        }
        uiStage.dispose();
    }

    /**
     * Called when this becomes the active screen in a Game.
     * Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    public void show() {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }

    /**
     * Called when this is no longer the active screen in a Game.
     * Screen class and Stages no longer process input.
     * Other InputProcessors must be removed manually.
     */
    public void hide() {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }

    /**
     * Useful for checking for touch-down events.
     */
    public boolean isTouchDownEvent(Event e) {
        return (e instanceof InputEvent) && ((InputEvent) e).getType().equals(Type.touchDown);
    }

    // methods required by InputProcessor interface
    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char c) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}