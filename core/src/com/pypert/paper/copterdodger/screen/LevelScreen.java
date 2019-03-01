package com.pypert.paper.copterdodger.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.pypert.paper.copterdodger.actor.*;
import com.pypert.paper.copterdodger.CopterDodger;
import com.pypert.paper.copterdodger.base.BaseActor;
import com.pypert.paper.copterdodger.base.BaseGame;
import com.pypert.paper.copterdodger.base.BaseScreen;

public class LevelScreen extends BaseScreen {
    private Copter copter;
    private float starTimer;
    private float gasolineSpawnTimer;
    private float starSpawnInterval;
    private float gasolineSpawnInterval;
    private float gasolineTankDecreaseInterval;
    private float gasolineTankTimer;
    private float enemyHelicopterTimer;
    private float helicopterSpawnInterval;
    private float enemyHelicopterSpeed;
    private float enemyTwinRotorTimer;
    private float twinRotorSpawnInterval;
    private float twinRotorSpeed;
    private float enemyAirplaneTimer;
    private float airplaneSpawnInterval;
    private float airplaneSpeed;
    private float bulletItemTimer;
    private float bulletItemSpawnInterval;
    private float gasolineIncrement;
    private float shieldTimer;
    private float shieldSpawnInterval;
    private float gasolineTank;
    private float gasolineTankMax;
    private short score, shieldScoreFlag, twinRotorScoreFlag, airplaneScoreFlag;
    private short blackInitialScore, blueInitialScore, redInitialScore, specialInitialScore;
    private short twinRotorShootFlag, airplaneShootFlag, helicopterIncreaseHealthFlag;
    private short blackBombScoreFlag, redBombScoreFlag, blueBombScoreFlag, specialBombScoreFlag;
    private Label scoreLabel, highScoreLabel, gasolineTankLabel, shieldPowerLabel, bulletDamageLabel;
    private boolean gameOver;
    private BaseActor gameOverMessage;
    private Sound sparkleSound, explosionSound;
    private Button restartButton, exitButton, soundButton, homeButton;
    private ButtonStyle restartButtonStyle, exitButtonStyle, soundButtonStyle, homeButtonStyle;
    private TextureRegion restartButtonRegion, exitButtonRegion, soundOnButtonRegion, soundOffButtonRegion, homeButtonRegion;
    private TextureRegionDrawable soundOff, soundOn;

    LevelScreen() {
        super();
    }

