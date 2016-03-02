package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.utils.Timer;


public class CollisionTask extends Timer.Task {

    private FruitScript f;
    private VeggieScript v;

    public CollisionTask (FruitScript fs,VeggieScript vs){
        this.f = fs;
        this.v = vs;
        f.setCollision(true);
        v.setCollision(true);
    }


    @Override
    public void run() {
        f.setHp(v.getDmg());
        v.setHp(f.getDmg());
        if(v.dead()){
            f.getVeggieList().remove(v);
            v.getEntity().remove();
            v.getEntity().getScripts().clear();
            if(!f.dead()){
                f.setCollision(false);
            }
        }
        else if(f.dead()){
            v.getFruitsList().remove(f);
            f.getEntity().remove();
            f.getEntity().getScripts().clear();
            if(!v.dead()){
                v.setCollision(false);
            }
        }

    }
}
