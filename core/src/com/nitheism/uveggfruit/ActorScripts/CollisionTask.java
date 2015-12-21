package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.utils.Timer;


public class CollisionTask extends Timer.Task {

    private FruitScript f;
    private VeggieScript v;

    public CollisionTask (FruitScript fs,VeggieScript vs){
        this.f = fs;
        this.v = vs;
    }


    @Override
    public void run() {
        f.setHp(v.getDmg());
        v.setHp(f.getDmg());


    }
}
