package com.nitheism.uveggfruit.ActorScripts;

import com.uwsoft.editor.renderer.scene2d.CompositeActor;

import java.util.ArrayList;


public abstract class VeggieScript {
    public abstract int getDmg();

    public abstract void setHp(int dmg);

    public abstract boolean dead();

    public abstract void setCollision(boolean inCollision);

    public abstract CompositeActor getEntity();

    public abstract ArrayList<FruitScript> getFruitsList();


}
