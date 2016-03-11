package com.nitheism.uveggfruit.ActorScripts;

import com.badlogic.gdx.utils.Timer;
import com.nitheism.uveggfruit.Players.FruitPlayer;
import com.nitheism.uveggfruit.Players.VeggiePlayer;


public class CollisionTask extends Timer.Task {

    private FruitScript f;
    private VeggieScript v;
    private VeggiePlayer vp;
    private FruitPlayer fp;

    public CollisionTask (FruitScript fs,VeggieScript vs,FruitPlayer fp,VeggiePlayer vp){
        //initializing
        this.f = fs;
        this.v = vs;
        this.vp = vp;
        this.fp = fp;
        //stopping all actors from moving
        f.setCollision(true);
        v.setCollision(true);
    }


    @Override
    public void run() {
        //both actors take damage
        f.setHp(v.getDmg());
        v.setHp(f.getDmg());
        //check if any of the actors is dead
        if(f.dead()){
            //removes actor from stage,list and gives money to the other player
            v.getFruitsList().remove(f);
            f.getEntity().remove();
            f.getEntity().getScripts().clear();
            vp.setMoney(f.moneyGain());
            //check if the other players unit is not dead and allows him to move again
            if(!v.dead()){
                v.setCollision(false);
            }
        }

        if(v.dead()){
            //removes actor from stage,list and gives money to the other player
            f.getVeggieList().remove(v);
            v.getEntity().remove();
            v.getEntity().getScripts().clear();
            fp.setMoney(v.moneyGain());
            //check if the other players unit is not dead and allows him to move again
            if(!f.dead()){
                f.setCollision(false);
            }

        }


    }
}
