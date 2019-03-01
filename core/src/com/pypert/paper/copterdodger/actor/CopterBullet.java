package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.pypert.paper.copterdodger.CopterDodger;
import com.pypert.paper.copterdodger.base.BaseActor;

public class CopterBullet extends BaseActor {
    public CopterBullet(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromFiles(CopterDodger.COPTER_BULLET_SKIN, 0.1f, true);

        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));
        setSpeed(450);
        setMaxSpeed(450);
        setDeceleration(0);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        wrapAroundWorld();
    }
}
