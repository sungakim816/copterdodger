package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.CopterDodger;

public class Airplane extends Enemy {
    public Airplane(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromFiles(CopterDodger.AIRPLANE_SKIN, 0.1f, true);
        setSpeed(150);
        setMotionAngle(180);
        setBoundaryPolygon(8);
        healthPoints = 250; // Override Attribute
        bulletSpawnInterval = 0.5f;
        worth = 2;
    }

    public void shoot() {
        if (getStage() == null || !canShoot) {
            return;
        }
        EnemyBullet bullet = new EnemyBullet(0, 0, getStage());
        bullet.loadTexture("blackbullet.png");
        bullet.centerAtPosition(this.getX(), this.getY() + this.getHeight() / 2);
        bullet.setRotation(this.getRotation());
        bullet.setMotionAngle(this.getMotionAngle());
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        if (this.canShoot) {
            bulletTimer += dt;
            if (bulletTimer >= bulletSpawnInterval) {
                shoot();
                bulletTimer = 0;
            }
        }
    }
}
