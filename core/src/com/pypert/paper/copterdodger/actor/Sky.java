package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.base.BaseActor;

public class Sky extends BaseActor {
    public Sky(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("sky.jpg");
        setSpeed(30);
        setMotionAngle(180);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);

        // if moved completely past left edge of screen:
        //   shift right, past other instance.
        if (getX() + getWidth() < 0) {
            moveBy(2 * getWidth(), 0);
        }
    }

}