package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.pypert.paper.copterdodger.base.BaseActor;

public class Gasoline extends BaseActor {
    public Gasoline(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("gas.png");
        Action pulse = Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.5f),
                Actions.scaleTo(1f, 1f, 0.5f)
        );
        addAction(Actions.forever(pulse));
        setSpeed(100);
        setMotionAngle(180);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        applyPhysics(delta);

        if (getX() + getWidth() < 0) {
            remove();
        }
    }
}
