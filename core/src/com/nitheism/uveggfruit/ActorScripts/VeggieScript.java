package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.scene2d.CompositeActor;
import com.uwsoft.editor.renderer.scripts.IActorScript;

import java.util.ArrayList;

//abstract class used for all vegetable actors
public abstract class VeggieScript implements IActorScript {

    public abstract Rectangle getBounds();

    public abstract int getDmg();

    public abstract void setHp(int dmg);

    public abstract void setCollision(boolean inCollision);

    public abstract boolean dead();

    public abstract CompositeActor getEntity();

    public abstract void drawHp();

    public abstract ArrayList<FruitScript> getFruitsList();

    public abstract int moneyGain();


}
