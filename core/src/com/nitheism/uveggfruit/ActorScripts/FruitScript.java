package com.nitheism.uveggfruit.ActorScripts;


import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;



public abstract class FruitScript {
    public abstract Rectangle getBounds();

    public abstract int getDmg();

    public abstract void setHp(int dmg);

    public abstract void setCol(boolean col);

    public abstract boolean dead();

    public abstract CompositeActor getEntity();

    public abstract void drawHp();
}
