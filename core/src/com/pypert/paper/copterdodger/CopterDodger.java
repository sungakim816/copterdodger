package com.pypert.paper.copterdodger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.pypert.paper.copterdodger.base.BaseGame;
import com.pypert.paper.copterdodger.screen.MenuScreen;

public class CopterDodger extends BaseGame {
    public static final String ENEMY = "com.pypert.paper.copterdodger.actor.Enemy";
    public static final String ENEMY_HELICOPTER = "com.pypert.paper.copterdodger.actor.Helicopter";
    public static final String ENEMY_TWIN_ROTOR = "com.pypert.paper.copterdodger.actor.TwinRotorHelicopter";
    public static final String ENEMY_AIRPLANE = "com.pypert.paper.copterdodger.actor.Airplane";
    public static final String STAR = "com.pypert.paper.copterdodger.actor.Star";
    public static final String BULLET_ITEM = "com.pypert.paper.copterdodger.actor.BulletItem";
    public static final String GASOLINE = "com.pypert.paper.copterdodger.actor.Gasoline";
    public static final String SHIELD_ITEM = "com.pypert.paper.copterdodger.actor.ShieldItem";
    public static final String GROUND = "com.pypert.paper.copterdodger.actor.Ground";
    public static final String SKY = "com.pypert.paper.copterdodger.actor.Sky";
    public static final String BLACK_BOMB = "com.pypert.paper.copterdodger.actor.BlackBomb";
    public static final String BLUE_BOMB = "com.pypert.paper.copterdodger.actor.BlueBomb";
    public static final String RED_BOMB = "com.pypert.paper.copterdodger.actor.RedBomb";
    public static final String SPECIAL_BOMB = "com.pypert.paper.copterdodger.actor.SpecialBomb";
    public static final String COPTER_BULLET = "com.pypert.paper.copterdodger.actor.CopterBullet";
    public static final String ENEMY_BULLET = "com.pypert.paper.copterdodger.actor.EnemyBullet";
    public static final String HIGH_SCORE_KEY = "com.pypert.paper.copterdodger.high.score";
    public static final String AUDIO_VOLUME_KEY = "com.pypert.paper.copterdodger.audio.volume";
    public static final String PREFERENCES_KEY = "CopterDodger Preferences";
    public static Preferences COPTER_PREFS;
    public static int HIGH_SCORE;
    public static int AUDIO_VOLUME;
    public static final int GAME_WORLD_WIDTH = 1366; // 100
    public static final float GROUND_WIDTH = 1366; //
    public static final int GAME_WORLD_HEIGHT = 768; // 75
    public static String[] HELICOPTER_SKIN;
    public static String[] AIRPLANE_SKIN;
    public static String[] TWIN_ROTOR_SKIN;
    public static String[] COPTER_BULLET_SKIN;
    public static String[] COPTER_SKIN;
    public static Music BACKGROUND_MUSIC;

    public void create() {
        super.create();
        COPTER_PREFS = Gdx.app.getPreferences(PREFERENCES_KEY);
        HIGH_SCORE = COPTER_PREFS.getInteger(HIGH_SCORE_KEY);
        COPTER_PREFS = Gdx.app.getPreferences(CopterDodger.PREFERENCES_KEY);
        AUDIO_VOLUME = CopterDodger.COPTER_PREFS.getInteger(CopterDodger.AUDIO_VOLUME_KEY);
        COPTER_PREFS.flush();
        BACKGROUND_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("Prelude-and-Action.mp3"));
        BACKGROUND_MUSIC.setLooping(true);

        String twin_rotor = "twinrotorhelicopter";
        String[] twin_rotors = new String[6];
        for (int i = 0; i < twin_rotors.length; i++) {
            twin_rotors[i] = twin_rotor + i + ".png";
        }
        TWIN_ROTOR_SKIN = twin_rotors;
        String helicopter = "enemyhelicopter";
        String[] helicopters = new String[2];
        for (int i = 0; i < helicopters.length; i++) {
            helicopters[i] = helicopter + i + ".png";
        }
        HELICOPTER_SKIN = helicopters;
        String airplane = "airplane";
        String[] airplanes = new String[16];
        for (int i = 0; i < airplanes.length; i++) {
            airplanes[i] = airplane + i + ".gif";
        }
        AIRPLANE_SKIN = airplanes;

        String copter_bullet = "bulletframe";
        String[] copter_bullets = new String[5];
        for (int i = 0; i < copter_bullets.length; i++) {
            copter_bullets[i] = copter_bullet + i + ".png";
        }
        COPTER_BULLET_SKIN = copter_bullets;
        String copter = "frame";
        String[] copterFrames = new String[5];
        for (int i = 0; i < copterFrames.length; i++) {
            copterFrames[i] = copter + i + ".png";
        }
        COPTER_SKIN = copterFrames;
        // first screen to be activated
        setActiveScreen(new MenuScreen());

    }
}