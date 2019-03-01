package com.pypert.paper.copterdodger.actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pypert.paper.copterdodger.base.BaseActor;

public class Explosion extends BaseActor
{
    public Explosion(float x, float y, Stage s)
    {
       super(x,y,s);
       loadAnimationFromSheet("explosion.png", 6,6, 0.02f, false);
    }
    
    public void act(float dt)
    {
        super.act(dt);
        
        if ( isAnimationFinished() )
            remove();
    }
}