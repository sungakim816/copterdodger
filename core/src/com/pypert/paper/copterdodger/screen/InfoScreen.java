package com.pypert.paper.copterdodger.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.pypert.paper.copterdodger.CopterDodger;
import com.pypert.paper.copterdodger.actor.Ground;
import com.pypert.paper.copterdodger.actor.Sky;
import com.pypert.paper.copterdodger.base.BaseActor;
import com.pypert.paper.copterdodger.base.BaseGame;
import com.pypert.paper.copterdodger.base.BaseScreen;

public class InfoScreen extends BaseScreen {
    private Button returnButton;
    private Button.ButtonStyle returnButtonStyle;
    private TextureRegion returnButtonRegion;
    private final String instruction = "Copter Dodger is an endless side-scrolling action game in which the player controls a helicopter " +
            "that moves up and down with the goal of dodging enemy helicopters or airplanes that fly across the screen and, whenever possible, " +
            "collecting stars and other items that appear. If the player’s helicopter collides with an enemy, the game is over. " +
            "Dodging enemies and collecting items both award points and helps the player to survive the game." +
            "The longer the player survives, the higher their score will be. ";
    private final String message = "Powered By Libgdx.\nDeveloper: K. Sunga, Dr. Arvin Dela Cruz";
    private BaseActor animatedLogo;
    private Label title;

    InfoScreen() {
        super();
    }


    @Override
    public void initialize() {
        animatedLogo = new BaseActor(0, 0, uiStage);
        animatedLogo.loadAnimationFromFiles(CopterDodger.COPTER_SKIN, 0.03f, true);
        title = new Label("Copter Dodger", BaseGame.GamePlayedStyle);
        new Sky(0, 0, mainStage);
        new Sky(CopterDodger.GAME_WORLD_WIDTH, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(CopterDodger.GROUND_WIDTH, 0, mainStage);
        returnButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("return.png")));
        returnButtonStyle = new ButtonStyle();
        returnButtonStyle.up = new TextureRegionDrawable(returnButtonRegion);
        returnButton = new Button(returnButtonStyle);
        returnButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                CopterDodger.setActiveScreen(new MenuScreen());
                return true;
            }
        });
        Label instructionLabel = new Label(instruction, BaseGame.MavenProRegular);
        Label poweredBy = new Label(message, BaseGame.GasolineLabelStyle);
        instructionLabel.setAlignment(Align.left);
        instructionLabel.setWrap(true);
        uiTable.row().pad(10).top().left().expandY();
        uiTable.add(returnButton);
        uiTable.row();
        uiTable.add(animatedLogo).expandX();
        uiTable.row();
        uiTable.add(title).expandX();
        uiTable.row().expand();
        uiTable.add(instructionLabel).padRight(50).padLeft(50).width(Gdx.graphics.getWidth() - 100).padBottom(100).expandY();
        uiTable.row();
        uiTable.add(poweredBy).left().pad(20);
        CopterDodger.BACKGROUND_MUSIC.setVolume(CopterDodger.AUDIO_VOLUME);
        CopterDodger.BACKGROUND_MUSIC.play();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        super.dispose();
        animatedLogo.remove();
    }
}
