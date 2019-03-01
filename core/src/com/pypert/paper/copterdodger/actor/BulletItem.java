package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.pypert.paper.copterdodger.base.BaseActor;

public class BulletItem extends BaseActor {
    public BulletItem(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("itembullet.png");
        Action pulse = Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.5f),
                Actions.scaleTo(1.0f, 1.0f, 0.5f));
        addAction(Actions.forever(pulse));

        setSpeed(120);
        setMotionAngle(180);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);

        // remove after moving past left edge of screen
        if (getX() + getWidth() < 0)
            remove();
    }
}
