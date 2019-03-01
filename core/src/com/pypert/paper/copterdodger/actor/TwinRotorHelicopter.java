package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.CopterDodger;

public class TwinRotorHelicopter extends Enemy {
    public TwinRotorHelicopter(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromFiles(CopterDodger.TWIN_ROTOR_SKIN, 0.06f, true);
        setSpeed(90);
        setMotionAngle(180);
        setBoundaryPolygon(8);
        healthPoints = 350;
        bulletSpawnInterval = 1f;
        worth = 3;
    }

    public void shoot() {
        if (getStage() == null || !canShoot) {
            return;
        }
        EnemyBullet bullet = new EnemyBullet(0, 0, getStage());
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
                this.shoot();
                bulletTimer = 0;
            }
        }
    }
}
