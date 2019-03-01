package com.pypert.paper.copterdodger.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Created when program is launched;
 * manages the screens that appear during the game.
 */
public abstract class BaseGame extends Game {
    /**
     * Stores reference to game; used when calling setActiveScreen method.
     */
    private static BaseGame game;
    public static LabelStyle labelStyle;
    public static LabelStyle GamePlayedStyle;
    public static LabelStyle KGNeatlyPrintedSpacedStyle;
    public static LabelStyle BreeSerifStyle;
    public static LabelStyle MavenProRegular;
    public static LabelStyle GasolineLabelStyle;
    public static TextButtonStyle textButtonStyle;

    /**
     * Called when game is initialized; stores global reference to game object.
     */
    public BaseGame() {
        game = this;
    }

    /**
     * Called when game is initialized,
     * after Gdx.input and other objects have been initialized.
     */
    public void create() {
        // prepare for multiple classes/stages/actors to receive discrete input
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
        labelStyle = new LabelStyle();
        setLabelStyle();
        GamePlayedStyle = new LabelStyle();
        setGamePlayedStyle();
        GasolineLabelStyle = new LabelStyle();
        setGasolineLabelStyle();
        KGNeatlyPrintedSpacedStyle = new LabelStyle();
        setKGNeatlyPrintedSpaced();
        BreeSerifStyle = new LabelStyle();
        setBreeSerifStyle();
        MavenProRegular = new LabelStyle();
        setMavenProRegular();
        // parameters for generating a custom bitmap font
        textButtonStyle = new TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));
        NinePatch buttonPatch = new NinePatch(buttonTex, 24, 24, 24, 24);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);
        textButtonStyle.font = labelStyle.font;
        textButtonStyle.fontColor = Color.GRAY;
    }

    private void setLabelStyle() {
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("gameplayed.otf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 40;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2f;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        BitmapFont customFont = fontGenerator.generateFont(fontParameters);
        labelStyle.font = customFont;
    }

    private void setKGNeatlyPrintedSpaced() {
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("KGNeatlyPrintedSpaced.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 36;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 1f;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        KGNeatlyPrintedSpacedStyle.font = fontGenerator.generateFont(fontParameters);
    }

    private void setBreeSerifStyle() {
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("BreeSerif-Regular.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 32;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 1f;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        BreeSerifStyle.font = fontGenerator.generateFont(fontParameters);
    }

    private void setMavenProRegular() {
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("MavenPro-Regular.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 32;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 1f;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        MavenProRegular.font = fontGenerator.generateFont(fontParameters);
    }

    private void setGamePlayedStyle() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("MavenPro-Regular.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 40;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 1f;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        GamePlayedStyle.font = fontGenerator.generateFont(fontParameters);
    }

    private void setGasolineLabelStyle() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("MavenPro-Regular.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 25;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 1f;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        GasolineLabelStyle.font = fontGenerator.generateFont(fontParameters);
    }

    /**
     * Used to switch screens while game is running.
     * Method is static to simplify usage.
     */
    public static void setActiveScreen(BaseScreen s) {
        // check if there's another screen prior to the BaseScreen s
        if (game.getScreen() != null) {
            game.getScreen().hide();
            game.getScreen().dispose();
        }
        game.setScreen(s);
    }

}