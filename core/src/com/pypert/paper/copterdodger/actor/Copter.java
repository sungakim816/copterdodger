package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.base.BaseActor;

public class Copter extends BaseActor {
    private Shield shield;
    private int shieldPower;
    private short healthPoints;
    public boolean canShoot;
    private int bulletDamage;
    private float bulletTimer;
    private float bulletSpawnInterval;

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public Copter(float x, float y, Stage s) {
        super(x, y, s);
        String file = "frame";
        String[] fileNames = new String[5];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = file + i + ".png";
        }
        loadAnimationFromFiles(fileNames, 0.03f, true);
        setMaxSpeed(800);
        setBoundaryPolygon(8);
        shield = new Shield(x, y, s);
        addActor(shield);
        setSpeed(75);
        shield.centerAtPosition(getWidth() / 2, getHeight() / 2);
        shield.setVisible(false);
        shieldPower = 0; // initial_power
        canShoot = false;
        bulletDamage = 10;
        healthPoints = 100;
        bulletTimer = 0;
        bulletSpawnInterval = 0.25f;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(short healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public void shoot() {
        CopterBullet copterBullet = new CopterBullet(0, 0, this.getStage());
        copterBullet.centerAtPosition(this.getX() + this.getWidth(), this.getY() + this.getHeight() / 2);
        copterBullet.setRotation(this.getRotation());
        copterBullet.setMotionAngle(this.getRotation());
    }

    public void reset_bullet_damage() {
        bulletDamage = 10;
    }

    public void act(float dt) {
        super.act(dt);
        // simulate force of gravity
        setAcceleration(800);
        accelerateAtAngle(270);
        applyPhysics(dt);
//        stop plane from passing through the ground
//        for (BaseActor g : BaseActor.getList(this.getStage(), "com.pypert.paper.copterdodger.actor.Ground")) {
//            if (this.overlaps(g)) {
//                setSpeed(0);
//                preventOverlap(g);
//            }
//        }
//        stop plane from moving past top of screen
        if (getY() + getHeight() > getWorldBounds().height) {
            setSpeed(0);
            boundToWorld();
        }

        if (this.canShoot || getStage() == null) {
            bulletTimer += dt;
            if (bulletTimer >= bulletSpawnInterval) {
                this.shoot(); // if copter can shoot
                bulletTimer = 0;
            }
        }
    }

    public void boost() {
        setSpeed(300);
        setMotionAngle(90);
    }

    public void activateShield() {
        this.shieldPower = 1;
        shield.setVisible(true);
    }

    public void deactivateShield() {
        this.shieldPower = 0;
        shield.setVisible(false);
    }

    public int getShieldPower() {
        return shieldPower;
    }

    public BaseActor getShield() {
        return shield;
    }

    public void setShieldPower(int shieldPower) {
        this.shieldPower = shieldPower;
    }

}