package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.CopterDodger;

public class Helicopter extends Enemy {
    public Helicopter(float x, float y, Stage s) {
        super(x, y, s);
        String file = "enemyhelicopter";
        String[] fileNames = new String[2];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = file + i + ".png";
        }
        loadAnimationFromFiles(CopterDodger.HELICOPTER_SKIN, 0.07f, true);
        setSpeed(200);
        setMotionAngle(180);
        setBoundaryPolygon(8);
        healthPoints = 75;
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
    }
}