package com.pypert.paper.copterdodger.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.pypert.paper.copterdodger.base.BaseActor;

public class EnemyBullet extends BaseActor {
    public int damage;

    public EnemyBullet(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("greenbullet.png");
        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));
        setSpeed(600);
        setMaxSpeed(600);
        setDeceleration(0);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        if (this.getX() + this.getWidth() < 0 || this.getWidth() >= Gdx.graphics.getWidth()) {
            this.remove();
            this.clear();
        }
    }
}