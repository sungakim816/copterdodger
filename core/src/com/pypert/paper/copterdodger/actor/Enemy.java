package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.base.BaseActor;

public class Enemy extends BaseActor {
    public int healthPoints;
    public boolean canShoot;
    public float bulletTimer;
    public float bulletSpawnInterval;
    public int worth;

    public Enemy(float x, float y, Stage s) {
        super(x, y, s);
        healthPoints = 25;
        canShoot = false;
        bulletTimer = 0f;
        bulletSpawnInterval = 0.3f;
        worth = 1;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

}
