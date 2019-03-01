package com.pypert.paper.copterdodger.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.pypert.paper.copterdodger.CopterDodger;
import com.pypert.paper.copterdodger.actor.Ground;
import com.pypert.paper.copterdodger.actor.Sky;
import com.pypert.paper.copterdodger.base.BaseActor;
import com.pypert.paper.copterdodger.base.BaseGame;
import com.pypert.paper.copterdodger.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private BaseActor animatedLogo;
    private Label title, highScoreLabel;
    private Button playButton;
    private Button exitButton;
    private Button soundButton;
    private Button infoButton;
    private ButtonStyle playButtonStyle, exitButtonStyle, soundButtonStyle, infoButtonStyle;
    private TextureRegion playButtonRegion, exitButtonRegion, soundOnButtonRegion, soundOffButtonRegion, infoButtonRegion;

    public MenuScreen() {
        super();
    }

    public void initialize() {
        animatedLogo = new BaseActor(0, 0, uiStage);
        animatedLogo.loadAnimationFromFiles(CopterDodger.COPTER_SKIN, 0.03f, true);
        playButtonStyle = new ButtonStyle();
        exitButtonStyle = new ButtonStyle();
        soundButtonStyle = new ButtonStyle();
        infoButtonStyle = new ButtonStyle();
        exitButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("exit_red_small.png")));
        playButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("play_button_green_big.png")));
        soundOnButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("soundon.png")));
        soundOffButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("soundoff.png")));
        infoButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("info.png")));
        TextureRegionDrawable soundOff = new TextureRegionDrawable(soundOffButtonRegion);
        TextureRegionDrawable soundOn = new TextureRegionDrawable(soundOnButtonRegion);
        playButtonStyle.up = new TextureRegionDrawable(playButtonRegion);
        exitButtonStyle.up = new TextureRegionDrawable(exitButtonRegion);
        infoButtonStyle.up = new TextureRegionDrawable(infoButtonRegion);
        if (CopterDodger.AUDIO_VOLUME == 1)
            soundButtonStyle.up = soundOn;
        else {
            soundButtonStyle.up = soundOff;
        }
        playButton = new Button(playButtonStyle);
        exitButton = new Button(exitButtonStyle);
        soundButton = new Button(soundButtonStyle);
        infoButton = new Button(infoButtonStyle);
        new Sky(0, 0, mainStage);
        new Sky(CopterDodger.GAME_WORLD_WIDTH, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(CopterDodger.GROUND_WIDTH, 0, mainStage);
        title = new Label("Copter Dodger", BaseGame.GamePlayedStyle);
        highScoreLabel = new Label(String.valueOf(CopterDodger.HIGH_SCORE), BaseGame.GamePlayedStyle);
        uiTable.add(animatedLogo).colspan(3).pad(5);
        uiTable.row();
        uiTable.add(title).colspan(3).expandX();
        uiTable.row();
        uiTable.add(highScoreLabel).colspan(3).expandX();
        uiTable.row();
        uiTable.add(playButton).padTop(10).colspan(3).expandX();
        uiTable.row().pad(20);
        uiTable.add(soundButton).right();
        uiTable.add(infoButton).center();
        uiTable.add(exitButton).left();

        playButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        CopterDodger.setActiveScreen(new LevelScreen());
                        return true;
                    }
                }
        );
        infoButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                CopterDodger.setActiveScreen(new InfoScreen());
                return true;
            }
        });
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        soundButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                CopterDodger.AUDIO_VOLUME = 1 - CopterDodger.AUDIO_VOLUME;
                CopterDodger.COPTER_PREFS.putInteger(CopterDodger.AUDIO_VOLUME_KEY, CopterDodger.AUDIO_VOLUME);
                CopterDodger.COPTER_PREFS.flush();
                soundButton.getStyle().up = (CopterDodger.AUDIO_VOLUME == 0) ? soundOff : soundOn;
                CopterDodger.BACKGROUND_MUSIC.setVolume(CopterDodger.AUDIO_VOLUME);
                return true;
            }
        });
        CopterDodger.BACKGROUND_MUSIC.setVolume(CopterDodger.AUDIO_VOLUME);
        CopterDodger.BACKGROUND_MUSIC.play();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
