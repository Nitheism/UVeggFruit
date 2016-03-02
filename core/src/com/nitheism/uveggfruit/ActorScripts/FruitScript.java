package com.nitheism.uveggfruit.ActorScripts;


import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;


public abstract class FruitScript implements IActorScript {
    public abstract Rectangle getBounds();

    public abstract int getDmg();

    public abstract void setHp(int dmg);

    public abstract void setCollision(boolean inCollision);

    public abstract boolean dead();

    public abstract CompositeActor getEntity();

    public abstract void drawHp();

    public abstract ArrayList<VeggieScript> getVeggieList();
}