    public void initialize() {
        homeButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("homesmall.png")));
        homeButtonStyle = new ButtonStyle();
        homeButtonStyle.up = new TextureRegionDrawable(homeButtonRegion);
        homeButton = new Button(homeButtonStyle);
        homeButton.setVisible(false);
        homeButton.setDisabled(true);
        homeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                CopterDodger.setActiveScreen(new MenuScreen());
                return true;
            }
        });
        restartButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("restartbig.png")));
        restartButtonStyle = new Button.ButtonStyle();
        restartButtonStyle.up = new TextureRegionDrawable(restartButtonRegion);
        restartButton = new Button(restartButtonStyle);
        restartButton.setVisible(false);
        restartButton.setDisabled(true);
        restartButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                CopterDodger.setActiveScreen(new LevelScreen());
                return true;
            }
        });
        exitButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("exit_red_small.png")));
        exitButtonStyle = new Button.ButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable(exitButtonRegion);
        exitButton = new Button(exitButtonStyle);
        exitButton.setVisible(false);
        exitButton.setDisabled(true);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        soundOffButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("soundoff.png")));
        soundOnButtonRegion = new TextureRegion(new Texture(Gdx.files.internal("soundon.png")));
        soundButtonStyle = new Button.ButtonStyle();
        soundOff = new TextureRegionDrawable(soundOffButtonRegion);
        soundOn = new TextureRegionDrawable(soundOnButtonRegion);
        soundButtonStyle.up = (CopterDodger.AUDIO_VOLUME == 1) ? soundOn : soundOff;
        soundButton = new Button(soundButtonStyle);
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
        soundButton.setDisabled(true);
        soundButton.setVisible(false);
        new Sky(0, 0, mainStage);
        new Sky(CopterDodger.GAME_WORLD_WIDTH, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(CopterDodger.GROUND_WIDTH, 0, mainStage);
        copter = new Copter(100, CopterDodger.GAME_WORLD_HEIGHT - 300, mainStage);
        BaseActor.setWorldBounds(CopterDodger.GAME_WORLD_WIDTH, CopterDodger.GAME_WORLD_HEIGHT);
        gasolineTank = 15f; // initial gasoline
        gasolineTankMax = 40f; // maximum gasoline in liters
        gasolineTankDecreaseInterval = 1f; // after 'n' second the gasoline will be decreased by 0.5 liter
        gasolineTankTimer = 0; // gasoline tank timer
        gasolineIncrement = 5f;
        // items
        gasolineSpawnTimer = 0f;
        starTimer = 0f;
        starSpawnInterval = 5f;
        gasolineSpawnInterval = 5f;
        shieldSpawnInterval = 10f;
        shieldTimer = 10f;
        bulletItemSpawnInterval = 10f;
        bulletItemTimer = 10f;
        // enemies
        enemyHelicopterTimer = 0;
        enemyHelicopterSpeed = 200;
        helicopterSpawnInterval = 4.5f;
        twinRotorSpeed = 100f;
        enemyTwinRotorTimer = 0;
        twinRotorSpawnInterval = airplaneSpawnInterval = 3f;
        airplaneSpeed = 150;
        enemyAirplaneTimer = 0;
        score = 0;
        blackInitialScore = blueInitialScore = redInitialScore = specialInitialScore = 0;
        shieldScoreFlag = 20;
        twinRotorScoreFlag = 80;
        airplaneScoreFlag = 200;
        blackBombScoreFlag = 300;
        twinRotorShootFlag = 400;
        blueBombScoreFlag = 500;
        helicopterIncreaseHealthFlag = 600;
        airplaneShootFlag = 700;
        redBombScoreFlag = 800;
        specialBombScoreFlag = 1000;
        scoreLabel = new Label(Integer.toString(score), BaseGame.GamePlayedStyle);
        bulletDamageLabel = new Label("Bullet Damage: " + copter.getBulletDamage(), BaseGame.MavenProRegular);
        bulletDamageLabel.setVisible(false);
        highScoreLabel = new Label("High Score: " + CopterDodger.HIGH_SCORE, BaseGame.MavenProRegular);
        gasolineTankLabel = new Label("Gas: " + gasolineTank + "L", BaseGame.GasolineLabelStyle);
        shieldPowerLabel = new Label("Shield: x" + copter.getShieldPower(), BaseGame.GasolineLabelStyle);
        shieldPowerLabel.setVisible(false);
        highScoreLabel.setVisible(false);
        gameOverMessage = new BaseActor(0, 0, uiStage);
        gameOverMessage.loadTexture("game-over.png");
        gameOverMessage.setVisible(false);
        uiTable.add(gasolineTankLabel).pad(10).left().expandX();
        uiTable.add(bulletDamageLabel).pad(10).center().expandX();
        uiTable.add(shieldPowerLabel).pad(10).right().expandX();
        uiTable.row();
        uiTable.add(scoreLabel).colspan(3).pad(10);
        uiTable.row();
        uiTable.add(highScoreLabel).colspan(3).expand();
        uiTable.row().pad(10);
        uiTable.add(restartButton).colspan(3).center().expandX();
        uiTable.row().pad(30);
        uiTable.add(soundButton).right().expandX();
        uiTable.add(homeButton).expandX();
        uiTable.add(exitButton).left().expandX();
        uiTable.row();
        uiTable.add().expandY();
        uiTable.row();
        sparkleSound = Gdx.audio.newSound(Gdx.files.internal("sparkle.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        CopterDodger.BACKGROUND_MUSIC.stop();
        CopterDodger.BACKGROUND_MUSIC.play();
    }

    private void decreasePlayerGasoline(float dt) {
        gasolineTankTimer += dt;
        if (gasolineTankTimer > gasolineTankDecreaseInterval) {
            gasolineTank -= 0.5f;
            gasolineTankLabel.setText("Gas: " + gasolineTank + "L");
            gasolineTankTimer = 0;
        }
    }

    private void createStarItem(float dt) {
        starTimer += dt;
        if (starTimer > starSpawnInterval) {
            new Star(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - 150), mainStage);
            starTimer = 0;
        }
    }

    private void createGasolineItem(float dt) {
        gasolineSpawnTimer += dt;
        if (gasolineSpawnTimer > gasolineSpawnInterval) {
            new Gasoline(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - 150), mainStage);
            gasolineSpawnTimer = 0;
            gasolineSpawnInterval = MathUtils.random(5f, 10f);
        }
    }

    private void createShieldItem(float dt) {
        shieldTimer += dt;
        if (shieldTimer >= shieldSpawnInterval) {
            new ShieldItem(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - 150), mainStage);
            shieldTimer = 0;
            shieldSpawnInterval = MathUtils.random(10f, 20f);
        }
    }

    private void createBulletItem(float dt) {
        bulletItemTimer += dt;
        if (bulletItemTimer >= bulletItemSpawnInterval) {
            new BulletItem(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - 100), mainStage);
            bulletItemTimer = 0;
            bulletItemSpawnInterval = MathUtils.random(10f, 20f);
        }
    }

    private void createBlackBombItem() {
        if (score > blackBombScoreFlag && score - blackInitialScore >= 100) {
            new BlackBomb(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - copter.getWidth()), mainStage);
            blackInitialScore = score;
        }
    }

    private void createBlueBombItem() {
        if (score > blueBombScoreFlag && score - blueInitialScore >= 150) {
            new BlueBomb(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - copter.getWidth()), mainStage);
            blueInitialScore = score;
        }
    }

    private void createRedBombItem() {
        if (score > redBombScoreFlag && score - redInitialScore >= 150) {
            new RedBomb(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - copter.getWidth()), mainStage);
            redInitialScore = score;
        }
    }

    private void createSpecialBombItem() {
        if (score > specialBombScoreFlag && score - specialInitialScore >= 200) {
            new SpecialBomb(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - copter.getWidth()), mainStage);
            specialInitialScore = score;
        }
    }

    private void createEnemyHelicopter(float dt) {
        enemyHelicopterTimer += dt;
        if (enemyHelicopterTimer > helicopterSpawnInterval) {
            Helicopter helicopter = new Helicopter(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(100, CopterDodger.GAME_WORLD_HEIGHT - copter.getWidth()), mainStage);
            helicopter.setSpeed(enemyHelicopterSpeed);
            if (score > helicopterIncreaseHealthFlag) {
                helicopter.healthPoints += 100;
            }
            helicopterSpawnInterval -= 0.1f;
            enemyHelicopterSpeed += 5;
            if (helicopterSpawnInterval <= 0.5f)
                helicopterSpawnInterval = 0.5f;
            if (enemyHelicopterSpeed >= 550)
                enemyHelicopterSpeed = 550;
            enemyHelicopterTimer = 0;
        }
    }

    private void createEnemyTwinRotor(float dt) {
        enemyTwinRotorTimer += dt;
        if (enemyTwinRotorTimer >= twinRotorSpawnInterval) {
            TwinRotorHelicopter twinRotor = new TwinRotorHelicopter(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(120, CopterDodger.GAME_WORLD_HEIGHT - copter.getWidth() - 100), mainStage);
            twinRotor.setSpeed(twinRotorSpeed);
            twinRotorSpeed += 5;
            if (score > twinRotorShootFlag && score % 2 == 1) {
                twinRotor.canShoot = true;
            }
            enemyTwinRotorTimer = 0;
            twinRotorSpawnInterval = MathUtils.random(3f, 8f);
        }
        if (twinRotorSpeed >= 550) {
            twinRotorSpeed = 550;
        }
    }

    private void createEnemyAirplane(float dt) {
        enemyAirplaneTimer += dt;
        if (enemyAirplaneTimer >= airplaneSpawnInterval) {
            Airplane airplane = new Airplane(CopterDodger.GAME_WORLD_WIDTH, MathUtils.random(120, CopterDodger.GAME_WORLD_HEIGHT - copter.getHeight()), mainStage);
            airplane.setSpeed(airplaneSpeed);
            airplaneSpeed += 5;
            if (score > airplaneShootFlag && score % 2 == 1) {
                airplane.canShoot = true;
            }
            enemyAirplaneTimer = 0;
            airplaneSpawnInterval = MathUtils.random(3f, 8f);
        }
        if (airplaneSpeed >= 600) {
            airplaneSpeed = 600;
        }
    }

    private void checkStarCollision() {
        for (BaseActor star : BaseActor.getList(mainStage, CopterDodger.STAR)) {
            if (copter.overlaps(star)) {
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(star);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                star.remove();
                star.clear();
                score += 2;
            }
        }
    }

    private void checkGasolineCollision() {
        for (BaseActor gasoline : BaseActor.getList(mainStage, CopterDodger.GASOLINE)) {
            if (copter.overlaps(gasoline)) {
                Sparkle ge = new Sparkle(0, 0, mainStage);
                ge.centerAtActor(gasoline);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                gasoline.remove();
                gasoline.clear();
                gasolineTank += gasolineIncrement;
                if (gasolineTank >= gasolineTankMax)
                    gasolineTank = gasolineTankMax;
                gasolineTankLabel.setText("Gas: " + gasolineTank + "L");
            }
        }
    }

    private void checkShieldItemCollision() {
        for (BaseActor shield : BaseActor.getList(mainStage, CopterDodger.SHIELD_ITEM)) {
            if (copter.overlaps(shield)) {
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(shield);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                shield.remove();
                shield.clear();
                score += 2;
                shieldPowerLabel.setVisible(true);
                if (!copter.getShield().isVisible()) {
                    copter.activateShield();
                } else { // if your shield is already active and then you get another shield item
                    copter.setShieldPower(copter.getShieldPower() + 1);
                }
                shieldPowerLabel.setText("Shield: x" + copter.getShieldPower());
            }
        }
    }

    private void checkGroundCollision() {
        for (BaseActor ground : BaseActor.getList(mainStage, CopterDodger.GROUND)) {
            if (copter.overlaps(ground)) {
                Explosion copterExplosion = new Explosion(0, 0, mainStage);
                copterExplosion.centerAtActor(copter);
                copterExplosion.setScale(3);
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
                copter.remove();
                copter.clear();
                gameOver = true;
            }
        }
    }

    private void checkBlackBombItemCollision() {
        for (BaseActor bomb : BaseActor.getList(mainStage, CopterDodger.BLACK_BOMB)) {
            if (copter.overlaps(bomb)) {
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(bomb);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                bomb.remove();
                bomb.clear();
                destroyAllEnemyHelicopter();
            }
        }
    }

    private void checkBlueBombItemCollision() {
        for (BaseActor bomb : BaseActor.getList(mainStage, CopterDodger.BLUE_BOMB)) {
            if (copter.overlaps(bomb)) {
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(bomb);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                bomb.remove();
                bomb.clear();
                destroyAllEnemyAirplanes();
            }
        }
    }

    private void checkRedBombItemCollision() {
        for (BaseActor bomb : BaseActor.getList(mainStage, CopterDodger.RED_BOMB)) {
            if (copter.overlaps(bomb)) {
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(bomb);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                bomb.remove();
                bomb.clear();
                destroyAllEnemyTwinRotor();
            }
        }
    }

    private void checkSpecialBombItemCollision() {
        for (BaseActor bomb : BaseActor.getList(mainStage, CopterDodger.SPECIAL_BOMB)) {
            if (copter.overlaps(bomb)) {
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(bomb);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                bomb.remove();
                bomb.clear();
                destroyAllEnemyHelicopter();
                destroyAllEnemyTwinRotor();
                destroyAllEnemyAirplanes();
            }
        }
    }

    private void destroyAllEnemyHelicopter() {
        for (BaseActor enemy : BaseActor.getList(mainStage, CopterDodger.ENEMY_HELICOPTER)) {
            Explosion ex = new Explosion(0, 0, mainStage);
            ex.centerAtActor(enemy);
            ex.setScale(3);
            enemy.remove();
            enemy.clear();
            score += ((Enemy) enemy).worth;
        }
    }

    private void destroyAllEnemyTwinRotor() {
        for (BaseActor enemy : BaseActor.getList(mainStage, CopterDodger.ENEMY_TWIN_ROTOR)) {
            Explosion ex = new Explosion(0, 0, mainStage);
            ex.centerAtActor(enemy);
            ex.setScale(3);
            enemy.remove();
            enemy.clear();
            score += ((Enemy) enemy).worth;
        }
    }

    private void destroyAllEnemyAirplanes() {
        for (BaseActor enemy : BaseActor.getList(mainStage, CopterDodger.ENEMY_AIRPLANE)) {
            Explosion ex = new Explosion(0, 0, mainStage);
            ex.centerAtActor(enemy);
            ex.setScale(3);
            enemy.remove();
            enemy.clear();
            score += ((Enemy) enemy).worth;
        }
    }

    private void checkEmptyGasoline() {
        if (gasolineTank <= 0) {
            Explosion copterExplosion = new Explosion(0, 0, mainStage);
            copterExplosion.centerAtActor(copter);
            copterExplosion.setScale(3);
            explosionSound.play(CopterDodger.AUDIO_VOLUME);
            copter.remove();
            copter.clear();
            gameOver = true;
        }
    }

    private void checkEnemyCollision() {
        for (BaseActor enemy : BaseActor.getList(mainStage, CopterDodger.ENEMY)) {
            if (copter.overlaps(enemy)) {
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
                int enemyHealthPoints = ((Enemy) enemy).healthPoints;
                if (copter.getShieldPower() <= 0 && !copter.canShoot) { // if shield is not present
                    Explosion copterExplosion = new Explosion(0, 0, mainStage); // for copter
                    copterExplosion.centerAtActor(copter);
                    copterExplosion.setScale(3);
                    copter.remove();
                    copter.clear();
                    gameOver = true;
                } else if (copter.getShieldPower() > 0) { // if shield is present
                    score += ((Enemy) enemy).worth;
                    copter.setShieldPower(copter.getShieldPower() - 1);
                    if (copter.getShieldPower() <= 0) {
                        copter.deactivateShield();
                        shieldPowerLabel.setVisible(false);
                    }
                    shieldPowerLabel.setText("Shield: x" + copter.getShieldPower());
                } else if (copter.canShoot && copter.getShieldPower() <= enemyHealthPoints) {
                    copter.setCanShoot(false);
                    bulletDamageLabel.setVisible(false);
                    score += ((Enemy) enemy).worth;
                    copter.deactivateShield();
                    shieldPowerLabel.setVisible(false);
                }
                Explosion ex = new Explosion(0, 0, mainStage); // for enemy
                ex.centerAtActor(enemy);
                ex.setScale(3);
                enemy.remove();
                enemy.clear();
            }
            if (enemy.getX() + enemy.getWidth() < 0) {
                score++;
                enemy.remove();
                enemy.clear();
            }
        }
    }

    private void checkBulletItemCollision() {
        for (BaseActor bulletItem : BaseActor.getList(mainStage, CopterDodger.BULLET_ITEM)) {
            if (copter.overlaps(bulletItem)) {
                score++;
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(bulletItem);
                sparkleSound.play(CopterDodger.AUDIO_VOLUME);
                bulletItem.remove();
                bulletItem.clear();
                if (!copter.canShoot) {
                    copter.canShoot = true;
                    bulletDamageLabel.setVisible(true);
                    copter.reset_bullet_damage();
                    bulletDamageLabel.setText("Bullet Damage: " + copter.getBulletDamage());
                } else {
                    if (copter.getBulletDamage() < 45) {
                        copter.setBulletDamage(copter.getBulletDamage() + 5);
                        bulletDamageLabel.setText("Bullet Damage: " + copter.getBulletDamage());
                    }
                }
            }
        }
    }

    private void checkCopterBulletEnemyCollision() {
        for (BaseActor copterBullet : BaseActor.getList(mainStage, CopterDodger.COPTER_BULLET)) {
            for (BaseActor enemy : BaseActor.getList(mainStage, CopterDodger.ENEMY)) {
                if (copterBullet.overlaps(enemy)) {
                    Explosion bulletExplosion = new Explosion(0, 0, mainStage);
                    bulletExplosion.centerAtActor(copterBullet);
                    copterBullet.remove();
                    copterBullet.clear();
                    ((Enemy) enemy).healthPoints -= copter.getBulletDamage();
                    if (((Enemy) enemy).healthPoints <= 0) {
                        Explosion enemyExplosion = new Explosion(0, 0, mainStage);
                        enemyExplosion.centerAtActor(enemy);
                        enemyExplosion.setScale(3);
                        score += ((Enemy) enemy).worth;
                        enemy.remove();
                        enemy.clear();
                    }
                    explosionSound.play(CopterDodger.AUDIO_VOLUME);
                }
            }
        }
    }

    private void checkEnemyBulletCollision() {
        for (BaseActor enemyBullet : BaseActor.getList(mainStage, CopterDodger.ENEMY_BULLET)) {
            if (enemyBullet.overlaps(copter)) {
                if (copter.getShieldPower() <= 0) {
                    gameOver = true;
                    Explosion copterExplosion = new Explosion(0, 0, mainStage);
                    copterExplosion.centerAtActor(copter);
                    copterExplosion.setScale(3);
                    copter.remove();
                    copter.clear();
                } else {
                    copter.setShieldPower(copter.getShieldPower() - 1);
                    if (copter.getShieldPower() <= 0) {
                        copter.deactivateShield();
                        shieldPowerLabel.setVisible(false);
                    }
                    shieldPowerLabel.setText("Shield: x" + copter.getShieldPower());
                }
                Explosion bulletExplosion = new Explosion(0, 0, mainStage);
                bulletExplosion.centerAtActor(enemyBullet);
                enemyBullet.remove();
                enemyBullet.clear();
                explosionSound.play(CopterDodger.AUDIO_VOLUME);
            }
        }
    }

    private void removeRemainingEnemy() {
        if (BaseActor.count(mainStage, CopterDodger.ENEMY) != 0) {
            for (BaseActor enemy : BaseActor.getList(mainStage, CopterDodger.ENEMY)) {
                if (enemy.getX() + enemy.getWidth() < 0) {
                    enemy.remove();
                    enemy.clear();
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        if (gameOver) {
            return; // do nothing, render remaining actors and background.
        }
        decreasePlayerGasoline(dt);
        createStarItem(dt);
        createGasolineItem(dt);
        createEnemyHelicopter(dt);
        createBlackBombItem();
        createBlueBombItem();
        createRedBombItem();
        createSpecialBombItem();
        if (score > shieldScoreFlag) {
            createShieldItem(dt);
        }
        if (score > twinRotorScoreFlag) {
            createEnemyTwinRotor(dt);
            createBulletItem(dt);
        }
        if (score > airplaneScoreFlag) {
            createEnemyAirplane(dt);
        }
        checkShieldItemCollision();
        checkBulletItemCollision();
        checkCopterBulletEnemyCollision();
        checkEnemyCollision();
        checkEmptyGasoline();
        checkStarCollision();
        checkGasolineCollision();
        checkEnemyBulletCollision();
        checkBlackBombItemCollision();
        checkBlueBombItemCollision();
        checkRedBombItemCollision();
        checkSpecialBombItemCollision();
        checkGroundCollision();
        scoreLabel.setText(Integer.toString(score));
        if (gameOver) {
            homeButton.setDisabled(false);
            homeButton.setVisible(true);
            gasolineTankLabel.setVisible(false);
            bulletDamageLabel.setVisible(false);
            shieldPowerLabel.setVisible(false);
            gameOverMessage.setVisible(true);
            restartButton.setVisible(true);
            restartButton.setDisabled(false);
            exitButton.setVisible(true);
            exitButton.setDisabled(false);
            soundButton.setVisible(true);
            soundButton.setDisabled(false);
            highScoreLabel.setVisible(true);
            CopterDodger.BACKGROUND_MUSIC.stop();
            if (score > CopterDodger.HIGH_SCORE) {
                CopterDodger.HIGH_SCORE = score;
                CopterDodger.COPTER_PREFS.putInteger(
                        CopterDodger.HIGH_SCORE_KEY, CopterDodger.HIGH_SCORE
                );
                CopterDodger.COPTER_PREFS.flush();
                highScoreLabel.setText("New High Score: " + CopterDodger.HIGH_SCORE);
            } else {
                highScoreLabel.setText("High Score: " + CopterDodger.HIGH_SCORE);
            }
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Keys.SPACE) {
            copter.boost();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.input.isTouched() && !gameOver) {
            copter.boost();
        }
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}